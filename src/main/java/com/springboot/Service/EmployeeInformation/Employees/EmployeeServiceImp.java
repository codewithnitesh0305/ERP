package com.springboot.Service.EmployeeInformation.Employees;

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
import com.springboot.Repository.EmployeeInformation.Setup.EmployeeTypeRepository;
import com.springboot.Repository.Organization.*;
import com.springboot.Repository.User.UserRepository;
import com.springboot.Service.Cloudinary.FileManager;
import com.springboot.Service.EmployeeInformation.Setup.EmployeeAutoNumberSchemeService;
import com.springboot.Utility.ApiResponse;
import com.springboot.Utility.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
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
    private final EmployeeDocumentRepository employeeDocumentRepository;
    private final EmployeeDocumentSubmissionRepository employeeDocumentSubmissionRepository;
    private final FinancialYearRepository financialYearRepository;
    private final CustomRepo customRepo;
    private final SalutationRepository salutationRepository;
    private final GenderRepository genderRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;
    private final EmployeeTypeRepository employeeTypeRepository;
    private final EmployeeAutoNumberSchemeService employeeAutoNumberSchemeService;
    private final BloodGroupRepository bloodGroupRepository;
    private final ReligionRepository religionRepository;
    private final CasteRepository casteRepository;

    @Override
    public ResponseEntity<?> saveUpdateEmployee(Map<String, MultipartFile> fileMap, EmployeeDto dto, HttpServletRequest request) {
        try {
            Long id = dto.getId();
            String employeeContactCode = dto.getContactNoCountryCode();
            String fatherContactCode = dto.getFatherContactNoCountryCode();
            String motherContactCode = dto.getMotherContactNoCountryCode();
            String spouseContactCode = dto.getSpouseContactNoCountryCode();
            Long departmentId = dto.getDepartmentId();
            Boolean isEmployeeProfileImageChange = dto.getIsChange();
            Long financialYearId = dto.getFinancialYearId();
            String employeeCode = dto.getEmployeeCode();

            if (!Objects.equals(dto.getAccountNo(), dto.getReEnterAccountNo())) return ApiResponse.apiValidation("Account No. and Re-enter Account No. should match.");
            Employees employees = id == null ? new Employees() : employeeRepository.findById(dto.getId()).orElseThrow(() -> new ResourceNotFoundException("Employee not found."));

            if(employeeCode == null || employeeCode.isEmpty()){
                employeeCode = employeeAutoNumberSchemeService.generateEmployeeAutoNumber(financialYearId, departmentId);
            }

            // Basic Details
            MultipartFile employeeImage = fileMap.get("employeeProfileImage");
            if(employeeImage != null){
                String employeeProfileImage = FileManager.uploadFile(employeeImage);
                if(isEmployeeProfileImageChange){ // default true
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
            employees.setEmployeeCode(employeeCode);
            employees.setFinancialYearId(dto.getFinancialYearId());
            employees.setSalutationId(dto.getSalutationId());
            employees.setFirstName(dto.getFirstName());
            employees.setMiddleName(dto.getMiddleName());
            employees.setLastName(dto.getLastName());
            String fullName = dto.getFirstName();
            if (dto.getMiddleName() != null && !dto.getMiddleName().isEmpty()) fullName += " " + dto.getMiddleName();
            if (dto.getLastName() != null && !dto.getLastName().isEmpty()) fullName += " " + dto.getLastName();
            employees.setFullName(fullName);
            employees.setGenderId(dto.getGenderId());
            employees.setDateOfBirth(Utilities.getUSDateFromIndianDate(dto.getDateOfBirth()));

            employees.setContactNoCountryCode(dto.getContactNoCountryCode());
            employees.setContactNo(dto.getContactNo());
            employees.setEmailId(dto.getEmailId());

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
            employees.setFatherContactNoCountryCode(fatherContactCode);
            employees.setFatherContactNo(dto.getFatherContactNo());
            employees.setFatherEmailId(dto.getFatherEmailId());

            // Mother Details
            employees.setMotherSalutationId(dto.getMotherSalutationId());
            employees.setMotherName(dto.getMotherName());
            employees.setMotherContactNoCountryCode(motherContactCode);
            employees.setMotherContactNo(dto.getMotherContactNo());
            employees.setMotherEmailId(dto.getMotherEmailId());

            // Spouse Details
            employees.setSpouseSalutationId(dto.getSpouseSalutationId());
            employees.setSpouseName(dto.getSpouseName());
            employees.setSpouseContactNoCountryCode(spouseContactCode);
            employees.setSpouseContactNo(dto.getSpouseContactNo());
            employees.setSpouseEmailId(dto.getSpouseEmailId());

            // Correspondence Address
            employees.setCorrespondingAddress(dto.getCorrespondingAddress());
            employees.setCorrespondingCountryId(dto.getCorrespondingCountryId());
            employees.setCorrespondingStateId(dto.getCorrespondingStateId());
            employees.setCorrespondingCityId(dto.getCorrespondingCityId());
            employees.setCorrespondingPinCode(dto.getCorrespondingPinCode());
            employees.setIsCorrespondenceSameAsPermanent(dto.getIsCorrespondenceSameAsPermanent());
            // Permanent Address
            if (Boolean.TRUE.equals(dto.getIsCorrespondenceSameAsPermanent())) {
                employees.setPermanentAddress(dto.getCorrespondingAddress());
                employees.setPermanentCountryId(dto.getCorrespondingCountryId());
                employees.setPermanentStateId(dto.getCorrespondingStateId());
                employees.setPermanentCityId(dto.getCorrespondingCityId());
                employees.setPermanentPinCode(dto.getCorrespondingPinCode());
            } else {
                employees.setPermanentAddress(dto.getPermanentAddress());
                employees.setPermanentCountryId(dto.getPermanentCountryId());
                employees.setPermanentStateId(dto.getPermanentStateId());
                employees.setPermanentCityId(dto.getPermanentCityId());
                employees.setPermanentPinCode(dto.getPermanentPinCode());
            }
            // Bank Details
            employees.setAccountName(dto.getAccountName());
            employees.setAccountNo(dto.getAccountNo());
            employees.setIfscCode(dto.getIfscCode());
            employees.setBankName(dto.getBankName());
            employees.setBranch(dto.getBranch());
            Long employeeId = employeeRepository.save(employees).getId();
            processedDocument(fileMap, dto, employeeId, request);
            if (dto.getId() == null) {
                Users user = new Users();
                user.setEmailId(dto.getEmailId());
                user.setEmployeeId(employeeId);
                user.setCreatedBy(null);
                user.setCreatedOn(Utilities.getCurrentDateTime());
                userRepository.save(user);
            }
            return ApiResponse.apiSuccess();
        } catch (Exception ex) {
            throw new RuntimeException("Something went wrong " + ex.getMessage());
        }
    }


    public void processedDocument(Map<String, MultipartFile> fileMap, EmployeeDto dto, Long employeeId, HttpServletRequest request) throws IOException {
        String documentListStr = dto.getDocumentJson();
        Long departmentId = dto.getDepartmentId();
        String createdOn = Utilities.getCurrentDateTime();
        List<Map<String, Object>> documentMapList = parseEmployeeDocumentJson(documentListStr);
        Map<Long, Boolean> employeeDocumentMap = new HashMap<>();
        List<EmployeeDocument> employeeDocumentList = employeeDocumentRepository.findByDepartmentId(departmentId);
        if (Utilities.isCollectionNotEmpty(employeeDocumentList)) {
            for (EmployeeDocument employeeDocument : employeeDocumentList) {
                employeeDocumentMap.put(employeeDocument.getId(), employeeDocument.getIsMandatory());
            }
        }
        Map<Long, EmployeeDocumentSubmission> existingEmployeeDocumentMap = new LinkedHashMap<>();
        if (employeeId != null) {
            List<EmployeeDocumentSubmission> existingEmployeeDocument = employeeDocumentSubmissionRepository.findByEmployeeId(employeeId);
            if (Utilities.isCollectionNotEmpty(existingEmployeeDocument)) {
                for (EmployeeDocumentSubmission documentSubmission : existingEmployeeDocument) {
                    existingEmployeeDocumentMap.put(documentSubmission.getId(), documentSubmission);
                }
            }
        }

        List<EmployeeDocumentSubmission> documentSubmissionList = new ArrayList<>();
        if (Utilities.isCollectionNotEmpty(documentMapList)) {
            for (Map<String, Object> documentDataMap : documentMapList) {
                Long id = Utilities.longValue(documentDataMap.get("id"));
                Long documentId = Utilities.longValue(documentDataMap.get("documentId"));
                String documentName = Utilities.stringValue(documentDataMap.get("documentName"));
                String documentExpiryDate = Utilities.getUSDateFromIndianDate(Utilities.stringValue(documentDataMap.get("documentExpiryDate")));
                String fileName = Utilities.stringValue(documentDataMap.get("fileName"));
                String documentNumber = Utilities.stringValue(documentDataMap.get("documentNumber"));
                Boolean isFileChange = Utilities.booleanValue(documentDataMap.get("isFileChange"));
                Boolean isDocumentMandatory = employeeDocumentMap.get(documentId);
                if (Boolean.TRUE.equals(isDocumentMandatory) && (documentNumber.isEmpty() || documentExpiryDate.isEmpty())) {
                    throw new ValidationException(documentName + " details is mandatory");
                }
                EmployeeDocumentSubmission employeeDocumentSubmission;
                MultipartFile multipartPart = fileMap.get(fileName);
                if (existingEmployeeDocumentMap.containsKey(id)) {
                    employeeDocumentSubmission = existingEmployeeDocumentMap.get(id);
                    if (Boolean.TRUE.equals(isFileChange)) {
                        String documentJson = FileManager.uploadFile(multipartPart);
                        if (documentJson != null && !documentJson.isEmpty()) {
                            String oldDocument = employeeDocumentSubmission.getDocumentUrl();
                            if (oldDocument != null && !oldDocument.isEmpty()) {
                                FileManager.deleteFile(oldDocument);
                            }
                            employeeDocumentSubmission.setDocumentUrl(documentJson);
                        } else {
                            employeeDocumentSubmission.setDocumentUrl(null);
                        }
                        employeeDocumentSubmission.setUpdatedBy(null);
                        employeeDocumentSubmission.setUpdatedOn(Utilities.getCurrentDateTime());
                    }
                } else {
                    employeeDocumentSubmission = new EmployeeDocumentSubmission();
                    String documentJson = FileManager.uploadFile(multipartPart);
                    employeeDocumentSubmission.setDocumentUrl(documentJson);
                    employeeDocumentSubmission.setDepartmentId(departmentId);
                    employeeDocumentSubmission.setDocumentId(documentId);
                    employeeDocumentSubmission.setEmployeeId(employeeId);
                    employeeDocumentSubmission.setCreatedBy(null);
                    employeeDocumentSubmission.setCreatedOn(createdOn);
                }
                employeeDocumentSubmission.setDocumentNo(documentNumber);
                employeeDocumentSubmission.setDocumentExpiryDate(documentExpiryDate);
                documentSubmissionList.add(employeeDocumentSubmission);
            }
        }
        employeeDocumentSubmissionRepository.saveAll(documentSubmissionList);
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
            StringBuilder filter = new StringBuilder("emp.releasing_date <= CURRENT_DATE()");
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

            Map<Long,String> employeeTypeMap = employeeTypeRepository.getActiveEmployeeTypeList().stream().collect(Collectors.toMap(sal -> Utilities.longValue(sal.get("value")), sal -> Utilities.stringValue(sal.get("label"))));
            Map<Long,String> salutationMap = salutationRepository.getActiveSalutationList().stream().collect(Collectors.toMap(sal -> Utilities.longValue(sal.get("value")), sal -> Utilities.stringValue(sal.get("label"))));
            Map<Long,String> genderMap = genderRepository.getActiveGenderList().stream().collect(Collectors.toMap(gen -> Utilities.longValue(gen.get("value")), gen -> Utilities.stringValue(gen.get("label"))));
            Map<Long,String> departmentMap = departmentRepository.getActiveDepartmentLIst().stream().collect(Collectors.toMap(gen -> Utilities.longValue(gen.get("value")), gen -> Utilities.stringValue(gen.get("label"))));
            Map<Long,String> designationMap = designationRepository.getAcitveDesignationList().stream().collect(Collectors.toMap(gen -> Utilities.longValue(gen.get("value")), gen -> Utilities.stringValue(gen.get("label"))));

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
                    contactNo = contactNo.isEmpty() ? "" : contactNoCountryCode.isEmpty() ? contactNo : contactNoCountryCode + contactNo;
                    String gender = Utilities.stringValue(genderMap.get(Utilities.longValue(employeeMap.get("genderId"))));
                    String dateOfBirth = Utilities.getIndianDateFormatFromUSDate(Utilities.stringValue(employeeMap.get("dateOfBirth")));
                    String dateOfJoining = Utilities.getIndianDateFormatFromUSDate(Utilities.stringValue(employeeMap.get("dateOfJoining")));
                    String department = Utilities.stringValue(departmentMap.get(Utilities.stringValue(employeeMap.get("departmentId"))));
                    String designation = Utilities.stringValue(designationMap.get(Utilities.stringValue(employeeMap.get("designationId"))));
                    String employeeType = Utilities.stringValue(employeeTypeMap.get(Utilities.stringValue(employeeMap.get("employeeTypeId"))));

                    Map<String,Object> dataMap = new LinkedHashMap<>();
                    dataMap.put("id",id);
                    dataMap.put("employeeImage",employeeImage);
                    dataMap.put("employeeCode",employeeCode);
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
            if(id == null) throw new ValidationException("Employee not found.");
            List<Map<String, Object>> employeeMapList = customRepo.customizeDataList(EmployeeStaticQuery.EMPLOYEE_DATA_QUERY, "emp.id = "+ id, null, "emp.created_on desc");
            result_map.put("employee",employeeMapList);
            return result_map;
        }catch (Exception ex){
            throw new RuntimeException("Something went wrong: "+ ex.getMessage());
        }
    }

    @Override
    public Map<String, Object> employeePreview(Map<String, Object> param, HttpServletRequest request) {
        Long employeeId = Utilities.longValue(param.get("id"));
        if (employeeId == null) {
            throw new RuntimeException("Kindly select at least one employee.");
        }
        Map<String, Object> employeePreviewMap = new LinkedHashMap<>();
        Map<Long, String> salutationMap = salutationRepository.findAll().stream().collect(Collectors.toMap(Salutation::getId, Salutation::getName));
        List<Map<String, Object>> employeeMapList = customRepo.customizeDataList(EmployeeStaticQuery.EMPLOYEE_PREVIEW_QUERY, "emp.id = " + employeeId, null, "emp.created_on desc");
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
        StringBuilder filter = new StringBuilder();

        Map<String,Object> resultMap = new LinkedHashMap<>();
        List<Map<String,Object>> resignEmployeeList = new ArrayList<>();
        Map<String, Object> departmentMap = customRepo.getAllDepartment();
        Map<String, Object> designationMap = customRepo.getAllDesignation();
        List<Map<String, Object>> inActiveEmployeeList = customRepo.customizeDataList(EmployeeStaticQuery.INACTIVE_EMPLOYEE_QUERY, filter.toString(), null, "emp.resign_date desc");
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
        return resultMap;
    }
}
