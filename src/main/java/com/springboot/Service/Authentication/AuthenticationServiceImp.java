package com.springboot.Service.Authentication;

import com.springboot.Exception.ResourceNotFoundException;
import com.springboot.Exception.ValidationException;
import com.springboot.Model.User.Tokens;
import com.springboot.Model.User.UserOtp;
import com.springboot.Model.User.Users;
import com.springboot.Repository.EmployeeInformation.Employees.EmployeeRepository;
import com.springboot.Repository.User.TokenRepository;
import com.springboot.Repository.User.UserOtpRepository;
import com.springboot.Repository.User.UserRepository;
import com.springboot.Security.JwtHelper;
import com.springboot.Service.Common.EmailService.EmailService;
import com.springboot.Utility.ApiResponse;
import com.springboot.Utility.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService{

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper;
    private final EmailService emailService;
    private final UserOtpRepository userOtpRepository;
    private final TokenRepository tokenRepository;

    @Override
    @Transactional
    public Map<String, Object> login(Map<String, Object> param) {
        Map<String, Object> response = new LinkedHashMap<>();
        String emailId = Utilities.stringValue(param.get("emailId"));
        String password = Utilities.stringValue(param.get("password"));
        if (emailId.isEmpty() || password.isEmpty()) {
            throw new ValidationException("Invalid Email Id and Password.");
        }
        Users users = userRepository.findByEmailId(emailId).orElseThrow(() -> new ResourceNotFoundException("Invalid email id."));
        if (!passwordEncoder.matches(password, users.getPassword())) {
            throw new ValidationException("Invalid Email Id and Password.");
        }
        builtLoginResponse(response, emailId, users);
        return response;
    }

    private void builtLoginResponse(Map<String, Object> response, String emailId, Users users) {
        Map<String, Object> employeeDetailMap = employeeRepository.getEmployeeDetails(emailId);
        if (employeeDetailMap == null || employeeDetailMap.isEmpty()) {
            throw new ResourceNotFoundException("Employee details not found.");
        }
        Long employeeId = Utilities.longValue(employeeDetailMap.get("employeeId"));
        Long branchId = Utilities.longValue(employeeDetailMap.get("branchId"));
        Long organizationId = Utilities.longValue(employeeDetailMap.get("organizationId"));
        Long userTypeId = Utilities.longValue(employeeDetailMap.get("userTypeId"));
        String token = jwtHelper.generateToken(users, employeeId, organizationId, branchId, userTypeId);
        tokenRepository.logoutAllExistingTokensByUserId(users.getId());
        saveUserTokens(users, token);
        Map<String, Object> organizationDetails = new LinkedHashMap<>();
        organizationDetails.put("organizationId", organizationId);
        organizationDetails.put("branchId", branchId);
        organizationDetails.put("branchName",Utilities.stringValue(employeeDetailMap.get("branchName")));
        organizationDetails.put("branchLogo", Utilities.getServingUrlFromImageString(Utilities.stringValue(employeeDetailMap.get("branchLogo"))));
        Map<String, Object> userDetails = new LinkedHashMap<>();
        userDetails.put("userId", users.getId());
        userDetails.put("employeeId", employeeId);
        userDetails.put("employeeName", Utilities.stringValue(employeeDetailMap.get("employeeName")));
        userDetails.put("employeeImage", Utilities.getServingUrlFromImageString(Utilities.stringValue(employeeDetailMap.get("employeeProfileImage"))));
        userDetails.put("emailId", Utilities.stringValue(employeeDetailMap.get("emailId")));
        userDetails.put("userTypeId", userTypeId);
        userDetails.put("departmentName", Utilities.stringValue(employeeDetailMap.get("departmentName")));
        userDetails.put("designationName",Utilities.stringValue(employeeDetailMap.get("designationName")));
        response.put("token", token);
        response.put("organization", organizationDetails);
        response.put("userDetails", userDetails);
    }

    private void saveUserTokens(Users users, String token) {
        Tokens tokens = new Tokens();
        tokens.setToken(token);
        tokens.setIsLogOut(false);
        tokens.setUserId(users.getId());
        tokenRepository.save(tokens);
    }

    @Override
    public ResponseEntity<?> forgetPassword(Map<String, Object> param) {
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
        Boolean isEmailSend = emailService.sendEmail(emailId, emailSubject, emailIdBody, false, null);
        if(!isEmailSend){
            return ApiResponse.apiFailure();
        }
        return ApiResponse.apiSuccess();
    }

    @Override
    public ResponseEntity<?> otpVerification(Map<String, Object> param) {
        String otp = Utilities.stringValue(param.get("otp"));
        String referenceNo = Utilities.stringValue(param.get("referenceNo"));
        UserOtp userOtp = userOtpRepository.findByReferenceNoAndOtp(referenceNo,otp).orElseThrow(() -> new ResourceNotFoundException("Invalid OTP"));
        userOtp.setIsVerified(true);
        userOtpRepository.save(userOtp);
        return ApiResponse.apiSuccess();
    }

    @Override
    public ResponseEntity<?> updatePassword(Map<String, Object> param) {
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
