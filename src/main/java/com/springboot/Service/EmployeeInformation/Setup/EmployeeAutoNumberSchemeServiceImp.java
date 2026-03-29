package com.springboot.Service.EmployeeInformation.Setup;

import com.springboot.Model.EmployeeInformation.Setup.EmployeeAutoNumber;
import com.springboot.Repository.CustomRepo.CustomRepo;
import com.springboot.Repository.EmployeeInformation.Setup.EmployeeAutoNumberSchemeRepository;
import com.springboot.Repository.Organization.FinancialYearRepository;
import com.springboot.Utility.ApiResponse;
import com.springboot.Utility.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeAutoNumberSchemeServiceImp implements EmployeeAutoNumberSchemeService{

    private final EmployeeAutoNumberSchemeRepository employeeAutoNumberSchemeRepository;
    private final CustomRepo customRepo;

    @Override
    public ResponseEntity<?> saveUpdateAutoNumberScheme(Map<String, Object> param, HttpServletRequest request) {
        try{
            Long id = Utilities.longValue(param.get("id"));
            String prefix = Utilities.stringValue(param.get("prefix"));
            Integer startingNo = Utilities.integerValue(param.get("startingNo"));
            String startingNoStr = Utilities.stringValue(param.get("startingNo"));
            String postfix = Utilities.stringValue(param.get("postfix"));
            Long financialYearId = Utilities.longValue(param.get("financialYearId"));
            financialYearId = financialYearId == -1 ? null : financialYearId;

            if(startingNo == null) return ApiResponse.apiValidation("Starting No. is required.");

             EmployeeAutoNumber existingAutoNumberScheme =  employeeAutoNumberSchemeRepository.getEmployeeAutoNoSchemeByFinancialYearId(financialYearId);
             if(existingAutoNumberScheme != null && !existingAutoNumberScheme.getId().equals(id)) return ApiResponse.apiValidation("Auto Number Scheme of this financial year already exist.");

             EmployeeAutoNumber employeeAutoNumber =  id == null ? new EmployeeAutoNumber() : employeeAutoNumberSchemeRepository.findById(id).orElseThrow(() -> new RuntimeException("Auto number scheme not founr."));
             if(id == null){
                 employeeAutoNumber.setIsActive(true);
                 employeeAutoNumber.setCreateBy(null);
                 employeeAutoNumber.setCreatedOn(Utilities.getCurrentDateTime());
             }else{
                 employeeAutoNumber.setUpdatedBy(null);
                 employeeAutoNumber.setUpdatedOn(Utilities.getCurrentDateTime());
             }
            employeeAutoNumber.setPrefix(prefix);
            employeeAutoNumber.setStartingNo(startingNo);
            employeeAutoNumber.setPostfix(postfix);
            employeeAutoNumber.setStartingNoSize(startingNoStr.length());
            employeeAutoNumber.setFinancialYearId(financialYearId);
            employeeAutoNumberSchemeRepository.save(employeeAutoNumber);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return ApiResponse.apiSuccess();
    }

    @Override
    public Map<String, Object> getAutoNumberScheme(Map<String, Object> param, HttpServletRequest request) {
        Map<String,Object> result_map = new LinkedHashMap<>();
        try{
            List<Map<String,Object>> data_array = new ArrayList<>();
            Map<String,Object> allFinancialYearList = new LinkedHashMap<>();
            allFinancialYearList.put("label","All");
            allFinancialYearList.put("value",-1);
            List<Map<String,Object>> financialYearList = customRepo.getAllFinancialYear(null,null);
            financialYearList.add(0,allFinancialYearList);

            Map<Long, Object> financialYearMap = financialYearList.stream().collect(Collectors.toMap(f -> Utilities.longValue(f.get("value")), f -> Utilities.stringValue(f.get("label"))));
            List<EmployeeAutoNumber> employeeAutoNumberList = employeeAutoNumberSchemeRepository.findByDeletedOnIsNullOrderByIdDesc();
            if(employeeAutoNumberList != null && !employeeAutoNumberList.isEmpty()){
                for(EmployeeAutoNumber employeeAutoNumber : employeeAutoNumberList){
                    Map<String,Object> data = new LinkedHashMap<>();
                    Long id = employeeAutoNumber.getId();
                    String prefix = employeeAutoNumber.getPrefix();
                    String postfix = employeeAutoNumber.getPostfix();
                    Boolean isActive = employeeAutoNumber.getIsActive();
                    Long financialYearId = employeeAutoNumber.getFinancialYearId();
                    Integer startingNo = employeeAutoNumber.getStartingNo();
                    Integer startingNoSize = employeeAutoNumber.getStartingNoSize();
                    Integer lastNo = employeeAutoNumber.getLastNo();

                    financialYearId = financialYearId == null ? -1 : financialYearId;
                    String financialYearName = Utilities.stringValue(financialYearMap.getOrDefault(financialYearId,"All"));
                    String startingNoStr = Utilities.getFormattedStartNumber(startingNo,startingNoSize);
                    String lastNoStr = "";
                    if(lastNo != null){
                        lastNoStr = prefix + lastNo + postfix;
                    }
                    data.put("id",id);
                    data.put("financialYearId",financialYearId);
                    data.put("financialYearName",financialYearName);
                    data.put("prefix",prefix);
                    data.put("startingNo",startingNoStr);
                    data.put("postfix",postfix);
                    data.put("lastNo",lastNoStr);
                    data.put("isActive",isActive);
                    data_array.add(data);
                }
            }
            result_map.put("financialYearList",financialYearList);
            result_map.put("autoNumberSchemeList",data_array);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return result_map;
    }

    @Override
    public ResponseEntity<?> updateAutoNumberSchemeStatus(Map<String, Object> param, HttpServletRequest request) {
        try{
            Long id =  Utilities.longValue(param.get("id"));
            Boolean isActive = Utilities.booleanValue(param.get("isActive"));
            EmployeeAutoNumber employeeAutoNumber  = employeeAutoNumberSchemeRepository.findById(id).orElseThrow(() -> new RuntimeException("Auto number scheme not fount."));
            employeeAutoNumber.setIsActive(isActive);
            employeeAutoNumber.setUpdatedBy(null);
            employeeAutoNumber.setUpdatedOn(Utilities.getCurrentDateTime());
            employeeAutoNumberSchemeRepository.save(employeeAutoNumber);
            return ApiResponse.apiSuccess();
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public ResponseEntity<?> deleteAutoNumberScheme(Map<String, Object> param, HttpServletRequest request) {
        try{
            Long id =  Utilities.longValue(param.get("id"));
            EmployeeAutoNumber employeeAutoNumber  = employeeAutoNumberSchemeRepository.findById(id).orElseThrow(() -> new RuntimeException("Auto number scheme not fount."));
            employeeAutoNumber.setDeletedBy(null);
            employeeAutoNumber.setDeletedOn(Utilities.getCurrentDateTime());
            employeeAutoNumberSchemeRepository.save(employeeAutoNumber);
            return ApiResponse.apiSuccess();
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
