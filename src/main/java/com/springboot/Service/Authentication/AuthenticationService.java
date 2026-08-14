package com.springboot.Service.Authentication;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthenticationService {

    Map<String,Object> login(Map<String,Object> param);
    ResponseEntity<?> forgetPassword(Map<String,Object> param);
    ResponseEntity<?> otpVerification(Map<String,Object> param);
    ResponseEntity<?> updatePassword(Map<String,Object> param);
}
