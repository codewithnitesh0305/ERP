package com.springboot.Service.EmployeeInformation.Employees;

import com.springboot.Utility.ApiResponse;
import com.springboot.Utility.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.hc.client5.http.entity.mime.MultipartPart;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.UnableToRegisterMBeanException;

import java.util.Map;

public class EmployeeServiceImp implements EmployeeService{
    @Override
    public ResponseEntity<?> saveUpdateEmployee(Map<String, MultipartPart> file, Map<String, Object> param, HttpServletRequest request) {
        try{
            String employeeName = Utilities.stringValue(param.get("employeeName"));
            Long genderId = Utilities.longValue(param.get("genderId"));
            String dateOfBirth = Utilities.stringValue(param.get("dateOfBirth"));
            Long contactNoCountryCode = Utilities.longValue(param.get("contactNoCountryCode"));
            String contactNo = Utilities.stringValue(param.get("contactNo"));
            String emailId = Utilities.stringValue(param.get("emailId"));
            Long nationalityId = Utilities.longValue(param.get("nationalityId"));
            Long departmentId = Utilities.longValue(param.get("departmentId"));
            Long designationId = Utilities.longValue(param.get("designationId"));
            Long userTypeId = Utilities.longValue(param.get("userTypeId"));
            Long employeeTypeId = Utilities.longValue(param.get("employeeTypeId"));
            String dateOfJoining = Utilities.getUSDateFromIndianDate(Utilities.stringValue(param.get("dateOfJoining")));

            if(employeeName.isEmpty()) return ApiResponse.apiValidation("Employee Name is required.");
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
