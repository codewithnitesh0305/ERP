package com.springboot.Service.Organization.BloodGroup;

import com.springboot.Model.Organizations.BloodGroup;
import com.springboot.Model.Organizations.Department;
import com.springboot.Repository.Organization.BloodGroupRepository;
import com.springboot.Utility.ApiResponse;
import com.springboot.Utility.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BloodGroupServiceImp implements BloodGroupService{

    private final BloodGroupRepository bloodGroupRepository;

    @Override
    public ResponseEntity<?> saveUpdateBloodGroup(Map<String, Object> parma, HttpServletRequest request) {
        try{
            Long id = Utilities.longValue(parma.get("id"));
            String name = Utilities.stringValue(parma.get("name"));

            if(name.isEmpty()) return ApiResponse.apiValidation("Blood Group is required.");

            BloodGroup existingBloodGroup = bloodGroupRepository.findByName(name);
            if(existingBloodGroup != null && existingBloodGroup.getName().equals(name) && !existingBloodGroup.getId().equals(id)) return ApiResponse.apiValidation("Department already exist.");

            BloodGroup bloodGroup = id == null ? new BloodGroup() : bloodGroupRepository.findById(id).orElseThrow(() -> new RuntimeException("Blood group not fount."));
            bloodGroup.setName(name);
            if(id == null){
                bloodGroup.setIsActive(true);
                bloodGroup.setCreatedBy(null);
                bloodGroup.setCreatedOn(LocalDateTime.now());
            }else{
                bloodGroup.setUpdatedBy(null);
                bloodGroup.setUpdatedOn(LocalDateTime.now());
            }
            bloodGroupRepository.save(bloodGroup);
        }catch (Exception ex){
            ApiResponse.apiFailure(ex);
        }
        return ApiResponse.apiSuccess();
    }

    @Override
    public Map<String, Object> getAllBloodGroup(Map<String, Object> param, HttpServletRequest request) {
        Map<String,Object> result_map = new LinkedHashMap<>();
        String fts = Utilities.stringValue(param.get("fts"));
        fts = fts.isEmpty() ? null : "%"+fts;
        List<Map<String,Object>> departmentList = bloodGroupRepository.getAllBloodGroup(fts);
        result_map.put("bloodGroupList",departmentList);
        return result_map;
    }

    @Override
    public ResponseEntity<?> deleteBloodGroup(Map<String, Object> param, HttpServletRequest request) {
        try{
            String bloodGroupIds = Utilities.stringValue(param.get("ids"));
            if(bloodGroupIds.isEmpty()) return ApiResponse.apiValidation("Kindly select at least on row.");
            Set<Long> idsSet = Utilities.getSetOfCommaValue(bloodGroupIds);
            if(!idsSet.isEmpty()) bloodGroupRepository.deleteAllById(idsSet);
        }catch (Exception ex){
            return ApiResponse.apiFailure(ex);
        }
        return ApiResponse.apiSuccess();
    }

    @Override
    public ResponseEntity<?> updateBloodGroupStatus(Map<String, Object> param, HttpServletRequest request) {
        try{
            Long bloodGroupId = Utilities.longValue(param.get("id"));
            Boolean isActive = Utilities.booleanValue(param.get("isActive"));
            BloodGroup bloodGroup = bloodGroupRepository.findById(bloodGroupId).orElseThrow(() -> new RuntimeException("Blood group not found."));
            bloodGroup.setIsActive(isActive);
            bloodGroup.setUpdatedBy(null);
            bloodGroup.setUpdatedOn(LocalDateTime.now());
            bloodGroupRepository.save(bloodGroup);
            return ApiResponse.apiSuccess();
        }catch (Exception ex){
            return ApiResponse.apiFailure(ex);
        }
    }
}
