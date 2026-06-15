package com.springboot.Service.Authentication;

import com.cloudinary.Api;
import com.springboot.Exception.ResourceNotFoundException;
import com.springboot.Exception.ValidationException;
import com.springboot.Model.EmployeeInformation.Employee.Employees;
import com.springboot.Model.Organizations.Organization;
import com.springboot.Model.Organizations.OrganizationBranch;
import com.springboot.Model.User.UserOtp;
import com.springboot.Model.User.Users;
import com.springboot.Repository.EmployeeInformation.Employees.EmployeeRepository;
import com.springboot.Repository.Organization.OrganizationBranchRepository;
import com.springboot.Repository.Organization.OrganizationRepository;
import com.springboot.Repository.User.UserOtpRepository;
import com.springboot.Repository.User.UserRepository;
import com.springboot.Security.JwtHelper;
import com.springboot.Service.Common.EmailService.EmailService;
import com.springboot.Utility.ApiResponse;
import com.springboot.Utility.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService{

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrganizationRepository organizationRepository;
    private final OrganizationBranchRepository organizationBranchRepository;
    private final JwtHelper jwtHelper;
    private final EmailService emailService;
    private final UserOtpRepository userOtpRepository;

    @Override
    public Map<String,Object> login(Map<String, Object> param, HttpServletRequest request) {
        Map<String,Object> response = new LinkedHashMap<>();
        String emailId = Utilities.stringValue(param.get("emailId"));
        String password = Utilities.stringValue(param.get("password"));
        if(emailId.isEmpty() || password.isEmpty()) {
            throw new ValidationException("Invalid Email Id and Password.");
        }
        Users users = userRepository.findByEmailId(emailId).orElseThrow(() -> new ResourceNotFoundException("Invalid email id."));
        if(!passwordEncoder.matches(password, users.getPassword())) {
             throw new ValidationException("Invalid Email Id and Password.");
        }
        Employees employees = employeeRepository.findByEmailId(emailId).orElseThrow(() -> new ResourceNotFoundException("Employee not found."));
        OrganizationBranch branch = organizationBranchRepository.findById(employees.getBranchId()).orElseThrow(() -> new ResourceNotFoundException("Branch not found."));
        Organization organization = organizationRepository.findById(branch.getOrganizationId()).orElseThrow(() -> new ResourceNotFoundException("Organization not found."));
        String token = jwtHelper.generateToken(users, employees, organization.getId());
        response.put("organizationBranchLogo",Utilities.getServingUrlFromImageString(branch.getBranchLogo()));
        response.put("organizationLogo",Utilities.getServingUrlFromImageString(organization.getOrganizationLogo()));
        response.put("organizationId",organization.getId());
        response.put("organizationName",organization.getName());
        response.put("organizationBranchId",branch.getId());
        response.put("organizationBranchName",branch.getName());
        Map<String,Object> userResponseMap = new LinkedHashMap<>();
        userResponseMap.put("employeeImage",Utilities.getServingUrlFromImageString(employees.getEmployeeProfileImage()));
        userResponseMap.put("employeeName",employees.getFullName());
        userResponseMap.put("employeeDesignation",null);
        userResponseMap.put("employeeDepartment",null);
        userResponseMap.put("token",token);
        response.put("userDetails",userResponseMap);
        return response;
    }

    @Override
    public ResponseEntity<?> forgetPassword(Map<String, Object> param, HttpServletRequest request) {
        String emailId = Utilities.stringValue(param.get("emailId"));
        if(emailId.isEmpty()) return ApiResponse.apiValidation("Email ID is required.");
        Users users = userRepository.findByEmailId(emailId).orElseThrow(() -> new ResourceNotFoundException("Invalid email ID."));
        String otp = Utilities.generateOtp();
        String referenceNo = Utilities.referenceNo();
        UserOtp userOtp = new UserOtp();
        userOtp.setOtp(otp);
        userOtp.setReferenceNo(referenceNo);
        userOtp.setIsVerified(false);
        userOtp.setUserId(userOtp.getUserId());
        userOtp.setCreatedBy(users.getId());
        userOtp.setCreatedOn(Utilities.getCurrentDateTime());
        userOtpRepository.save(userOtp);
        String emailIdBody = "";
        String emailSubject = "";
        Boolean isEmailSend = emailService.sendEmail(emailId, emailSubject, emailIdBody, false, request);
        if(!isEmailSend){
            return ApiResponse.apiFailure();
        }
        return ApiResponse.apiSuccess();
    }

    @Override
    public ResponseEntity<?> otpVerification(Map<String, Object> param, HttpServletRequest request) {
        String otp = Utilities.stringValue(param.get("otp"));
        String referenceNo = Utilities.stringValue(param.get("referenceNo"));
        UserOtp userOtp = userOtpRepository.findByReferenceNoAndOtp(referenceNo,otp).orElseThrow(() -> new ResourceNotFoundException("Invalid OTP"));
        userOtp.setIsVerified(true);
        userOtpRepository.save(userOtp);
        return ApiResponse.apiSuccess();
    }

    @Override
    public ResponseEntity<?> updatePassword(Map<String, Object> param, HttpServletRequest request) {
        String referenceNo = Utilities.stringValue(param.get("referenceNo"));
        String newPassword = Utilities.stringValue(param.get("password"));
        String reEnterPassword = Utilities.stringValue(param.get("reEnterPassword"));
        if(referenceNo.isEmpty()) return ApiResponse.apiValidation("Reference No. not found.");
        if(newPassword.isEmpty()) return ApiResponse.apiValidation("New password is required.");
        if(reEnterPassword.isEmpty()) return ApiResponse.apiValidation("Re-enter password is required.");
        if(!newPassword.equals(reEnterPassword)) return ApiResponse.apiValidation("New password and re-enter password do not match.");
        UserOtp userOtp = userOtpRepository.findByReferenceNo(referenceNo).orElseThrow(() -> new ResourceNotFoundException("Reference No. not found."));
        Boolean isVerified = Utilities.booleanValue(userOtp.getIsVerified());
        Long userId =  Utilities.longValue(userOtp.getUserId());
        if(!isVerified) return ApiResponse.apiValidation("Otp is not verified.");
        Users users = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found."));
        users.setPassword(passwordEncoder.encode(newPassword));
        users.setUpdatedBy(userId);
        users.setUpdatedOn(Utilities.getCurrentDateTime());
        userRepository.save(users);
        return ApiResponse.apiSuccess();
    }
}
