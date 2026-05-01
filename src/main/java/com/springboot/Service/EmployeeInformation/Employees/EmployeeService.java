package com.springboot.Service.EmployeeInformation.Employees;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.hc.client5.http.entity.mime.MultipartPart;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface EmployeeService{

    ResponseEntity<?> saveUpdateEmployee(Map<String, MultipartPart> file, Map<String,Object> param, HttpServletRequest request);
    Map<String,Object> getAllEmployees(Map<String,Object> param,HttpServletRequest request);
    Map<String,Object> employeeById(Map<String,Object> param,HttpServletRequest request);
}
