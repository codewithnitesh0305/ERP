package com.springboot.Service.Organization.Profession;

import com.springboot.Model.Organizations.Profession;
import com.springboot.Model.Organizations.Qualification;
import com.springboot.Repository.Organization.ProfessionRepository;
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
public class ProfessionServiceImp implements ProfessionService{

    private final ProfessionRepository professionRepository;

    @Override
    public ResponseEntity<?> saveUpdateProfession(Map<String, Object> param, HttpServletRequest request) {
        Long id = Utilities.longValue(param.get("id"));
        String name = Utilities.stringValue(param.get("name"));

        if(name.isEmpty()) return ApiResponse.apiValidation("Qualification is required.");

        Profession existingProfession =  professionRepository.findByName(name);
        if(existingProfession != null && existingProfession.getName().equals(name) && !(existingProfession.getId().equals(id))) return ApiResponse.apiValidation("Qualification is already exist.");

        Profession profession = id == null ? new Profession() : professionRepository.findById(id).orElseThrow(() -> new RuntimeException("Profession not found."));
        profession.setName(name);
        if(id == null){
            profession.setIsActive(true);
            profession.setCreatedBy(null);
            profession.setCreatedOn(LocalDateTime.now());
        }else{
            profession.setUpdatedBy(null);
            profession.setUpdatedOn(LocalDateTime.now());
        }
        professionRepository.save(profession);
        return ApiResponse.apiSuccess();
    }

    @Override
    public Map<String, Object> getAllProfession(Map<String, Object> param, HttpServletRequest request) {
        String fts = Utilities.stringValue(param.get("fts"));
        fts = fts.isEmpty() ? null : "%"+fts+"%";
        List<Map<String,Object>> professionList = professionRepository.getAllProfession(fts);
        return Map.of("data_array",professionList);
    }

    @Override
    public ResponseEntity<?> updateProfessionStatus(Map<String, Object> param, HttpServletRequest request) {
        Long id = Utilities.longValue(param.get("id"));
        Boolean isActive = Utilities.booleanValue(param.get("isActive"));
        Profession profession = professionRepository.findById(id).orElseThrow(() -> new RuntimeException("Kindly select al least one row."));
        if(profession != null){
            profession.setIsActive(isActive);
            profession.setUpdatedBy(null);
            profession.setUpdatedOn(LocalDateTime.now());
            professionRepository.save(profession);
        }
        return ApiResponse.apiSuccess();
    }

    @Override
    public ResponseEntity<?> deleteProfession(Map<String, Object> param, HttpServletRequest request) {
        String id = Utilities.stringValue(param.get("ids"));
        if(id.isEmpty()) return ApiResponse.apiValidation("Kindly select at least one row.");
        Set<Long> ids = Utilities.getSetOfCommaValue(id);
        if(!ids.isEmpty()) professionRepository.deleteAllById(ids);
        return ApiResponse.apiSuccess();
    }
}
