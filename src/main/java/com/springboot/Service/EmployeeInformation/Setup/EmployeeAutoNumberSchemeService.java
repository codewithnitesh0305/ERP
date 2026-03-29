package com.springboot.Service.EmployeeInformation.Setup;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface EmployeeAutoNumberSchemeService {

    ResponseEntity<?> saveUpdateAutoNumberScheme(Map<String,Object> param, HttpServletRequest request);
    Map<String,Object> getAutoNumberScheme(Map<String,Object> param,HttpServletRequest request);
    ResponseEntity<?> updateAutoNumberSchemeStatus(Map<String,Object> param,HttpServletRequest request);
    ResponseEntity<?> deleteAutoNumberScheme(Map<String,Object> param,HttpServletRequest request);
}
