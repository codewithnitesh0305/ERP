package com.springboot.Controller;

import com.springboot.Model.Employee;
import com.springboot.Model.RefreshToken;
import com.springboot.Payload.JwtRequest;
import com.springboot.Payload.JwtResponse;
import com.springboot.Payload.RefreshTokenRequest;
import com.springboot.Security.JwtHelper;
import com.springboot.Service.EmployeeService;
import com.springboot.Service.RefreshTokenService;
import com.springboot.UploadExcel.Excel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/api")
public class PublicController {
    @Autowired
    private EmployeeService service;
    @Autowired
    private JwtHelper helper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private Excel excel;

    @PostMapping("/SignUp")
    public ResponseEntity<?> saveUser(@RequestBody Employee employee) throws UnknownHostException {
        Employee saveEmployee = service.createEmployee(employee);
        return new ResponseEntity<Employee>(saveEmployee, HttpStatus.CREATED);
    }

    @PostMapping("/Login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        //this.doAuthenticate(request.getEmail(), request.getPassword());
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        String token = this.helper.generateToken(authentication);
        RefreshToken refershToken = refreshTokenService.createRefershToken(authentication.getName());
        JwtResponse response = JwtResponse.builder()
                .token(token)
                .username(userDetails.getUsername())
                .refreshToken(refershToken.getRefreshToken())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/Refresh")
    public ResponseEntity<?> refreshJwtToken(@RequestBody RefreshTokenRequest refreshToken) {
        System.out.println("Refresh token: " + refreshToken.getRefreshToken());

        // Verify the refresh token
        RefreshToken refershToken = refreshTokenService.verifyRefershToken(refreshToken.getRefreshToken());
        System.out.println("Refresh Token: "+ refershToken.getRefreshToken());
        Employee user = refershToken.getEmployee();

        // Load the user details based on the username (or email)
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        // Manually create an Authentication object and set it in the SecurityContext
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate the new JWT token
        String generateToken = this.helper.generateToken(authentication);

        // Prepare the response with the new token and refresh token
        JwtResponse response = JwtResponse.builder()
                .refreshToken(refershToken.getRefreshToken())
                .token(generateToken)
                .username(userDetails.getUsername())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
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
}
