package com.springboot.Service.Organization.Caste;

import com.springboot.Model.Organizations.Caste;
import com.springboot.Model.Organizations.Gender;
import com.springboot.Model.Organizations.Religion;
import com.springboot.Repository.Organization.CasteRepository;
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
public class CasteServiceImp implements CasteService{

    private final CasteRepository casteRepository;

    @Override
    public ResponseEntity<?> saveUpdateCaste(Map<String, Object> param, HttpServletRequest request) {
        Long id = Utilities.longValue(param.get("id"));
        String name = Utilities.stringValue(param.get("name"));

        if(name.isEmpty()) return ApiResponse.apiValidation("Caste is required.");

        Caste existingCaste =  casteRepository.findByName(name);
        if(existingCaste != null && existingCaste.getName().equals(name) && !(existingCaste.getId().equals(id))) return ApiResponse.apiValidation("Caste is already exist.");

        Caste caste = id == null ? new Caste() : casteRepository.findById(id).orElseThrow(() -> new RuntimeException("Caste not found."));
        caste.setName(name);
        if(id == null){
            caste.setIsActive(true);
            caste.setCreatedBy(null);
            caste.setCreatedOn(LocalDateTime.now());
        }else{
            caste.setUpdatedBy(null);
            caste.setUpdatedOn(LocalDateTime.now());
        }
        casteRepository.save(caste);
        return ApiResponse.apiSuccess();
    }

    @Override
    public Map<String, Object> getAllCaste(Map<String, Object> param, HttpServletRequest request) {
        String fts = Utilities.stringValue(param.get("fts"));
        fts = fts.isEmpty() ? null : "%"+fts+"%";
        List<Map<String,Object>> religionList = casteRepository.getAllCaste(fts);
        return Map.of("data_array",religionList);
    }

    @Override
    public ResponseEntity<?> updateCasteStatus(Map<String, Object> param, HttpServletRequest request) {
        Long id = Utilities.longValue(param.get("id"));
        Boolean isActive = Utilities.booleanValue(param.get("isActive"));
        Caste caste = casteRepository.findById(id).orElseThrow(() -> new RuntimeException("Kindly select al least one row."));
        if(caste != null){
            caste.setIsActive(isActive);
            caste.setUpdatedBy(null);
            caste.setUpdatedOn(LocalDateTime.now());
            casteRepository.save(caste);
        }
        return ApiResponse.apiSuccess();
    }

    @Override
    public ResponseEntity<?> deleteCaste(Map<String, Object> param, HttpServletRequest request) {
        String id = Utilities.stringValue(param.get("ids"));
        if(id.isEmpty()) return ApiResponse.apiValidation("Kindly select at least one row.");
        Set<Long> ids = Utilities.getSetOfCommaValue(id);
        if(!ids.isEmpty()) casteRepository.deleteAllById(ids);
        return ApiResponse.apiSuccess();
    }
}
