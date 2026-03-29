package com.springboot.Utility;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ApiResponse<T> {

    private String message;
    private T t;


    public static ResponseEntity<?> apiSuccess(){
        Map<String,Object> response = new HashMap<>();
        response.put("success",true);
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public static ResponseEntity<?> apiSuccess(Object o){
        Map<String,Object> response = new HashMap<>();
        response.put("success",true);
        response.put("data",o);
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public static ResponseEntity<?> apiFailure(){
        Map<String,Object> response = new HashMap<>();
        response.put("success",false);
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<?> apiFailure(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<?> apiValidation(String message){
        Map<String,Object> response = new HashMap<>();
        response.put("success",false);
        response.put("status",HttpStatus.BAD_REQUEST.value());
        response.put("message",message);
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
