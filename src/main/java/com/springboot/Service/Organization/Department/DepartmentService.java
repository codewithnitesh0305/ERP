package com.springboot.Service.Organization.Department;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface DepartmentService {

    public ResponseEntity<?> saveUpdateDepartment(Map<String,Object> parma, HttpServletRequest request);
    public Map<String,Object> getAllDepartment(Map<String,Object> param, HttpServletRequest request);
    public ResponseEntity<?> deleteDepartment(Map<String,Object> param, HttpServletRequest request);
    public ResponseEntity<?> updateDepartmentStatus(Map<String,Object> param, HttpServletRequest request);

}
