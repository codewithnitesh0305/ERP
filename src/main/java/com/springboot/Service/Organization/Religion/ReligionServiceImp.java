package com.springboot.Service.Organization.Religion;

import com.springboot.Model.Organizations.Religion;
import com.springboot.Repository.Organization.ReligionRepository;
import com.springboot.Utility.ApiResponse;
import com.springboot.Utility.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReligionServiceImp implements ReligionService{

    private final ReligionRepository religionRepository;

    @Override
    public ResponseEntity<?> saveUpdateReligion(Map<String, Object> param, HttpServletRequest request) {
        Long id = Utilities.longValue(param.get("id"));
        String name = Utilities.stringValue(param.get("name"));

        if(name.isEmpty()) return ApiResponse.apiValidation("Religion is required.");
        Religion existingReligion = religionRepository.findByName(name);
        if(existingReligion != null && existingReligion.getName().equals(name) && !(existingReligion.getId().equals(id))) return ApiResponse.apiValidation("Religion already exist.");

        Religion religion = id == null ? new Religion() : religionRepository.findById(id).orElseThrow(() -> new RuntimeException("Religion not found."));
        religion.setName(name);
        if(id == null){
            religion.setIsActive(true);
            religion.setCreatedBy(null);
            religion.setCreatedOn(LocalDateTime.now());
        }else{
            religion.setUpdatedBy(null);
            religion.setUpdatedOn(LocalDateTime.now());
        }
        religionRepository.save(religion);
        return ApiResponse.apiSuccess();
    }

    @Override
    public Map<String, Object> getAllReligion(Map<String, Object> param, HttpServletRequest request) {
        String fts = Utilities.stringValue(param.get("fts"));
        fts = fts.isEmpty() ? null : "%"+fts+"%";
        List<Map<String,Object>> religionList = religionRepository.getAllReligion(fts);
        return Map.of("data_array",religionList);
    }

    @Override
    public ResponseEntity<?> updateReligionStatus(Map<String, Object> param, HttpServletRequest request) {
        Long id = Utilities.longValue(param.get("id"));
        Boolean isActive = Utilities.booleanValue(param.get("isActive"));
        Religion religion = religionRepository.findById(id).orElseThrow(() -> new RuntimeException("Kindly select al least one row."));
        if(religion != null){
            religion.setIsActive(isActive);
            religion.setUpdatedBy(null);
            religion.setUpdatedOn(LocalDateTime.now());
            religionRepository.save(religion);
        }
        return ApiResponse.apiSuccess();
    }

    @Override
    public ResponseEntity<?> deleteReligion(Map<String, Object> param, HttpServletRequest request) {
        String id = Utilities.stringValue(param.get("ids"));
        if(id.isEmpty()) return ApiResponse.apiValidation("Kindly select at least one row.");
        Set<Long> ids = Utilities.getSetOfCommaValue(id);
        if(!ids.isEmpty()) religionRepository.deleteAllById(ids);
        return ApiResponse.apiSuccess();
    }
}
