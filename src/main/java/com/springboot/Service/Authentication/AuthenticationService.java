package com.springboot.Service.Authentication;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthenticationService {

    Map<String,Object> login(Map<String,Object> param, HttpServletRequest request);
    ResponseEntity<?> forgetPassword(Map<String,Object> param,HttpServletRequest request);
    ResponseEntity<?> otpVerification(Map<String,Object> param,HttpServletRequest request);
    ResponseEntity<?> updatePassword(Map<String,Object> param,HttpServletRequest request);
}
