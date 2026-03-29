package com.springboot.Service.Organization.FinancialYear;

import com.springboot.Model.Organizations.FinancialYear;
import com.springboot.Repository.Organization.FinancialYearRepository;
import com.springboot.Utility.ApiResponse;
import com.springboot.Utility.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FinancialYearServiceImp implements FinancialYearService{

    private final FinancialYearRepository financialYearRepository;

    @Override
    public ResponseEntity<?> saveUpdateFinancialYear(Map<String, Object> param, HttpServletRequest request) {
        Long id = Utilities.longValue(param.get("id"));
        String year = Utilities.stringValue(param.get("year"));
        String fromDateStr = Utilities.getUSDateFromIndianDate(Utilities.stringValue(param.get("fromDate")));
        String toDateStr = Utilities.getUSDateFromIndianDate(Utilities.stringValue(param.get("toDate")));
        String financialYearShorName = Utilities.stringValue(param.get("shortName"));

        if(year.isEmpty()) return ApiResponse.apiValidation("Please enter year.");
        if(fromDateStr.isEmpty()) return ApiResponse.apiValidation("Please enter from date.");
        if(toDateStr.isEmpty()) return ApiResponse.apiValidation("Please enter to date.");

        LocalDate fromDate = LocalDate.parse(fromDateStr);
        LocalDate toDate = LocalDate.parse(toDateStr);
        if(fromDate.isAfter(toDate)) return ApiResponse.apiValidation("To date should be greater tha from date");

        FinancialYear existingFinancialYear = financialYearRepository.findByFinancialYearName(year);
        if(existingFinancialYear != null && existingFinancialYear.getFinancialYearName().equals(year) && !(existingFinancialYear.getId().equals(id)) ) return ApiResponse.apiValidation("Financial year already exist");

        FinancialYear financialYear = id == null ? new FinancialYear() : financialYearRepository.findById(id).orElseThrow(() -> new RuntimeException("Financial year not fount."));
        if(id != null){
            financialYear.setUpdatedBy(null);
            financialYear.setUpdatedOn(Utilities.getCurrentDateTime());
        }else {
            financialYear.setIsActive(true);
            financialYear.setCreatedBy(null);
            financialYear.setCreateOn(Utilities.getCurrentDateTime());
        }
        financialYear.setFinancialYearName(year);
        financialYear.setFinancialYearShortName(financialYearShorName);
        financialYear.setFromDate(fromDate);
        financialYear.setToDate(toDate);
        financialYearRepository.save(financialYear);
        return ApiResponse.apiSuccess();
    }

    @Override
    public Map<String, Object> getAllFinancialYear(Map<String, Object> param, HttpServletRequest request) {
        Map<String,Object> result_map = new LinkedHashMap<>();
        String fts = Utilities.stringValue(param.get("fts"));
        List<Map<String,Object>> financialYearList = financialYearRepository.getFinancialYear(fts);
        result_map.put("data_array",financialYearList);
        return result_map;
    }

    @Override
    public ResponseEntity<?> updateFinancialYearStatus(Map<String, Object> param, HttpServletRequest request) {
        Long id = Utilities.longValue(param.get("id"));
        Boolean isActive = Utilities.booleanValue(param.get("isActive"));
        FinancialYear financialYear =  financialYearRepository.findById(id).orElseThrow(() -> new RuntimeException("Financial year not fount"));
        financialYear.setIsActive(isActive);
        financialYear.setUpdatedOn(Utilities.getCurrentDateTime());
        financialYear.setUpdatedBy(null);
        financialYearRepository.save(financialYear);
        return ApiResponse.apiSuccess();
    }

    @Override
    public ResponseEntity<?> deleteFinancialYear(Map<String, Object> param, HttpServletRequest request) {
        Long id = Utilities.longValue(param.get("id"));
        FinancialYear financialYear =  financialYearRepository.findById(id).orElseThrow(() -> new RuntimeException("Financial year not fount"));
        financialYearRepository.delete(financialYear);
        return null;
    }
}
