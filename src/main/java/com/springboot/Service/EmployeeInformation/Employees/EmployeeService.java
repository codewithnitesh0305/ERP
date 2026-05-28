package com.springboot.Service.EmployeeInformation.Employees;

import com.springboot.Dto.EmployeeInformation.EmployeeDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface EmployeeService{

    ResponseEntity<?> saveUpdateEmployee(Map<String, MultipartFile> file, EmployeeDto employeeDto, HttpServletRequest request);
    Map<String,Object> getAllEmployees(Map<String,Object> param,HttpServletRequest request);
    Map<String,Object> employeeById(Map<String,Object> param,HttpServletRequest request);
    Map<String,Object> employeePreview(Map<String,Object> param,HttpServletRequest request);
    Map<String,Object> employeeDocumentByDepartment(Map<String,Object> param,HttpServletRequest request);
    ResponseEntity<?> resignEmployee(MultipartFile file, Map<String,Object> param,HttpServletRequest request) throws IOException;
    Map<String,Object> resignEmployeeList(Map<String,Object> param,HttpServletRequest request);
}
