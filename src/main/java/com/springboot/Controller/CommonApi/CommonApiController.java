package com.springboot.Controller.CommonApi;

import com.springboot.Payload.Response;
import com.springboot.Service.Common.CommonService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/common-api")
@RequiredArgsConstructor
public class CommonApiController {

    private final CommonService commonService;

    @GetMapping("/financial-year")
    public ResponseEntity<?> getFinancialYearDetails(@RequestParam Map<String,Object> param, HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",true,commonService.getFinancialYear(param,request)), HttpStatus.OK);
    }

    @GetMapping("/country")
    public ResponseEntity<?> getCountry(@RequestParam Map<String,Object> param, HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",true,commonService.getCountry(param,request)), HttpStatus.OK);
    }

}
