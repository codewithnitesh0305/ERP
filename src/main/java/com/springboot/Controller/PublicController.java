package com.springboot.Controller;

import com.springboot.Model.EmployeeInformation.Employee.Employees;
import com.springboot.Model.RefreshToken;
import com.springboot.Payload.JwtRequest;
import com.springboot.Payload.JwtResponse;
import com.springboot.Payload.RefreshTokenRequest;
import com.springboot.Security.JwtHelper;
import com.springboot.Service.RefreshTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PublicController {
    @Autowired
    private JwtHelper helper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private RefreshTokenService refreshTokenService;

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
        Employees user = refershToken.getEmployee();

        // Load the user details based on the username (or email)
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmailId());

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

}
