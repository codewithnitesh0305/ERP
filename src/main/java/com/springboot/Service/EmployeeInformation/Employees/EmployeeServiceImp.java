package com.springboot.Service.EmployeeInformation.Employees;

import com.springboot.Exception.ResourceNotFoundException;
import com.springboot.Exception.ValidationException;
import com.springboot.Model.EmployeeInformation.Employee.EmployeeDocumentSubmission;
import com.springboot.Model.EmployeeInformation.Employee.Employees;
import com.springboot.Model.EmployeeInformation.Setup.EmployeeDocument;
import com.springboot.Model.User.Users;
import com.springboot.Repository.CustomRepo.CustomRepo;
import com.springboot.Repository.EmployeeInformation.Employees.EmployeeDocumentSubmissionRepository;
import com.springboot.Repository.EmployeeInformation.Employees.EmployeeRepository;
import com.springboot.Repository.EmployeeInformation.Employees.EmployeeStaticQuery;
import com.springboot.Repository.EmployeeInformation.Setup.EmployeeDocumentRepository;
import com.springboot.Repository.Organization.*;
import com.springboot.Repository.User.UserRepository;
import com.springboot.Service.Cloudinary.FileManager;
import com.springboot.Utility.ApiResponse;
import com.springboot.Utility.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImp implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeDocumentRepository employeeDocumentRepository;
    private final EmployeeDocumentSubmissionRepository employeeDocumentSubmissionRepository;
    private final FinancialYearRepository financialYearRepository;
    private final CustomRepo customRepo;
    private final SalutationRepository salutationRepository;
    private final GenderRepository genderRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;

    @Override
    public ResponseEntity<?> saveUpdateEmployee(Map<String, MultipartFile> file, Map<String, Object> param, HttpServletRequest request) {
        try{
            Long id = Utilities.longValue(param.get("id"));
            Long financialYearId = Utilities.longValue(param.get("financialYearId"));
            String firstName = Utilities.stringValue(param.get("firstName"));
            Long genderId = Utilities.longValue(param.get("genderId"));
            String dateOfBirth = Utilities.getUSDateFromIndianDate(Utilities.stringValue(param.get("dateOfBirth")));
            String contactNoCountryCode = Utilities.stringValue(param.get("contactNoCountryCode"));
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
            employees.setFinancialYearId(financialYearId);
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
            employees.setFatherContactNoCountryCode(Utilities.stringValue(param.get("fatherContactNoCountryCode")));
            employees.setFatherContactNo(Utilities.stringValue(param.get("fatherContactNo")));
            employees.setFatherEmailId(Utilities.stringValue(param.get("fatherEmailId")));

            employees.setMotherSalutationId(Utilities.longValue(param.get("motherSalutationId")));
            employees.setMotherName(Utilities.stringValue(param.get("motherName")));
            employees.setMotherContactNoCountryCode(Utilities.stringValue(param.get("motherContactNoCountryCode")));
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

            Long employeeId = employeeRepository.save(employees).getId();
            processedDocument(file,param,employeeId,request);
            if(id == null){
                Users user = new Users();
                user.setUserId(emailId);
                user.setEmployeeId(employeeId);
                user.setPassword(passwordEncoder.encode("HRNest@123"));
                user.setCreatedBy(null);
                user.setCreatedOn(Utilities.getCurrentDateTime());
                userRepository.save(user);
            }
            return ApiResponse.apiSuccess();
        }catch (Exception ex){
            throw new RuntimeException("Something went wrong "+ex.getMessage());
        }
    }

    public void processedDocument(Map<String,MultipartFile> fileMap,Map<String,Object> parma,Long employeeId,HttpServletRequest request) throws IOException {
        String documentListStr = Utilities.stringValue(parma.get("documentList"));
        Long departmentId = Utilities.longValue(parma.get("departmentId"));
        List<Map<String, Object>> documentMapList = parseEmployeeDocumentJson(documentListStr);

        List<EmployeeDocument> employeeDocumentList = employeeDocumentRepository.findByDepartmentId(departmentId);
        Map<Long,Boolean> employeeDocumentMap = new HashMap<>();
        if(Utilities.isCollectionNotEmpty(employeeDocumentList)){
            for(EmployeeDocument employeeDocument : employeeDocumentList){
                Long documentId = employeeDocument.getId();
                Boolean isMandatory = employeeDocument.getIsMandatory();
                employeeDocumentMap.put(documentId,isMandatory);
            }
        }

        if(Utilities.isCollectionNotEmpty(documentMapList)){
            for(Map<String,Object> documentDataMap : documentMapList){
                Long id = Utilities.longValue(documentDataMap.get("id"));
                EmployeeDocumentSubmission documentSubmission = id == null ? new EmployeeDocumentSubmission() : employeeDocumentSubmissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Document Not found."));
                Long documentId = Utilities.longValue(documentDataMap.get("documentId"));
                String documentName = Utilities.stringValue(documentDataMap.get("documentName"));
                String documentExpiryDate = Utilities.getUSDateFromIndianDate(Utilities.stringValue(documentDataMap.get("documentExpiryDate")));
                String fileName = Utilities.stringValue(documentDataMap.get("fileName"));
                String documentNumber = Utilities.stringValue(documentDataMap.get("documentNumber"));
                Boolean isFileChange = Utilities.booleanValue(documentDataMap.get("isFileChange"));
                Boolean isDocumentMandatory = employeeDocumentMap.get(documentId);
                if(isDocumentMandatory && (documentNumber.isEmpty() || documentExpiryDate.isEmpty())) throw new ValidationException(documentName+" details is mandatory");
                MultipartFile multipartPart = fileMap.get(fileName);
                if(isFileChange){
                    String documentJson = FileManager.uploadFile(multipartPart);
                    String documentUrl = Utilities.stringValue(documentSubmission.getDocumentUrl());
                    if(!documentJson.isEmpty()){
                        FileManager.deleteFile(documentJson);
                    }
                    documentSubmission.setEmployeeId(employeeId);
                    documentSubmission.setDocumentName(documentName);
                    documentSubmission.setDocumentId(documentId);
                    documentSubmission.setDepartmentId(departmentId);
                    documentSubmission.setDocumentNo(documentNumber);
                    documentSubmission.setDocumentExpiryDate(documentExpiryDate);
                    employeeDocumentSubmissionRepository.save(documentSubmission);
                }
            }
        }
    }

    public List<Map<String, Object>> parseEmployeeDocumentJson(String documentListStr) {
        List<Map<String, Object>> documentMapList = new ArrayList<>();
        if (documentListStr != null && !documentListStr.isEmpty()) {
            JSONArray jsonArray = new JSONArray(documentListStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Map<String, Object> documentMap = new HashMap<>();
                Iterator<String> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    documentMap.put(key, jsonObject.optString(key));
                }
                documentMapList.add(documentMap);
            }
        }
        return documentMapList;
    }

    @Override
    public Map<String, Object> getAllEmployees(Map<String, Object> param, HttpServletRequest request) {
        Map<String,Object> result_map = new LinkedHashMap<>();
        try{
            Long employeeTypeId = Utilities.longValue(param.get("employeeTypeId"));
            Long departmentId = Utilities.longValue(param.get("departmentId"));
            Long designationId = Utilities.longValue(param.get("designationId"));
            Long genderId = Utilities.longValue(param.get("genderId"));
            Long financialYearId = Utilities.longValue(param.get("financialYearId"));

            if(financialYearId == null){
                financialYearId = financialYearRepository.findIdByIsActiveTrue();
            }
            StringBuilder filter = new StringBuilder();
            if(employeeTypeId != null){
                filter.append(" emp.employee_type_id = ").append(employeeTypeId);
            }
            if(departmentId != null){
                if(!filter.isEmpty()) filter.append(" and ");
                filter.append(" emp.department_id = ").append(financialYearId);
            }
            if(departmentId != null){
                if(!filter.isEmpty()) filter.append(" and ");
                filter.append(" emp.department_id = ").append(departmentId);
            }
            if(designationId != null){
                if(!filter.isEmpty()) filter.append(" and ");
                filter.append(" emp.designation_id = ").append(designationId);
            }
            if(genderId != null){
                if(!filter.isEmpty()) filter.append(" and ");
                filter.append(" emp.gender_id = ").append(genderId);
            }

            Map<String, Object> allDepartment = customRepo.getAllDepartment();
            List<Map<String,Object>> departmentList = (List<Map<String, Object>>) allDepartment.get("departmentList");
            Map<String,Object> departmentMap = (Map<String,Object>) allDepartment.get("departmentMap");

            Map<String, Object> allDesignation = customRepo.getAllDesignation();
            List<Map<String,Object>> designationList = (List<Map<String,Object> >) allDesignation.get("designationList");
            Map<String,Object> designationMap = (Map<String,Object>) allDepartment.get("designationMap");

            Map<String, Object> allEmployeeType = customRepo.getAllEmployeeType();
            List<Map<String,Object>> employeeTypeList = (List<Map<String, Object>>) allEmployeeType.get("employeeTypeList");
            Map<String,Object> employeeTypeMap = (Map<String,Object>) allEmployeeType.get("employeeTypeMap");

            Map<Long,String> salutationMap = salutationRepository.salutationList().stream().collect(Collectors.toMap(sal -> Utilities.longValue(sal.get("id")), sal -> Utilities.stringValue(sal.get("name"))));
            Map<Long,String> genderMap = genderRepository.genderList().stream().collect(Collectors.toMap(gen -> Utilities.longValue(gen.get("id")), gen -> Utilities.stringValue(gen.get("name"))));

            List<Map<String,Object>> employeeList = new ArrayList<>();
            List<Map<String, Object>> employeeMapList = customRepo.customizeDataList(EmployeeStaticQuery.EMPLOYEE_DATA_QUERY, filter.toString(), null, "emp.created_on desc");
            if(Utilities.isCollectionNotEmpty(employeeMapList)){
                for(Map<String,Object> employeeMap : employeeMapList){
                    Long id = Utilities.longValue(employeeMap.get("id"));
                    String employeeImage = Utilities.getServingUrlFromImageString(Utilities.stringValue(employeeMap.get("employeeProfileImage")));
                    String employeeCode = Utilities.stringValue(employeeMap.get("employeeCode"));
                    String salutation = Utilities.stringValue(salutationMap.get(Utilities.longValue(employeeMap.get("salutationId"))));
                    String fullName = Utilities.stringValue(employeeMap.get("fullName"));
                    fullName = fullName.isEmpty() ? "" : salutation.isEmpty() ? fullName : salutation + fullName;
                    String emailId = Utilities.stringValue(employeeMap.get("emailId"));
                    String contactNoCountryCode = Utilities.stringValue(employeeMap.get("contactNoCountryCode"));
                    String contactNo = Utilities.stringValue(employeeMap.get("contactNo"));
                    contactNo = contactNo.isEmpty() ? "" : contactNoCountryCode.isEmpty() ? contactNo : "+"+contactNoCountryCode + contactNo;
                    String gender = Utilities.stringValue(genderMap.get(Utilities.longValue(employeeMap.get("genderId"))));
                    String dateOfBirth = Utilities.getIndianDateFormatFromUSDate(Utilities.stringValue(employeeMap.get("dateOfBirth")));
                    String dateOfJoining = Utilities.getIndianDateFormatFromUSDate(Utilities.stringValue(employeeMap.get("dateOfJoining")));
                    String department = Utilities.stringValue(departmentMap.get(Utilities.stringValue(employeeMap.get("departmentId"))));
                    String designation = Utilities.stringValue(designationMap.get(Utilities.stringValue(employeeMap.get("designationId"))));
                    String employeeType = Utilities.stringValue(employeeTypeMap.get(Utilities.stringValue(employeeMap.get("employeeTypeId"))));

                    Map<String,Object> dataMap = new LinkedHashMap<>();
                    dataMap.put("id",id);
                    dataMap.put("employeeImage",employeeImage);
                    dataMap.put("fullName",fullName);
                    dataMap.put("emailId",emailId);
                    dataMap.put("contactNo",contactNo);
                    dataMap.put("gender",gender);
                    dataMap.put("dateOfBirth",dateOfBirth);
                    dataMap.put("dateOfJoining",dateOfJoining);
                    dataMap.put("department",department);
                    dataMap.put("designation",designation);
                    dataMap.put("employeeType",employeeType);
                    employeeList.add(dataMap);
                }
            }

            result_map.put("employeeType",employeeTypeList);
            result_map.put("department",departmentList);
            result_map.put("designation",designationList);
            result_map.put("employees",employeeList);
        }catch (Exception ex){
            throw new RuntimeException("Something went wrong: "+ ex.getMessage());
        }
        return  result_map;
    }

    @Override
    public Map<String, Object> employeeById(Map<String, Object> param, HttpServletRequest request) {
        Map<String,Object> result_map = new LinkedHashMap<>();
        try{
            Long id = Utilities.longValue(param.get("id"));
            
            List<Map<String,Object>> salutationList = salutationRepository.salutationList();
            List<Map<String,Object>> genderList = genderRepository.genderList();
            List<Map<String, Object>> departmentList = departmentRepository.getDepartmentList();
            List<Map<String, Object>> designationList = designationRepository.getDesignationList();
            if(id != null){
                List<Map<String, Object>> employeeMapList = customRepo.customizeDataList(EmployeeStaticQuery.EMPLOYEE_DATA_QUERY, "emp.id = "+ id, null, "emp.created_on desc");
                result_map.put("employee",employeeMapList);
            }
            result_map.put("salutationList",salutationList);
            result_map.put("genderList",genderList);
            result_map.put("departmentList",departmentList);
            result_map.put("designationList",designationList);
            return result_map;
        }catch (Exception ex){
            throw new RuntimeException("Something went wrong: "+ ex.getMessage());
        }
    }
}
