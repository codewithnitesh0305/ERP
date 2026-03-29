package com.springboot.Service.Organization.FinancialYear;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface FinancialYearService {

    ResponseEntity<?> saveUpdateFinancialYear(Map<String,Object> param, HttpServletRequest request);
    Map<String,Object> getAllFinancialYear(Map<String,Object> param, HttpServletRequest request);
    ResponseEntity<?> updateFinancialYearStatus(Map<String,Object> param,HttpServletRequest request);
    ResponseEntity<?> deleteFinancialYear(Map<String,Object> param,HttpServletRequest request);
}
