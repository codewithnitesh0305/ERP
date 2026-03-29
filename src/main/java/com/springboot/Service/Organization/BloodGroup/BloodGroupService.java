package com.springboot.Service.Organization.BloodGroup;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface BloodGroupService {

    public ResponseEntity<?> saveUpdateBloodGroup(Map<String,Object> parma, HttpServletRequest request);
    public Map<String,Object> getAllBloodGroup(Map<String,Object> param, HttpServletRequest request);
    public ResponseEntity<?> deleteBloodGroup(Map<String,Object> param, HttpServletRequest request);
    public ResponseEntity<?> updateBloodGroupStatus(Map<String,Object> param, HttpServletRequest request);
}
