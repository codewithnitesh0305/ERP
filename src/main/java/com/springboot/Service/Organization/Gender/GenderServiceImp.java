package com.springboot.Service.Organization.Gender;

import com.springboot.Model.Organizations.Caste;
import com.springboot.Model.Organizations.Gender;
import com.springboot.Repository.Organization.GenderRepository;
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
public class GenderServiceImp implements GenderService{

    private final GenderRepository genderRepository;

    @Override
    public ResponseEntity<?> saveUpdateGender(Map<String, Object> param, HttpServletRequest request) {
        Long id = Utilities.longValue(param.get("id"));
        String name = Utilities.stringValue(param.get("name"));

        if(name.isEmpty()) return ApiResponse.apiValidation("Gender is required.");

        Gender existingGender =  genderRepository.findByName(name);
        if(existingGender != null && existingGender.getName().equals(name) && !(existingGender.getId().equals(id))) return ApiResponse.apiValidation("Gender is already exist.");

        Gender gender = id == null ? new Gender() : genderRepository.findById(id).orElseThrow(() -> new RuntimeException("Gender not found."));
        gender.setName(name);
        if(id == null){
            gender.setIsActive(true);
            gender.setCreatedBy(null);
            gender.setCreatedOn(LocalDateTime.now());
        }else{
            gender.setUpdatedBy(null);
            gender.setUpdatedOn(LocalDateTime.now());
        }
        genderRepository.save(gender);
        return ApiResponse.apiSuccess();
    }

    @Override
    public Map<String, Object> getAllGender(Map<String, Object> param, HttpServletRequest request) {
        String fts = Utilities.stringValue(param.get("fts"));
        fts = fts.isEmpty() ? null : "%"+fts+"%";
        List<Map<String,Object>> genderList = genderRepository.getAllGenders(fts);
        return Map.of("data_array",genderList);
    }

    @Override
    public ResponseEntity<?> updateGenderStatus(Map<String, Object> param, HttpServletRequest request) {
        Long id = Utilities.longValue(param.get("id"));
        Boolean isActive = Utilities.booleanValue(param.get("isActive"));
        Gender gender = genderRepository.findById(id).orElseThrow(() -> new RuntimeException("Kindly select al least one row."));
        if(gender != null){
            gender.setIsActive(isActive);
            gender.setUpdatedBy(null);
            gender.setUpdatedOn(LocalDateTime.now());
            genderRepository.save(gender);
        }
        return ApiResponse.apiSuccess();
    }

    @Override
    public ResponseEntity<?> deleteGender(Map<String, Object> param, HttpServletRequest request) {
        String id = Utilities.stringValue(param.get("ids"));
        if(id.isEmpty()) return ApiResponse.apiValidation("Kindly select at least one row.");
        Set<Long> ids = Utilities.getSetOfCommaValue(id);
        if(!ids.isEmpty()) genderRepository.deleteAllById(ids);
        return ApiResponse.apiSuccess();
    }
}
