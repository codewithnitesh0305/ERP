package com.springboot.Controller.Authentication;

import com.springboot.Utility.Response;
import com.springboot.Service.Authentication.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth/api")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,Object> param, HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",true,authenticationService.login(param,request)), HttpStatus.OK);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",true,authenticationService.forgetPassword(param,request)),HttpStatus.OK);
    }

    @PostMapping("/otp-verification")
    public ResponseEntity<?> otpVerification(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",true,authenticationService.otpVerification(param,request)),HttpStatus.OK);
    }

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",true,authenticationService.updatePassword(param,request)),HttpStatus.OK);
    }

}
