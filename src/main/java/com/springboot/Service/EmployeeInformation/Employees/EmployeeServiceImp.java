package com.springboot.Service.EmployeeInformation.Employees;

import com.springboot.Dto.EmployeeInformation.DocumentSubmissionDto;
import com.springboot.Dto.EmployeeInformation.EmployeeDto;
import com.springboot.Exception.ResourceNotFoundException;
import com.springboot.Exception.ValidationException;
import com.springboot.Model.EmployeeInformation.Employee.EmployeeDocumentSubmission;
import com.springboot.Model.EmployeeInformation.Employee.Employees;
import com.springboot.Model.EmployeeInformation.Setup.EmployeeDocument;
import com.springboot.Model.Organizations.Salutation;
import com.springboot.Model.User.Users;
import com.springboot.Repository.CustomRepo.CustomRepo;
import com.springboot.Repository.EmployeeInformation.Employees.EmployeeDocumentSubmissionRepository;
import com.springboot.Repository.EmployeeInformation.Employees.EmployeeRepository;
import com.springboot.Repository.EmployeeInformation.Employees.EmployeeStaticQuery;
import com.springboot.Repository.EmployeeInformation.Setup.EmployeeDocumentRepository;
import com.springboot.Repository.Organization.*;
import com.springboot.Repository.User.UserRepository;
import com.springboot.Service.Cloudinary.FileManager;
import com.springboot.Service.EmployeeInformation.Setup.EmployeeAutoNumberSchemeService;
import com.springboot.Utility.ApiResponse;
import com.springboot.Utility.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImp implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final EmployeeDocumentRepository employeeDocumentRepository;
    private final EmployeeDocumentSubmissionRepository employeeDocumentSubmissionRepository;
    private final FinancialYearRepository financialYearRepository;
    private final CustomRepo customRepo;
    private final SalutationRepository salutationRepository;
    private final GenderRepository genderRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;
    private final EmployeeAutoNumberSchemeService employeeAutoNumberSchemeService;
    private final BloodGroupRepository bloodGroupRepository;
    private final ReligionRepository religionRepository;
    private final CasteRepository casteRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeExperienceService employeeExperienceService;
    private final EmployeeQualificationService employeeQualificationService;
    private final EmployeeDocumentSubmissionService employeeDocumentSubmissionService;

    @Override
    @Transactional
    public ResponseEntity<?> saveUpdateEmployee(EmployeeDto dto, HttpServletRequest request) {
        try {
            Long id = dto.getId();
            String employeeContactCode = Utilities.stringNullValue(dto.getContactNoCountryCode());
            String fatherContactCode = Utilities.stringNullValue(dto.getFatherContactNoCountryCode());
            String motherContactCode = Utilities.stringNullValue(dto.getMotherContactNoCountryCode());
            String spouseContactCode = Utilities.stringNullValue(dto.getSpouseContactNoCountryCode());
            Long departmentId = Utilities.longValue(dto.getDepartmentId());
            Boolean isEmployeeProfileImageChange = Utilities.booleanValue(dto.getIsChange());
            Long financialYearId = Utilities.longValue(dto.getFinancialYearId());
            String employeeCode = Utilities.stringValue(dto.getEmployeeCode());
            Long currentEmployeeId = Utilities.currentEmployeeId();
            String currentDateTime = Utilities.getCurrentDateTime();

            if (employeeContactCode != null && !employeeContactCode.isEmpty()) {
                Integer lengthByContactCode = customRepo.getContactNoLengthByContactCode(employeeContactCode);
                if (lengthByContactCode != null && dto.getContactNo() != null && dto.getContactNo().length() != lengthByContactCode) {
                    return ApiResponse.apiValidation("Contact No. should be " + lengthByContactCode + " digits.");
                }
            }
            if (fatherContactCode != null && !fatherContactCode.isEmpty()) {
                Integer lengthByContactCode = customRepo.getContactNoLengthByContactCode(fatherContactCode);
                if (lengthByContactCode != null && dto.getFatherContactNo() != null && dto.getFatherContactNo().length() != lengthByContactCode) {
                    return ApiResponse.apiValidation("Father Contact No. should be " + lengthByContactCode + " digits.");
                }
            }
            if (motherContactCode != null && !motherContactCode.isEmpty()) {
                Integer lengthByContactCode = customRepo.getContactNoLengthByContactCode(motherContactCode);
                if (lengthByContactCode != null && dto.getMotherContactNo() != null && dto.getMotherContactNo().length() != lengthByContactCode) {
                    return ApiResponse.apiValidation("Mother Contact No. should be " + lengthByContactCode + " digits.");
                }
            }
            if (spouseContactCode != null && !spouseContactCode.isEmpty()) {
                Integer lengthByContactCode = customRepo.getContactNoLengthByContactCode(spouseContactCode);
                if (lengthByContactCode != null && dto.getSpouseContactNo() != null && dto.getSpouseContactNo().length() != lengthByContactCode) {
                    return ApiResponse.apiValidation("Spouse Contact No. should be " + lengthByContactCode + " digits.");
                }
            }
            Long isContactNoExist = employeeRepository.existByContactNo(dto.getContactNo(),id);
            if(isContactNoExist > 0) return ApiResponse.apiValidation("Contact No. already exist.");

            Long isEmailExist = employeeRepository.existsByEmailId(dto.getEmailId(),id);
            if(isEmailExist > 0) return ApiResponse.apiValidation("Email ID already exist.");

            if (!Objects.equals(dto.getAccountNo(), dto.getReEnterAccountNo())) return ApiResponse.apiValidation("Account No. and Re-enter Account No. should match.");
            Employees employees = id == null ? new Employees() : employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found."));
            if(employeeCode.isBlank()) employeeCode = employeeAutoNumberSchemeService.generateEmployeeAutoNumber(financialYearId, departmentId);

            MultipartFile employeeProfileImage1 = dto.getEmployeeProfileImage();
            if(employeeProfileImage1 != null){
                String employeeProfileImage = FileManager.uploadFile(employeeProfileImage1);
                if(isEmployeeProfileImageChange){
                    String existingProfileImage = employees.getEmployeeProfileImage();
                    if(existingProfileImage != null && !existingProfileImage.isEmpty()){
                        FileManager.deleteFile(existingProfileImage);
                    }
                }else{
                    if(employeeProfileImage != null && !employeeProfileImage.isEmpty()){
                        employees.setEmployeeProfileImage(employeeProfileImage);
                    }else{
                        employees.setEmployeeProfileImage(null);
                    }
                }
            }
            employees = setEmployeesData(employees,dto,employeeCode,currentEmployeeId,currentDateTime);
            Long employeeId = employeeRepository.save(employees).getId();
            employeeDocumentSubmissionService.saveUpdateDocumentDetails(dto, employeeId,currentEmployeeId,currentDateTime);
            employeeQualificationService.saveUpdateQualificationDetails(dto.getEmployeeQualificationList(),dto.getDeletedEmployeeQualificationIds(),employeeId,currentEmployeeId,currentDateTime);
            employeeExperienceService.saveUpdateExperienceDetails(dto.getEmployeeExperienceList(),dto.getDeletedEmployeeExperienceIds(),employeeId,currentEmployeeId,currentDateTime);
            saveUpdateUser(dto, employeeId,currentEmployeeId,currentDateTime);
            return ApiResponse.apiSuccess();
        } catch (Exception ex) {
            throw new RuntimeException("Something went wrong " + ex.getMessage());
        }
    }

    private void saveUpdateUser(EmployeeDto dto, Long employeeId,Long currentEmployeeId,String currentDateTime) {
        String emailId = dto.getEmailId();
        Users users = emailId == null ? new Users() : userRepository.findByEmailId(dto.getEmailId()).orElseThrow(() -> new ResourceNotFoundException("User id not found."));
        users.setEmailId(dto.getEmailId());
        users.setEmployeeId(employeeId);
        if(users.getId() == null) users.setPassword(passwordEncoder.encode("Employee@123"));
        users.setCreatedBy(currentEmployeeId);
        users.setCreatedOn(currentDateTime);
        userRepository.save(users);
    }

    public Employees setEmployeesData(Employees employees,EmployeeDto dto,String employeeCode,Long currentEmployeeId,String currentDateTime) throws IOException {
        // Basic Details
        Long id = dto.getId();
        employees.setEmployeeCode(employeeCode);
        employees.setFinancialYearId(Utilities.longValue(dto.getFinancialYearId()));
        employees.setSalutationId(Utilities.longValue(dto.getSalutationId()));
        employees.setFirstName(Utilities.stringNullValue(dto.getFirstName()));
        employees.setMiddleName(Utilities.stringNullValue(dto.getMiddleName()));
        employees.setLastName(Utilities.stringNullValue(dto.getLastName()));
        String fullName = dto.getFirstName();
        if (dto.getMiddleName() != null && !dto.getMiddleName().isEmpty()) fullName += " " + dto.getMiddleName();
        if (dto.getLastName() != null && !dto.getLastName().isEmpty()) fullName += " " + dto.getLastName();
        employees.setFullName(fullName);
        employees.setGenderId(Utilities.longValue(dto.getGenderId()));
        employees.setDateOfBirth(Utilities.stringNullValue(Utilities.getUSDateFromIndianDate(dto.getDateOfBirth())));

        employees.setContactNoCountryCode(Utilities.stringNullValue(dto.getContactNoCountryCode()));
        employees.setContactNo(Utilities.stringNullValue(dto.getContactNo()));
        employees.setEmailId(Utilities.stringNullValue(dto.getEmailId()));

        employees.setBloodGroupId(dto.getBloodGroupId());
        employees.setMaritalStatusId(dto.getMaritalStatusId());
        employees.setNationalityId(dto.getNationalityId());
        employees.setReligionId(dto.getReligionId());
        employees.setCasteId(dto.getCasteId());

        // Official Details
        employees.setDepartmentId(dto.getDepartmentId());
        employees.setDesignationId(dto.getDesignationId());
        employees.setUserTypeId(dto.getUserTypeId());
        employees.setEmployeeTypeId(dto.getEmployeeTypeId());

        employees.setDateOfJoining(Utilities.getUSDateFromIndianDate(dto.getDateOfJoining()));
        employees.setReportingAuthorityId(dto.getReportingAuthorityId());
        employees.setUanNo(dto.getUanNo());

        // Father Details
        employees.setFatherSalutationId(dto.getFatherSalutationId());
        employees.setFatherName(dto.getFatherName());
        employees.setFatherContactNoCountryCode(dto.getFatherContactNoCountryCode());
        employees.setFatherContactNo(dto.getFatherContactNo());
        employees.setFatherEmailId(dto.getFatherEmailId());

        // Mother Details
        employees.setMotherSalutationId(dto.getMotherSalutationId());
        employees.setMotherName(Utilities.stringNullValue(dto.getMotherName()));
        employees.setMotherContactNoCountryCode(Utilities.stringNullValue(dto.getMotherContactNoCountryCode()));
        employees.setMotherContactNo(Utilities.stringNullValue(dto.getMotherContactNo()));
        employees.setMotherEmailId(Utilities.stringNullValue(dto.getMotherEmailId()));

        // Spouse Details
        employees.setSpouseSalutationId(dto.getSpouseSalutationId());
        employees.setSpouseName(Utilities.stringNullValue(dto.getSpouseName()));
        employees.setSpouseContactNoCountryCode(dto.getSpouseContactNoCountryCode());
        employees.setSpouseContactNo(Utilities.stringNullValue(dto.getSpouseContactNo()));
        employees.setSpouseEmailId(Utilities.stringNullValue(dto.getSpouseEmailId()));

        // Correspondence Address
        employees.setCorrespondingAddress(Utilities.stringNullValue(dto.getCorrespondingAddress()));
        employees.setCorrespondingCountryId(dto.getCorrespondingCountryId());
        employees.setCorrespondingStateId(dto.getCorrespondingStateId());
        employees.setCorrespondingCityId(dto.getCorrespondingCityId());
        employees.setCorrespondingPinCode(Utilities.stringNullValue(dto.getCorrespondingPinCode()));
        employees.setIsCorrespondenceSameAsPermanent(dto.getIsCorrespondenceSameAsPermanent());
        // Permanent Address
        if (Boolean.TRUE.equals(dto.getIsCorrespondenceSameAsPermanent())) {
            employees.setPermanentAddress(dto.getCorrespondingAddress());
            employees.setPermanentCountryId(dto.getCorrespondingCountryId());
            employees.setPermanentStateId(dto.getCorrespondingStateId());
            employees.setPermanentCityId(dto.getCorrespondingCityId());
            employees.setPermanentPinCode(Utilities.stringNullValue(dto.getCorrespondingPinCode()));
        } else {
            employees.setPermanentAddress(Utilities.stringNullValue(dto.getPermanentAddress()));
            employees.setPermanentCountryId(dto.getPermanentCountryId());
            employees.setPermanentStateId(dto.getPermanentStateId());
            employees.setPermanentCityId(dto.getPermanentCityId());
            employees.setPermanentPinCode(Utilities.stringNullValue(dto.getPermanentPinCode()));
        }
        // Bank Details
        employees.setAccountName(Utilities.stringNullValue(dto.getAccountName()));
        employees.setAccountNo(Utilities.stringNullValue(dto.getAccountNo()));
        employees.setIfscCode(Utilities.stringNullValue(dto.getIfscCode()));
        employees.setBankName(Utilities.stringNullValue(dto.getBankName()));
        employees.setBranch(Utilities.stringNullValue(dto.getBranch()));
        if(id == null){
            employees.setCreatedBy(currentEmployeeId);
            employees.setCreatedOn(currentDateTime);
        }else{
            employees.setUpdatedBy(currentEmployeeId);
            employees.setUpdatedOn(currentDateTime);
        }
        return employees;
    }






    @Override
    public Map<String, Object> getAllEmployees(Map<String, Object> param, HttpServletRequest request) {
        Map<String,Object> result_map = new LinkedHashMap<>();
        try{
            Integer pageNo = Utilities.getPageNo(request);
            Long employeeTypeId = Utilities.longValue(param.get("employeeTypeId"));
            Long departmentId = Utilities.longValue(param.get("departmentId"));
            Long designationId = Utilities.longValue(param.get("designationId"));
            Long genderId = Utilities.longValue(param.get("genderId"));
            Long financialYearId = Utilities.longValue(param.get("financialYearId"));

            if(financialYearId == null){
                financialYearId = financialYearRepository.findIdByIsActiveTrue();
            }
            StringBuilder filter = new StringBuilder("(emp.releasing_date IS NULL OR emp.releasing_date <= CURRENT_DATE())");
            if(employeeTypeId != null){
                filter.append(" and emp.employee_type_id = ").append(employeeTypeId);
            }
            if(financialYearId != null){
                filter.append(" and emp.financial_year_id = ").append(financialYearId);
            }
            if(departmentId != null){
                filter.append(" and emp.department_id = ").append(departmentId);
            }
            if(designationId != null){
                filter.append(" and emp.designation_id = ").append(designationId);
            }
            if(genderId != null){
                filter.append(" and emp.gender_id = ").append(genderId);
            }

            Map<Long,String> salutationMap = salutationRepository.getActiveSalutationList().stream().collect(Collectors.toMap(sal -> Utilities.longValue(sal.get("value")), sal -> Utilities.stringValue(sal.get("label"))));
            List<Map<String,Object>> employeeList = new ArrayList<>();
            Long totalCount = Utilities.longValue(customRepo.customSingleValue(EmployeeStaticQuery.EMPLOYEE_DATA_COUNT_QUERY, filter.toString(), null));
            List<Map<String, Object>> employeeMapList = customRepo.customizeDataList(EmployeeStaticQuery.EMPLOYEE_DATA_QUERY, filter.toString(), null, "emp.created_on desc",pageNo);
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
                    contactNo = contactNo.isEmpty() ? "" : contactNoCountryCode.isEmpty() ? contactNo : contactNoCountryCode + contactNo;
                    String dateOfJoining = Utilities.getIndianDateFormatFromUSDate(Utilities.stringValue(employeeMap.get("dateOfJoining")));
                    String department = Utilities.stringValue(employeeMap.get("department"));
                    String designation = Utilities.stringValue(employeeMap.get("designation"));
                    String employeeType = Utilities.stringValue(employeeMap.get("employeeType"));

                    Map<String,Object> dataMap = new LinkedHashMap<>();
                    dataMap.put("id",id);
                    dataMap.put("employeeImage",employeeImage);
                    dataMap.put("employeeCode",employeeCode);
                    dataMap.put("fullName",fullName);
                    dataMap.put("emailId",emailId);
                    dataMap.put("contactNo",contactNo);
                    dataMap.put("dateOfJoining",dateOfJoining);
                    dataMap.put("department",department);
                    dataMap.put("designation",designation);
                    dataMap.put("employeeType",employeeType);
                    employeeList.add(dataMap);
                }
            }
            result_map.put("employees",employeeList);
            Utilities.pagination(result_map,request,totalCount);
        }catch (Exception ex){
            throw new RuntimeException("Something went wrong: "+ ex.getMessage());
        }
        return  result_map;
    }

    @Override
    public Map<String, Object> employeeById(Long id, HttpServletRequest request) {
        Map<String,Object> result_map = new LinkedHashMap<>();
        try{
            if(id == null) throw new ValidationException("Employee not found.");
            List<Map<String, Object>> employeeMapList = customRepo.customizeDataList(EmployeeStaticQuery.EMPLOYEE_DATA_QUERY, "emp.id = "+ id, null, "emp.created_on desc",null);
            result_map.put("employee",employeeMapList);
            return result_map;
        }catch (Exception ex){
            throw new RuntimeException("Something went wrong: "+ ex.getMessage());
        }
    }

    @Override
    public Map<String, Object> employeePreview(Long id, HttpServletRequest request) {
        if (id == null) {
            throw new RuntimeException("Kindly select at least one employee.");
        }
        Map<String, Object> employeePreviewMap = new LinkedHashMap<>();
        Map<Long, String> salutationMap = salutationRepository.findAll().stream().collect(Collectors.toMap(Salutation::getId, Salutation::getName));
        List<Map<String, Object>> employeeMapList = customRepo.customizeDataList(EmployeeStaticQuery.EMPLOYEE_PREVIEW_QUERY, "emp.id = " + id, null, "emp.created_on desc",null);
        if (Utilities.isCollectionNotEmpty(employeeMapList)) {
            Map<String, Object> employeeMap = employeeMapList.get(0);
            employeePreviewMap.put("id", Utilities.longValue(employeeMap.get("id")));
            employeePreviewMap.put("employeeCode", Utilities.stringValue(employeeMap.get("employeeCode")));
            employeePreviewMap.put("employeeProfileImage", Utilities.getServingUrlFromImageString(Utilities.stringValue(employeeMap.get("employeeProfileImage"))));
            String employeeName = Utilities.stringValue(employeeMap.get("fullName"));
            String employeeSalutation = Utilities.stringValue(salutationMap.get(Utilities.longValue(employeeMap.get("salutationId"))));
            employeeName = employeeName.isEmpty() ? "" : employeeSalutation.isEmpty() ? employeeName : employeeSalutation + " " + employeeName;
            employeePreviewMap.put("employeeName", employeeName);
            String employeeContactNo = Utilities.stringValue(employeeMap.get("contactNo"));
            String employeeContactCode = Utilities.stringValue(employeeMap.get("contactNoCountryCode"));
            employeeContactNo = employeeContactNo.isEmpty() ? "" : employeeContactCode.isEmpty() ? employeeContactNo : employeeContactCode + "-" + employeeContactNo;
            employeePreviewMap.put("employeeContactNo", employeeContactNo);
            employeePreviewMap.put("emailId", Utilities.stringValue(employeeMap.get("emailId")));
            Long genderId = Utilities.longValue(employeeMap.get("genderId"));
            if (genderId != null) {employeePreviewMap.put("gender", Utilities.stringValue(genderRepository.findNameById(genderId)));}
            employeePreviewMap.put("dateOfBirth", Utilities.getIndianDateFormatFromUSDate(Utilities.stringValue(employeeMap.get("dateOfBirth"))));
            Long bloodGroupId = Utilities.longValue(employeeMap.get("bloodGroupId"));
            if (bloodGroupId != null) employeePreviewMap.put("bloodGroup", Utilities.stringValue(bloodGroupRepository.findNameById(bloodGroupId)));
            Long maritalStatusId = Utilities.longValue(employeeMap.get("martialStatusId"));
            if (maritalStatusId != null) employeePreviewMap.put("maritalStatus", null);
            employeePreviewMap.put("nationality",Utilities.stringValue(employeeMap.get("nationalityName")));
            Long religionId = Utilities.longValue(employeeMap.get("religionId"));
            if (religionId != null) employeePreviewMap.put("religion", Utilities.stringValue(religionRepository.findNameById(religionId)));
            Long casteId = Utilities.longValue(employeeMap.get("casteId"));
            if (casteId != null) employeePreviewMap.put("caste", Utilities.stringValue(casteRepository.findNameById(casteId)));
            Long departmentId = Utilities.longValue(employeeMap.get("departmentId"));
            if (departmentId != null) employeePreviewMap.put("department", Utilities.stringValue(departmentRepository.findNameById(departmentId)));
            Long designationId = Utilities.longValue(employeeMap.get("designationId"));
            if (designationId != null) employeePreviewMap.put("designation", Utilities.stringValue(designationRepository.findNameById(designationId)));
            employeePreviewMap.put("dateOfJoining", Utilities.getIndianDateFormatFromUSDate(Utilities.stringValue(employeeMap.get("dateOfJoining"))));
            employeePreviewMap.put("uanNo", Utilities.stringValue(employeeMap.get("uanNo")));

            // Father Name
            String fatherName = Utilities.stringValue(employeeMap.get("fatherName"));
            String fatherEmailId = Utilities.stringValue(employeeMap.get("fatherEmailId"));
            String fatherContactCode = Utilities.stringValue(employeeMap.get("fatherContactCode"));
            String fatherContactNo = Utilities.stringValue(employeeMap.get("fatherContactNo"));
            String fatherSalutation = Utilities.stringValue(salutationMap.get(Utilities.longValue(employeeMap.get("fatherSalutationId"))));
            fatherName = fatherName.isEmpty() ? "" : fatherSalutation.isEmpty() ? fatherName : fatherSalutation + " " + fatherName;
            fatherContactNo = fatherContactNo.isEmpty() ? "" : fatherContactCode.isEmpty() ? fatherContactNo : fatherContactCode+"-"+fatherContactNo;
            employeePreviewMap.put("fatherName", fatherName);
            employeePreviewMap.put("fatherEmailId", fatherEmailId);
            employeePreviewMap.put("fatherContactNo", fatherContactNo);

            // Mother Name
            String motherName = Utilities.stringValue(employeeMap.get("motherName"));
            String motherEmailId = Utilities.stringValue(employeeMap.get("motherEmailId"));
            String motherContactCode = Utilities.stringValue(employeeMap.get("motherContactCode"));
            String motherContactNo = Utilities.stringValue(employeeMap.get("motherContactNo"));
            String motherSalutation = Utilities.stringValue(salutationMap.get(Utilities.longValue(employeeMap.get("motherSalutationId"))));
            motherName = motherName.isEmpty() ? "" : motherSalutation.isEmpty() ? motherName : motherSalutation + " " + motherName;
            motherContactNo = motherContactNo.isEmpty() ? "" : motherContactCode.isEmpty() ? motherContactNo : motherContactCode+"-"+motherContactNo;
            employeePreviewMap.put("motherName", motherName);
            employeePreviewMap.put("motherContactNo", motherContactNo);
            employeePreviewMap.put("motherEmailId", motherEmailId);

            // Spouse Name
            String spouseName = Utilities.stringValue(employeeMap.get("spouseName"));
            String spouseEmailId = Utilities.stringValue(employeeMap.get("spouseEmailId"));
            String spouseContactCode = Utilities.stringValue(employeeMap.get("spouseContactCode"));
            String spouseContactNo = Utilities.stringValue(employeeMap.get("spouseContactNo"));
            String spouseSalutation = Utilities.stringValue(salutationMap.get(Utilities.longValue(employeeMap.get("spouseSalutationId"))));
            spouseName = spouseName.isEmpty() ? "" : spouseSalutation.isEmpty() ? spouseName : spouseSalutation + " " + spouseName;
            spouseContactNo = spouseContactNo.isEmpty() ? "" : spouseContactCode.isEmpty() ? spouseContactNo : spouseContactCode+"-"+spouseContactNo;
            employeePreviewMap.put("spouseName", spouseName);
            employeePreviewMap.put("spouseEmailId", spouseEmailId);
            employeePreviewMap.put("spouseContactNo", spouseContactNo);

            // Permanent Address
            employeePreviewMap.put("permanentAddress",Utilities.stringValue(employeeMap.get("permanentAddress")));
            employeePreviewMap.put("permanentCountry",Utilities.stringValue(employeeMap.get("permanentCountry")));
            employeePreviewMap.put("permanentState",Utilities.stringValue(employeeMap.get("permanentState")));
            employeePreviewMap.put("permanentCity",Utilities.stringValue(employeeMap.get("permanentCity")));
            employeePreviewMap.put("permanentPinCode",Utilities.stringValue(employeeMap.get("permanentPinCode")));

            employeePreviewMap.put("correspondingAddress",Utilities.stringValue(employeeMap.get("correspondingAddress")));
            employeePreviewMap.put("correspondingCountry",Utilities.stringValue(employeeMap.get("correspondingCountry")));
            employeePreviewMap.put("correspondingState",Utilities.stringValue(employeeMap.get("correspondingState")));
            employeePreviewMap.put("correspondingCity",Utilities.stringValue(employeeMap.get("correspondingCity")));
            employeePreviewMap.put("correspondingPinCode",Utilities.stringValue(employeeMap.get("correspondingPinCode")));
        }

        return employeePreviewMap;
    }

    @Override
    public Map<String, Object> employeeDocumentByDepartment(Map<String, Object> param, HttpServletRequest request) {
        Map<String,Object> resultMap = new LinkedHashMap<>();
        List<Map<String,Object>> employeeDocumentsList = new ArrayList<>();
        Long departmentId = Utilities.longValue(param.get("departmentId"));
        List<EmployeeDocument> employeeDocumentList = employeeDocumentRepository.findByDepartmentId(departmentId);
        if(Utilities.isCollectionNotEmpty(employeeDocumentList)){
            for(EmployeeDocument employeeDocument : employeeDocumentList){
                Map<String,Object> dataMap = new LinkedHashMap<>();
                dataMap.put("id",employeeDocument.getId());
                dataMap.put("documentName",employeeDocument.getName());
                dataMap.put("isDocumentMandatory",employeeDocument.getIsMandatory());
                dataMap.put("isDocumentExpiryDate",employeeDocument.getIsExpiryDate());
                employeeDocumentsList.add(dataMap);
            }
        }
        resultMap.put("employeeDocumentsList",employeeDocumentsList);
        return resultMap;
    }

    @Override
    public ResponseEntity<?> resignEmployee(MultipartFile file, Map<String, Object> param, HttpServletRequest request) throws IOException {
        Long employeeId = Utilities.longValue(param.get("id"));
        Boolean isAttachmentChange = Utilities.booleanValue(param.get("isAttachmentChange"));
        String resignDate = Utilities.getUSDateFromIndianDate(Utilities.stringValue(param.get("resignDate")));
        String releasingDate = Utilities.getUSDateFromIndianDate(Utilities.stringValue(param.get("releasingDate")));
        Employees employees = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee not found."));
        String reason = Utilities.stringValue(param.get("reason"));
        if(isAttachmentChange){
            String uploadFile = FileManager.uploadFile(file);
            String existingAttachment = Utilities.stringValue(employees.getResignDocument());
            if(!existingAttachment.isEmpty()){
                FileManager.deleteFile(existingAttachment);
            }
            if(uploadFile != null && !uploadFile.isEmpty()){
                employees.setResignDocument(uploadFile);
            }else {
                employees.setResignDocument(null);
            }
        }
        employees.setResignDate(resignDate);
        employees.setResignDocument(releasingDate);
        employees.setResignRemarks(reason);
        employeeRepository.save(employees);
        return ApiResponse.apiSuccess();
    }

    @Override
    public Map<String, Object> resignEmployeeList(Map<String, Object> param, HttpServletRequest request) {
        String fts = Utilities.stringValue(param.get("fts"));
        Long currentBranchId = Utilities.currentBranchId();
        Integer pageNo = Utilities.getPageNo(request);
        String filter = "emp.branch_id = '" + currentBranchId + "' and (emp.emp.resign_date is not null and emp.emp.resign_date < CURATE()";
        Map<String,Object> resultMap = new LinkedHashMap<>();
        List<Map<String,Object>> resignEmployeeList = new ArrayList<>();
        Map<String, Object> departmentMap = customRepo.getAllDepartment();
        Map<String, Object> designationMap = customRepo.getAllDesignation();
        Long totalCount = Utilities.longValue(customRepo.customSingleValue(EmployeeStaticQuery.INACTIVE_EMPLOYEE_COUNT_QUERY, filter, null));
        List<Map<String, Object>> inActiveEmployeeList = customRepo.customizeDataList(EmployeeStaticQuery.INACTIVE_EMPLOYEE_QUERY,filter, null, "emp.resign_date desc",pageNo);
        if(Utilities.isCollectionNotEmpty(inActiveEmployeeList)){
            for(Map<String,Object> inActiveEmployeeMap : inActiveEmployeeList){
                Map<String,Object> dataMap = new LinkedHashMap<>();
                dataMap.put("id",Utilities.longValue(inActiveEmployeeMap.get("id")));
                dataMap.put("employeeName",Utilities.stringValue(inActiveEmployeeMap.get("employeeName")));
                dataMap.put("employeeCode",Utilities.stringValue(inActiveEmployeeMap.get("employeeCode")));
                dataMap.put("employeeProfileImage",Utilities.getServingUrlFromImageString(Utilities.stringValue(inActiveEmployeeMap.get("employeeProfileImage"))));
                dataMap.put("department",Utilities.stringValue(departmentMap.get(Utilities.stringValue(inActiveEmployeeMap.get("departmentId")))));
                dataMap.put("designation",Utilities.stringValue(designationMap.get(Utilities.stringValue(inActiveEmployeeMap.get("designationId")))));
                dataMap.put("releasingDate",Utilities.getIndianDateFormatFromUSDate(Utilities.stringValue(inActiveEmployeeMap.get("releasingDate"))));
                dataMap.put("resignDate",Utilities.getIndianDateFormatFromUSDate(Utilities.stringValue(inActiveEmployeeMap.get("resignDate"))));
                resignEmployeeList.add(dataMap);
            }
        }
        resultMap.put("resignEmployeeList",resignEmployeeList);
        Utilities.pagination(resultMap,request,totalCount);
        return resultMap;
    }
}
