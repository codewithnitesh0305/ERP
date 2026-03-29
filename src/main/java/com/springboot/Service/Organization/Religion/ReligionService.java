package com.springboot.Service.Organization.Religion;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ReligionService {

    ResponseEntity<?> saveUpdateReligion(Map<String,Object> param, HttpServletRequest request);
    Map<String,Object> getAllReligion(Map<String,Object> param, HttpServletRequest request);
    ResponseEntity<?> updateReligionStatus(Map<String,Object> param,HttpServletRequest request);
    ResponseEntity<?> deleteReligion(Map<String,Object> param,HttpServletRequest request);
}
