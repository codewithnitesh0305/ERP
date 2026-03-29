package com.springboot.Controller;

import com.springboot.Model.ClaimRequest;
import com.springboot.Model.Employee;
import com.springboot.Model.Leave;
import com.springboot.Service.ClaimRequestService;
import com.springboot.Service.EmployeeService;
import com.springboot.Service.LeaveService;
import com.springboot.Service.Organization.Department.DepartmentService;
import com.springboot.UploadExcel.Excel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private ClaimRequestService claimRequestService;
    
    @Autowired
    private Excel excel;
    
    @Autowired
    private LeaveService leaveService;
    
    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/CreateEmployee")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) throws UnknownHostException {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/Employee/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") int id){
        Employee employee = employeeService.getEmployeeById(id);
        System.out.println("Employee: "+ employee);
        return new ResponseEntity<>(employee,HttpStatus.OK);
    }

    @GetMapping("/AllEmployees")
    public ResponseEntity<?> getAllEmployees(){
        List<Employee> employee = employeeService.getAllEmployee();
        return  new ResponseEntity<>(employee,HttpStatus.OK);
    }

    @PutMapping("/UpdateEmployee/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable("id") int id, @RequestBody Employee employee) throws UnknownHostException {
    	System.out.println("Employee Id: "+ id);
    	System.out.println("Employee Name: "+ employee.getName());
    	employee.setId(id);
        Employee updateEmployee =  employeeService.updateEmployee(employee);
        return new ResponseEntity<Employee>(updateEmployee,HttpStatus.CREATED);
    }

    @DeleteMapping("/DeleteEmployee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") int id){
        boolean deleteEmployee = employeeService.deleteEmployee(id);
        if(deleteEmployee){
            return new ResponseEntity<String>("Employee Deleted Successfully ",HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("Something went wrong ",HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/SearchEmployee")
    public ResponseEntity<?> searchEmployee(@RequestParam(required = false) String name ) {
        if ((name == null || name.isEmpty())) {
            return new ResponseEntity<>("Name or Employee Code must be provided", HttpStatus.BAD_REQUEST);
        }
        List<Employee> employees = employeeService.searchEmployee(name);

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
    
    @GetMapping("/SearchClaim")
    public ResponseEntity<?> searchClaim(@RequestParam(required = false) String claimNo){
    	List<ClaimRequest> list = claimRequestService.searchClaims(claimNo);
    	return new ResponseEntity<List<ClaimRequest>>(list, HttpStatus.OK);
    }
    
    @PostMapping("/Employee/Upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException{
    	if(excel.checkExcelFromat(file)) {
    		employeeService.saveExcelData(file);
    		return ResponseEntity.ok(Map.of("message","File is uploaded and data in save succcessfully"));
    	}
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file upload");
    }
    @GetMapping("Employee/Download")
    public ResponseEntity<Resource> downloadExcelData() throws IOException{
    	String filename = "Users.xlsx";
    	ByteArrayInputStream actualData = employeeService.downloadExcelData();
    	InputStreamResource file = new InputStreamResource(actualData);
    	ResponseEntity<Resource> body = ResponseEntity.ok()
    			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename)
    			.contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
    			.body(file);
    	return body;
    }
    @GetMapping("Employee/download-header-only")
    public ResponseEntity<InputStreamResource> downloadHeaderOnly() {
        try {
            ByteArrayInputStream stream = excel.downloadExcelWithHeaderOnly();
            
            if (stream == null) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=HR_Nest.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(new InputStreamResource(stream));
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/Count")
    public ResponseEntity<?> countAllEmployees(){
    	long countAllEmployees = employeeService.countAllEmployees();
    	return new ResponseEntity<Long>(countAllEmployees,HttpStatus.OK);
    }
    
    @GetMapping("/CountByL1")
    public ResponseEntity<?> countByRoleL1(){
    	long count = employeeService.countByRoleL1();
    	return new ResponseEntity<Long>(count,HttpStatus.OK);
    }
    
    @GetMapping("/CountByL2")
    public ResponseEntity<?> countByRoleL2(){
    	long count = employeeService.countByRoleL2();
    	return new ResponseEntity<Long>(count,HttpStatus.OK);
    }
    
    @GetMapping("/CountByL3")
    public ResponseEntity<?> countByRoleL3(){
    	long count = employeeService.countByRoleL3();
    	return new ResponseEntity<Long>(count,HttpStatus.OK);
    }
    
    @PutMapping("/updateClaim")
    public ResponseEntity<?> updateClaimStatus(@RequestParam(required = false) Integer id, @RequestParam(required = false) boolean status){
    	int claimRequest = claimRequestService.updateClaimRequest(id, status);
    	return new ResponseEntity<Integer>(claimRequest,HttpStatus.OK);
    }
    
    @GetMapping("/AllLeaves")
    public ResponseEntity<?> getAllLeaves(){
    	List<Leave> allLeave = leaveService.getAllLeave();
    	return new ResponseEntity<List<Leave>>(allLeave,HttpStatus.OK);
    }
    
    @PutMapping("/UpldateLeave")
    public ResponseEntity<?> updateLeaveStatus(@RequestParam(required = false) Integer id, @RequestParam(required = false) boolean status){
    	int updateLeaveRequest = leaveService.updateLeaveRequest(id, status);
    	return new ResponseEntity<Integer>(updateLeaveRequest,HttpStatus.OK);
    }
    
    @GetMapping("/AllClaim")
    public ResponseEntity<?> getAllEmployeeClaimRequest(){
    	List<ClaimRequest> allCaimRequest = claimRequestService.getAllCaimRequest();
    	return new ResponseEntity<List<ClaimRequest>>(allCaimRequest,HttpStatus.OK);
    }
    
   /* @PostMapping("/Department")
    public ResponseEntity<?> createDepartment(@RequestBody Department department){
    	Department saveDepartment = departmentService.saveDepartment(department);
    	return new ResponseEntity<Department>(saveDepartment,HttpStatus.CREATED);
    }*/
    
    /*@GetMapping("/AllDepartment")
    public ResponseEntity<?> getAllDepartment(){
    	List<Department> allDepartment = departmentService.getAllDepartment();
    	return new ResponseEntity<List<Department>>(allDepartment,HttpStatus.OK);
    }*/
    
}
