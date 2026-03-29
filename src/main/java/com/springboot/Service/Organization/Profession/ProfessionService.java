package com.springboot.Service.Organization.Profession;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ProfessionService {

    ResponseEntity<?> saveUpdateProfession(Map<String,Object> param, HttpServletRequest request);
    Map<String,Object> getAllProfession(Map<String,Object> param, HttpServletRequest request);
    ResponseEntity<?> updateProfessionStatus(Map<String,Object> param,HttpServletRequest request);
    ResponseEntity<?> deleteProfession(Map<String,Object> param,HttpServletRequest request);
}
