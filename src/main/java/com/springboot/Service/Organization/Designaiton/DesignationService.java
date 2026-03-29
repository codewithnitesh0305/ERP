package com.springboot.Service.Organization.Designaiton;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface DesignationService {

    ResponseEntity<?> saveUpdateDesignation(Map<String,Object> param, HttpServletRequest request);
    Map<String,Object> getAllDesignation(Map<String,Object> param,HttpServletRequest request);
    ResponseEntity<?> updateDesignationStatus(Map<String,Object> param,HttpServletRequest request);
    ResponseEntity<?> deleteDesignation(Map<String,Object> param,HttpServletRequest request);
}
