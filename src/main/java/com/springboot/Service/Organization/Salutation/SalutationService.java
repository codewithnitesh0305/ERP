package com.springboot.Service.Organization.Salutation;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface SalutationService {

    ResponseEntity<?> saveUpdateSalutation(Map<String,Object> param, HttpServletRequest request);
    Map<String,Object> getAllSalutation(Map<String,Object> param,HttpServletRequest request);
    ResponseEntity<?> updateSalutationStatus(Map<String,Object> param,HttpServletRequest request);
    ResponseEntity<?> deleteSalutation(Map<String,Object> param,HttpServletRequest request);
}
