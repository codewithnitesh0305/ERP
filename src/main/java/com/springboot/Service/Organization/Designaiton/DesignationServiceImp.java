package com.springboot.Service.Organization.Designaiton;

import com.cloudinary.Api;
import com.springboot.Model.Organizations.Designation;
import com.springboot.Repository.Organization.DesignationRepository;
import com.springboot.Utility.ApiResponse;
import com.springboot.Utility.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.UnableToRegisterMBeanException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class DesignationServiceImp implements DesignationService {

    private final DesignationRepository designationRepository;

    @Override
    public ResponseEntity<?> saveUpdateDesignation(Map<String, Object> param, HttpServletRequest request) {
        Long id = Utilities.longValue(param.get("id"));
        String name = Utilities.stringValue(param.get("name"));

        if(name.isEmpty()) return ApiResponse.apiValidation("Designation is required.");

        Designation existingDesignation = designationRepository.findByName(name);
        if(existingDesignation != null && existingDesignation.getName().equals(name) && !(existingDesignation.getId().equals(id)))
            return ApiResponse.apiValidation("Designation already exist.");


        Designation designation = id == null ? new Designation() : designationRepository.findById(id).orElseThrow(() -> new RuntimeException("Designation not found."));
        designation.setName(name);
        if(id == null){
            designation.setIsActive(true);
            designation.setCreatedBy(null);
            designation.setCreatedOn(LocalDateTime.now());
        }else{
            designation.setUpdatedBy(null);
            designation.setUpdatedOn(LocalDateTime.now());
        }
        designationRepository.save(designation);
        return ApiResponse.apiSuccess();
    }

    @Override
    public Map<String, Object> getAllDesignation(Map<String, Object> param, HttpServletRequest request) {
        String fts = Utilities.stringValue(param.get("fts"));
        fts = fts.isEmpty() ? null : "%"+fts+"%";
        List<Map<String,Object>> designationList = designationRepository.getAllDesignation(fts);
        return Map.of("data_array",designationList);
    }

    @Override
    public ResponseEntity<?> updateDesignationStatus(Map<String, Object> param, HttpServletRequest request) {
        Long id = Utilities.longValue(param.get("id"));
        Boolean isActive = Utilities.booleanValue(param.get("isActive"));
        Designation designation = designationRepository.findById(id).orElseThrow(() -> new RuntimeException("Kindly select al least one row."));
        if(designation != null){
            designation.setIsActive(isActive);
            designation.setUpdatedBy(null);
            designation.setUpdatedOn(LocalDateTime.now());
            designationRepository.save(designation);
        }
        return ApiResponse.apiSuccess();
    }

    @Override
    public ResponseEntity<?> deleteDesignation(Map<String, Object> param, HttpServletRequest request) {
        String id = Utilities.stringValue(param.get("ids"));
        if(id.isEmpty()) return ApiResponse.apiValidation("Kindly select at least one row.");
        Set<Long> ids = Utilities.getSetOfCommaValue(id);
        if(!ids.isEmpty()) designationRepository.deleteAllById(ids);
        return ApiResponse.apiSuccess();
    }
}
