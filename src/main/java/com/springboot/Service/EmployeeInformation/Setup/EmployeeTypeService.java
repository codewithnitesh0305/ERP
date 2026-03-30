package com.springboot.Service.EmployeeInformation.Setup;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface EmployeeTypeService {

    public ResponseEntity<?> saveUpdateEmployeeType(Map<String,Object> parma, HttpServletRequest request);
    public Map<String,Object> getAllEmployeeType(Map<String,Object> param, HttpServletRequest request);
    public ResponseEntity<?> deleteEmployeeType(Map<String,Object> param, HttpServletRequest request);
}
