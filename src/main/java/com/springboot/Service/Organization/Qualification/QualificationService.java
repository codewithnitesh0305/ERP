package com.springboot.Service.Organization.Qualification;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface QualificationService {

    ResponseEntity<?> saveUpdateQualification(Map<String,Object> param, HttpServletRequest request);
    Map<String,Object> getAllQualification(Map<String,Object> param, HttpServletRequest request);
    ResponseEntity<?> updateQualificationStatus(Map<String,Object> param,HttpServletRequest request);
    ResponseEntity<?> deleteQualification(Map<String,Object> param,HttpServletRequest request);
}
