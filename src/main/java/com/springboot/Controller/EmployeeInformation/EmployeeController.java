package com.springboot.Controller.EmployeeInformation;

import com.springboot.Payload.Response;
import com.springboot.Service.EmployeeInformation.Setup.EmployeeAutoNumberSchemeService;
import com.springboot.Service.EmployeeInformation.Setup.EmployeeDocumentService;
import com.springboot.Service.EmployeeInformation.Setup.EmployeeTypeService;
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
    private final EmployeeTypeService employeeTypeService;
    private final EmployeeDocumentService employeeDocumentService;

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

    @PostMapping("/employee-type")
    public ResponseEntity<?> saveUpdateEmployeeType(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return employeeTypeService.saveUpdateEmployeeType(param,request);
    }

    @GetMapping("/employee-type")
    public ResponseEntity<?> getAllEmployeeType(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",employeeTypeService.getAllEmployeeType(param,request)),HttpStatus.OK);
    }

    @DeleteMapping("/employee-type")
    public ResponseEntity<?> deleteEmployeeType(@RequestParam Map<String,Object> param,HttpServletRequest request) {
        return employeeTypeService.deleteEmployeeType(param, request);
    }

    @PostMapping("/employee-document")
    public ResponseEntity<?> saveUpdateEmployeeDocument(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return employeeDocumentService.saveUpdateEmployeeDocument(param,request);
    }

    @GetMapping("/employee-document")
    public ResponseEntity<?> getAllEmployeeDocument(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",employeeDocumentService.getAllEmployeeDocument(param,request)),HttpStatus.OK);
    }

    @DeleteMapping("/employee-document")
    public ResponseEntity<?> deleteEmployeeDocument(@RequestParam Map<String,Object> param,HttpServletRequest request) {
        return employeeDocumentService.deleteEmployeeDocument(param, request);
    }

}
