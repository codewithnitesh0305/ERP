package com.springboot.Service.Organization.Caste;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CasteService {

    ResponseEntity<?> saveUpdateCaste(Map<String,Object> param, HttpServletRequest request);
    Map<String,Object> getAllCaste(Map<String,Object> param, HttpServletRequest request);
    ResponseEntity<?> updateCasteStatus(Map<String,Object> param,HttpServletRequest request);
    ResponseEntity<?> deleteCaste(Map<String,Object> param,HttpServletRequest request);
}
