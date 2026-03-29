package com.springboot.Service.Organization.Qualification;

import com.springboot.Model.Organizations.Gender;
import com.springboot.Model.Organizations.Qualification;
import com.springboot.Repository.Organization.QualificationRepository;
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
public class QualificationServiceImp implements QualificationService{

    private final QualificationRepository qualificationRepository;

    @Override
    public ResponseEntity<?> saveUpdateQualification(Map<String, Object> param, HttpServletRequest request) {
        Long id = Utilities.longValue(param.get("id"));
        String name = Utilities.stringValue(param.get("name"));

        if(name.isEmpty()) return ApiResponse.apiValidation("Qualification is required.");

        Qualification existingQualification =  qualificationRepository.findByName(name);
        if(existingQualification != null && existingQualification.getName().equals(name) && !(existingQualification.getId().equals(id))) return ApiResponse.apiValidation("Qualification is already exist.");

        Qualification qualification = id == null ? new Qualification() : qualificationRepository.findById(id).orElseThrow(() -> new RuntimeException("Gender not found."));
        qualification.setName(name);
        if(id == null){
            qualification.setIsActive(true);
            qualification.setCreatedBy(null);
            qualification.setCreatedOn(LocalDateTime.now());
        }else{
            qualification.setUpdatedBy(null);
            qualification.setUpdatedOn(LocalDateTime.now());
        }
        qualificationRepository.save(qualification);
        return ApiResponse.apiSuccess();
    }

    @Override
    public Map<String, Object> getAllQualification(Map<String, Object> param, HttpServletRequest request) {
        String fts = Utilities.stringValue(param.get("fts"));
        fts = fts.isEmpty() ? null : "%"+fts+"%";
        List<Map<String,Object>> qualificationList = qualificationRepository.getAllQualification(fts);
        return Map.of("data_array",qualificationList);
    }

    @Override
    public ResponseEntity<?> updateQualificationStatus(Map<String, Object> param, HttpServletRequest request) {
        Long id = Utilities.longValue(param.get("id"));
        Boolean isActive = Utilities.booleanValue(param.get("isActive"));
        Qualification qualification = qualificationRepository.findById(id).orElseThrow(() -> new RuntimeException("Kindly select al least one row."));
        if(qualification != null){
            qualification.setIsActive(isActive);
            qualification.setUpdatedBy(null);
            qualification.setUpdatedOn(LocalDateTime.now());
            qualificationRepository.save(qualification);
        }
        return ApiResponse.apiSuccess();
    }

    @Override
    public ResponseEntity<?> deleteQualification(Map<String, Object> param, HttpServletRequest request) {
        String id = Utilities.stringValue(param.get("ids"));
        if(id.isEmpty()) return ApiResponse.apiValidation("Kindly select at least one row.");
        Set<Long> ids = Utilities.getSetOfCommaValue(id);
        if(!ids.isEmpty()) qualificationRepository.deleteAllById(ids);
        return ApiResponse.apiSuccess();
    }
}
