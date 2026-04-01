package com.springboot.Service.EmployeeInformation.Setup;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface EmployeeDocumentService {

    public ResponseEntity<?> saveUpdateEmployeeDocument(Map<String,Object> param, HttpServletRequest request);
    public Map<String,Object> getAllEmployeeDocument(Map<String,Object> param,HttpServletRequest request);
    public ResponseEntity<?> deleteEmployeeDocument(Map<String,Object> param,HttpServletRequest request);
}
