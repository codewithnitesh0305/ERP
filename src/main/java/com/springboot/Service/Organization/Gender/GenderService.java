package com.springboot.Service.Organization.Gender;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface GenderService {

    ResponseEntity<?> saveUpdateGender(Map<String,Object> param, HttpServletRequest request);
    Map<String,Object> getAllGender(Map<String,Object> param, HttpServletRequest request);
    ResponseEntity<?> updateGenderStatus(Map<String,Object> param,HttpServletRequest request);
    ResponseEntity<?> deleteGender(Map<String,Object> param,HttpServletRequest request);
}
