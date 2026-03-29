package com.springboot.Controller.EmployeeInformation;

import com.springboot.Payload.Response;
import com.springboot.Service.EmployeeInformation.Setup.EmployeeAutoNumberSchemeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/employee-information")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeAutoNumberSchemeService employeeAutoNumberSchemeService;

    @PostMapping("/auto-number-scheme")
    public ResponseEntity<?> saveUpdateAutoNoScheme(@RequestBody Map<String,Object> param, HttpServletRequest request){
        return employeeAutoNumberSchemeService.saveUpdateAutoNumberScheme(param,request);
    }

    @GetMapping("/auto-number-scheme")
    public ResponseEntity<?> getAllAutoNumberScheme(@RequestParam Map<String,Object> param, HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",true,employeeAutoNumberSchemeService.getAutoNumberScheme(param,request)), HttpStatus.OK);
    }

    @PutMapping("/auto-number-scheme")
    public ResponseEntity<?> updateAutoNumberSchemeStatus(@RequestBody Map<String,Object> param, HttpServletRequest request){
        return employeeAutoNumberSchemeService.updateAutoNumberSchemeStatus(param,request);
    }

    @DeleteMapping("/auto-number-scheme")
    public ResponseEntity<?> deleteAutoNumberScheme(@RequestParam Map<String,Object> param, HttpServletRequest request){
        return employeeAutoNumberSchemeService.deleteAutoNumberScheme(param,request);
    }

}
