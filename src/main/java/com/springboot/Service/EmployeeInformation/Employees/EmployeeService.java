package com.springboot.Service.EmployeeInformation.Employees;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface EmployeeService{

    ResponseEntity<?> saveUpdateEmployee(Map<String, MultipartFile> file, Map<String,Object> param, HttpServletRequest request);
    Map<String,Object> getAllEmployees(Map<String,Object> param,HttpServletRequest request);
    Map<String,Object> employeeById(Map<String,Object> param,HttpServletRequest request);
}
