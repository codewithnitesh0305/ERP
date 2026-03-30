package com.springboot.Service.EmployeeInformation.Setup;

import com.springboot.Model.EmployeeInformation.Setup.EmployeeType;
import com.springboot.Model.Organizations.BloodGroup;
import com.springboot.Repository.EmployeeInformation.Setup.EmployeeTypeRepository;
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
public class EmployeeTypeServiceImp implements EmployeeTypeService{

    private final EmployeeTypeRepository  employeeTypeRepository;

    @Override
    public ResponseEntity<?> saveUpdateEmployeeType(Map<String, Object> parma, HttpServletRequest request) {
        try{
            Long id = Utilities.longValue(parma.get("id"));
            String name = Utilities.stringValue(parma.get("name"));

            if(name.isEmpty()) return ApiResponse.apiValidation("Blood Group is required.");

            EmployeeType existingEmployeeType = employeeTypeRepository.findByName(name);
            if(existingEmployeeType != null && existingEmployeeType.getName().equals(name) && !existingEmployeeType.getId().equals(id)) return ApiResponse.apiValidation("Employee already exist.");

            EmployeeType employeeType = id == null ? new EmployeeType() : employeeTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("Blood group not fount."));
            employeeType.setName(name);
            if(id == null){
                employeeType.setCreatedBy(null);
                employeeType.setCreatedOn(Utilities.getCurrentDateTime());
            }else{
                employeeType.setUpdateBy(null);
                employeeType.setUpdatedOn(Utilities.getCurrentDateTime());
            }
            employeeTypeRepository.save(employeeType);
        }catch (Exception ex){
            ApiResponse.apiFailure(ex);
        }
        return ApiResponse.apiSuccess();
    }

    @Override
    public Map<String, Object> getAllEmployeeType(Map<String, Object> param, HttpServletRequest request) {
        Map<String,Object> result_map = new LinkedHashMap<>();
        String fts = Utilities.stringValue(param.get("fts"));
        fts = fts.isEmpty() ? null : "%"+fts+"%";
        List<Map<String,Object>> employeeTypeList = employeeTypeRepository.getAllEmployeeType(fts);
        result_map.put("employeeTypeList",employeeTypeList);
        return result_map;
    }

    @Override
    public ResponseEntity<?> deleteEmployeeType(Map<String, Object> param, HttpServletRequest request) {
        try{
            String employeeTypeIds = Utilities.stringValue(param.get("ids"));
            if(employeeTypeIds.isEmpty()) return ApiResponse.apiValidation("Kindly select at least on row.");
            Set<Long> idsSet = Utilities.getSetOfCommaValue(employeeTypeIds);
            if(!idsSet.isEmpty()) employeeTypeRepository.deleteAllById(idsSet);
        }catch (Exception ex){
            return ApiResponse.apiFailure(ex);
        }
        return ApiResponse.apiSuccess();
    }
}
