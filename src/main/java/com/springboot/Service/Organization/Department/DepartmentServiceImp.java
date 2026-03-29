package com.springboot.Service.Organization.Department;

import com.cloudinary.Api;
import com.springboot.Model.Organizations.Department;
import com.springboot.Repository.Organization.DepartmentRepository;
import com.springboot.Utility.ApiResponse;
import com.springboot.Utility.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class DepartmentServiceImp implements DepartmentService{

    private DepartmentRepository departmentRepository;

    @Override
    public ResponseEntity<?> saveUpdateDepartment(Map<String, Object> parma, HttpServletRequest request) {
        try{
            Long departmentId = Utilities.longValue(parma.get("id"));
            String departmentName = Utilities.stringValue(parma.get("departmentName"));

            if(departmentName.isEmpty()){
                return ApiResponse.apiValidation("Department is required.");
            }

            Department existingDepartment = departmentRepository.findByName(departmentName);
            if(existingDepartment != null && existingDepartment.getName().equals(departmentName) && !existingDepartment.getId().equals(departmentId)){
                return ApiResponse.apiValidation("Department already exist.");
            }

            Department department = departmentId == null ? new Department() : departmentRepository.findById(departmentId).orElseThrow(() -> new RuntimeException("Department not fount."));
            department.setName(departmentName);
            if(departmentId == null){
                department.setIsActive(true);
                department.setCreatedBy(null);
                department.setCreatedOn(LocalDateTime.now());
            }else{
                 department.setUpdatedBy(null);
                 department.setUpdatedOn(LocalDateTime.now());
            }
            departmentRepository.save(department);
        }catch (Exception ex){
            ApiResponse.apiFailure(ex);
        }
        return ApiResponse.apiSuccess();
    }

    @Override
    public Map<String, Object> getAllDepartment(Map<String, Object> param, HttpServletRequest request) {
        Map<String,Object> result_map = new LinkedHashMap<>();
        String fts = Utilities.stringValue(param.get("fts"));
        List<Map<String,Object>> departmentList = departmentRepository.getAllDepartment(fts);
        result_map.put("departmentList",departmentList);
        return result_map;
    }

    @Override
    public ResponseEntity<?> deleteDepartment(Map<String, Object> param, HttpServletRequest request) {
        try{
            String departmentIds = Utilities.stringValue(param.get("ids"));
            if(departmentIds.isEmpty()) return ApiResponse.apiValidation("Kindly select at least on row.");
            Set<Long> idsSet = Utilities.getSetOfCommaValue(departmentIds);
            if(!idsSet.isEmpty()){
                departmentRepository.deleteAllById(idsSet);
            }
        }catch (Exception ex){
           return ApiResponse.apiFailure(ex);
        }
        return ApiResponse.apiSuccess();
    }

    @Override
    public ResponseEntity<?> updateDepartmentStatus(Map<String, Object> param, HttpServletRequest request) {
        try{
            Long departmentId = Utilities.longValue(param.get("id"));
            Boolean isActive = Utilities.booleanValue(param.get("isActive"));
            Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new RuntimeException("Department not found."));
            department.setIsActive(isActive);
            department.setUpdatedBy(null);
            department.setUpdatedOn(LocalDateTime.now());
            departmentRepository.save(department);
            return ApiResponse.apiSuccess();
        }catch (Exception ex){
            return ApiResponse.apiFailure(ex);
        }
    }

}
