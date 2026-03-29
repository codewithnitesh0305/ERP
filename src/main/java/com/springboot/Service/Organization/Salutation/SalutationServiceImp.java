package com.springboot.Service.Organization.Salutation;

import com.springboot.Model.Organizations.Designation;
import com.springboot.Model.Organizations.Religion;
import com.springboot.Model.Organizations.Salutation;
import com.springboot.Repository.Organization.SalutationRepository;
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
public class SalutationServiceImp implements SalutationService{

    private final SalutationRepository salutationRepository;

    @Override
    public ResponseEntity<?> saveUpdateSalutation(Map<String, Object> param, HttpServletRequest request) {
        Long id = Utilities.longValue(param.get("id"));
        String name = Utilities.stringValue(param.get("name"));

        if(name.isEmpty()) return ApiResponse.apiValidation("Salutation is required.");

        Salutation existingSalutation = salutationRepository.findByName(name);
        if(existingSalutation != null && existingSalutation.getName().equals(name) && !(existingSalutation.getId().equals(id))) return ApiResponse.apiValidation("Salutation already exist.");

        Salutation salutation = id == null ? new Salutation() : salutationRepository.findById(id).orElseThrow(() -> new RuntimeException("Salutation not found."));
        salutation.setName(name);
        if(id == null){
            salutation.setIsActive(true);
            salutation.setCreatedBy(null);
            salutation.setCreatedOn(LocalDateTime.now());
        }else{
            salutation.setUpdatedBy(null);
            salutation.setUpdatedOn(LocalDateTime.now());
        }
        salutationRepository.save(salutation);
        return ApiResponse.apiSuccess();
    }

    @Override
    public Map<String, Object> getAllSalutation(Map<String, Object> param, HttpServletRequest request) {
        String fts = Utilities.stringValue(param.get("fts"));
        fts = fts.isEmpty() ? null : "%"+fts+"%";
        List<Map<String,Object>> salutationList = salutationRepository.getAllSalutation(fts);
        return Map.of("data_array",salutationList);
    }

    @Override
    public ResponseEntity<?> updateSalutationStatus(Map<String, Object> param, HttpServletRequest request) {
        Long id = Utilities.longValue(param.get("id"));
        Boolean isActive = Utilities.booleanValue(param.get("isActive"));
        Salutation salutation = salutationRepository.findById(id).orElseThrow(() -> new RuntimeException("Kindly select al least one row."));
        if(salutation != null){
            salutation.setIsActive(isActive);
            salutation.setUpdatedBy(null);
            salutation.setUpdatedOn(LocalDateTime.now());
            salutationRepository.save(salutation);
        }
        return ApiResponse.apiSuccess();
    }

    @Override
    public ResponseEntity<?> deleteSalutation(Map<String, Object> param, HttpServletRequest request) {
        String id = Utilities.stringValue(param.get("ids"));
        if(id.isEmpty()) return ApiResponse.apiValidation("Kindly select at least one row.");
        Set<Long> ids = Utilities.getSetOfCommaValue(id);
        if(!ids.isEmpty()) salutationRepository.deleteAllById(ids);
        return ApiResponse.apiSuccess();
    }
}
