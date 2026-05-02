package com.springboot.Service.EmployeeInformation.Employees;

import com.springboot.Exception.ResourceNotFoundException;
import com.springboot.Model.EmployeeInformation.Employee.Employees;
import com.springboot.Repository.EmployeeInformation.Employees.EmployeeRepository;
import com.springboot.Utility.ApiResponse;
import com.springboot.Utility.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.entity.mime.MultipartPart;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImp implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    @Override
    public ResponseEntity<?> saveUpdateEmployee(Map<String, MultipartPart> file, Map<String, Object> param, HttpServletRequest request) {
        try{
            Long id = Utilities.longValue(param.get("id"));
            String firstName = Utilities.stringValue(param.get("firstName"));
            Long genderId = Utilities.longValue(param.get("genderId"));
            String dateOfBirth = Utilities.getUSDateFromIndianDate(Utilities.stringValue(param.get("dateOfBirth")));
            Long contactNoCountryCode = Utilities.longValue(param.get("contactNoCountryCode"));
            String contactNo = Utilities.stringValue(param.get("contactNo"));
            String emailId = Utilities.stringValue(param.get("emailId"));
            Long nationalityId = Utilities.longValue(param.get("nationalityId"));
            Long departmentId = Utilities.longValue(param.get("departmentId"));
            Long designationId = Utilities.longValue(param.get("designationId"));
            Long userTypeId = Utilities.longValue(param.get("userTypeId"));
            Long employeeTypeId = Utilities.longValue(param.get("employeeTypeId"));
            String dateOfJoining = Utilities.getUSDateFromIndianDate(Utilities.stringValue(param.get("dateOfJoining")));
            Long reportingAuthorityId = Utilities.longValue(param.get("reportingAuthorityId"));
            String accountNo = Utilities.stringValue(param.get("accountNo"));
            String reEnterAccountNo = Utilities.stringValue(param.get("reEnterAccountNo"));

            if(firstName.isEmpty()) return ApiResponse.apiValidation("Employee Name is required.");
            if(genderId == null) return ApiResponse.apiValidation("Select gender.");
            if(dateOfBirth.isEmpty()) return ApiResponse.apiValidation("Date of Birth is required.");
            if(contactNo.isEmpty()) return ApiResponse.apiValidation("Contact No. is required.");
            if(emailId.isEmpty()) return ApiResponse.apiValidation("Email is required.");
            if(nationalityId == null) return ApiResponse.apiValidation("Nationality is required.");
            if(departmentId == null) return ApiResponse.apiValidation("Select department.");
            if(designationId == null) return ApiResponse.apiValidation("Select designation.");
            if(userTypeId == null) return ApiResponse.apiValidation("Select User Type.");
            if(employeeTypeId == null) return ApiResponse.apiValidation("Select Employee Type.");
            if(dateOfJoining.isEmpty()) return ApiResponse.apiValidation("Select Date of Joining.");
            if (!Objects.equals(accountNo, reEnterAccountNo)) return ApiResponse.apiValidation("Account No. and Re-enter Account No. should match.");

            Employees employees = id == null ? new Employees() : employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found."));
            String lastName = Utilities.stringValue(param.get("lastName"));
            String middleName = Utilities.stringValue(param.get("middleName"));
            employees.setSalutationId(Utilities.longValue(param.get("salutationId")));
            employees.setFirstName(firstName);
            employees.setLastName(lastName);
            employees.setMiddleName(middleName);
            String fullName = "";
            fullName = fullName+=firstName;
            if(!middleName.isEmpty()) fullName = fullName +" "+ middleName;
            if(!lastName.isEmpty()) fullName = fullName +" "+ lastName;
            employees.setFullName(fullName);
            employees.setGenderId(genderId);
            employees.setDateOfBirth(dateOfBirth);
            employees.setContactNoCountryCode(contactNoCountryCode);
            employees.setContactNo(contactNo);
            employees.setEmailId(emailId);
            employees.setBloodGroupId(Utilities.longValue(param.get("bloodGroupId")));
            employees.setMaritalStatusId(Utilities.longValue(param.get("maritalStatusId")));
            employees.setNationalityId(Utilities.longValue(param.get("nationalityId")));
            employees.setReligionId(Utilities.longValue(param.get("religionId")));
            employees.setCasteId(Utilities.longValue(param.get("casteId")));
            employees.setDepartmentId(departmentId);
            employees.setDesignationId(designationId);
            employees.setUserTypeId(userTypeId);
            employees.setEmployeeTypeId(employeeTypeId);
            employees.setDateOfJoining(dateOfJoining);
            employees.setReportingAuthorityId(reportingAuthorityId);
            employees.setUanNo(Utilities.stringValue(param.get("uanNo")));

            employees.setFatherSalutationId(Utilities.longValue(param.get("fatherSalutationId")));
            employees.setFatherName(Utilities.stringValue(param.get("fatherName")));
            employees.setFatherContactNoCountryCode(Utilities.longValue(param.get("fatherContactNoCountryCode")));
            employees.setFatherContactNo(Utilities.stringValue(param.get("fatherContactNo")));
            employees.setFatherEmailId(Utilities.stringValue(param.get("fatherEmailId")));

            employees.setMotherSalutationId(Utilities.longValue(param.get("motherSalutationId")));
            employees.setMotherName(Utilities.stringValue(param.get("motherName")));
            employees.setMotherContactNoCountryCode(Utilities.longValue(param.get("motherContactNoCountryCode")));
            employees.setMotherContactNo(Utilities.stringValue(param.get("motherContactNo")));
            employees.setMotherEmailId(Utilities.stringValue(param.get("motherEmailId")));

            employees.setSpouseSalutationId(Utilities.longValue(param.get("spouseSalutationId")));
            employees.setSpouseName(Utilities.stringValue(param.get("spouseName")));
            employees.setSpouseContactNoCountryCode(Utilities.longValue(param.get("spouseContactNoCountryCode")));
            employees.setSpouseContactNo(Utilities.stringValue(param.get("spouseContactNo")));
            employees.setSpouseEmailId(Utilities.stringValue(param.get("spouseEmailId")));

            employees.setCorrespondingAddress(Utilities.stringValue(param.get("correspondingAddress")));
            employees.setCorrespondingCountryId(Utilities.longValue(param.get("correspondingCountryId")));
            employees.setCorrespondingStateId(Utilities.longValue(param.get("correspondenceStateId")));
            employees.setCorrespondingCityId(Utilities.longValue(param.get("correspondenceCityId")));
            employees.setCorrespondingPinCode(Utilities.stringValue(param.get("correspondencePinCode")));
            Boolean isPermanentSameAsCorrespondence = Utilities.booleanValue(param.get("isPermanentSameAsCorrespondence"));
            employees.setIsPermanentSameAsCorrespondence(isPermanentSameAsCorrespondence);
            if(isPermanentSameAsCorrespondence){
                employees.setPermanentAddress(Utilities.stringValue(param.get("correspondingAddress")));
                employees.setPermanentCountryId(Utilities.longValue(param.get("correspondingCountryId")));
                employees.setPermanentStateId(Utilities.longValue(param.get("correspondenceStateId")));
                employees.setPermanentCityId(Utilities.longValue(param.get("correspondenceCityId")));
                employees.setPermanentPinCode(Utilities.stringValue(param.get("correspondencePinCode")));
            }else{
                employees.setPermanentAddress(Utilities.stringValue(param.get("permanentAddress")));
                employees.setPermanentCountryId(Utilities.longValue(param.get("permanentCountryId")));
                employees.setPermanentStateId(Utilities.longValue(param.get("permanentStateId")));
                employees.setPermanentCityId(Utilities.longValue(param.get("permanentCityId")));
                employees.setPermanentPinCode(Utilities.stringValue(param.get("permanentPinCode")));
            }

            employees.setAccountNo(accountNo);
            employees.setAccountName(Utilities.stringValue(param.get("accountName")));
            employees.setIfscCode(Utilities.stringValue(param.get("ifscCode")));
            employees.setBankName(Utilities.stringValue(param.get("bankName")));
            employees.setBranch(Utilities.stringValue(param.get("branchName")));

            employeeRepository.save(employees);

            return ApiResponse.apiSuccess();
        }catch (Exception ex){
            throw new RuntimeException("Something went wrong "+ex.getMessage());
        }
    }

    @Override
    public Map<String, Object> getAllEmployees(Map<String, Object> param, HttpServletRequest request) {
        return null;
    }

    @Override
    public Map<String, Object> employeeById(Map<String, Object> param, HttpServletRequest request) {
        return null;
    }
}
