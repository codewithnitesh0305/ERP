package com.springboot.Service.EmployeeInformation.Setup;

import com.springboot.Model.EmployeeInformation.Setup.EmployeeDocument;
import com.springboot.Repository.CustomRepo.CustomRepo;
import com.springboot.Repository.EmployeeInformation.Setup.EmployeeDocumentRepository;
import com.springboot.Utility.ApiResponse;
import com.springboot.Utility.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeDocumentServiceImp implements EmployeeDocumentService{

    private final EmployeeDocumentRepository employeeDocumentRepository;
    private final CustomRepo customRepo;

    @Override
    public ResponseEntity<?> saveUpdateEmployeeDocument(Map<String, Object> param, HttpServletRequest request) {
        try{
            Long id = Utilities.longValue(param.get("id"));
            String documentName = Utilities.stringValue(param.get("documentName"));
            Long departmentId = Utilities.longValue(param.get("departmentId"));
            Boolean isAllowToUploadDocument = Utilities.booleanValue(param.get("isAllowToUploadDocument"));
            Boolean isReminderRequired = Utilities.booleanValue(param.get("isReminderRequired"));
            Long authorizedEmployeeId = Utilities.longValue(param.get("authorizedEmployeeId"));
            Boolean isMandatory = Utilities.booleanValue(param.get("isMandatory"));

            if(!documentName.isEmpty()) return ApiResponse.apiValidation("Document name is required.");

            EmployeeDocument existingDocument =  employeeDocumentRepository.findByName(documentName);
            if(existingDocument != null && existingDocument.getName().equals(documentName) && existingDocument.getId().equals(id)) return ApiResponse.apiValidation("Document already exist.");

            EmployeeDocument employeeDocument = id == null ? new EmployeeDocument() : employeeDocumentRepository.findById(id).orElseThrow(() -> new RuntimeException("Document not found"));
            if(id == null){
                employeeDocument.setCreatedBy(null);
                employeeDocument.setCreatedOn(Utilities.getCurrentDateTime());
            }else{
                employeeDocument.setUpdatedBy(null);
                employeeDocument.setUpdatedOn(Utilities.getCurrentDateTime());
            }

            employeeDocument.setName(documentName);
            employeeDocument.setDepartmentId(departmentId);
            employeeDocument.setIsDocumentUpload(isAllowToUploadDocument);
            employeeDocument.setIsReminderRequired(isReminderRequired);
            employeeDocument.setAuthorizedEmployeeId(authorizedEmployeeId);
            employeeDocument.setIsMandatory(isMandatory);
            employeeDocumentRepository.save(employeeDocument);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return ApiResponse.apiSuccess();
    }

    @Override
    public Map<String, Object> getAllEmployeeDocument(Map<String, Object> param, HttpServletRequest request) {
        Map<String,Object> result_map = new LinkedHashMap<>();
        try{
            String fts = Utilities.stringValue(param.get("fts"));
            fts = fts.isEmpty() ? null : "%"+fts+"%";

            List<Map<String,Object>> documentList = new ArrayList<>();
            Map<Long,String> departmentMap = (Map<Long, String>) customRepo.getAllDepartment().get("departmentMap");

            List<Map<String,Object>> employeeDocumentList = employeeDocumentRepository.getAllDocument(fts);
            if(employeeDocumentList != null && !employeeDocumentList.isEmpty()){
                for(Map<String,Object> documentMap : employeeDocumentList){
                    Map<String, Object> map = new LinkedHashMap<>();
                    String name = Utilities.stringValue(documentMap.get("name"));
                    Long departmentId = Utilities.longValue(documentMap.get("department_id"));
                    String departmentName = Utilities.stringValue(departmentMap.get(departmentId));
                    Boolean isEmployeeAllowToUpload = Utilities.booleanValue(documentMap.get("is_document_upload"));
                    String isEmployeeAllowToUploadStr = isEmployeeAllowToUpload ? "Yes" : "No";
                    Boolean isReminderRequired = Utilities.booleanValue(documentMap.get("is_reminder_required"));
                    String isReminderRequiredStr = isReminderRequired ? "Yes" : "No";
                    Long authorizedEmployeeId = Utilities.longValue(documentMap.get("authorized_employee_id"));
                    Boolean isMandatory = Utilities.booleanValue(documentMap.get("is_mandatory"));
                    String isMandatoryStr = isMandatory ? "Yes" : "No";

                    map.put("name", name);
                    map.put("departmentId", departmentId);
                    map.put("departmentName", departmentName);
                    map.put("isEmployeeAllowToUpload", isEmployeeAllowToUploadStr);
                    map.put("isReminderRequired", isReminderRequiredStr);
                    map.put("authorizedEmployeeId", authorizedEmployeeId);
                    map.put("isMandatory", isMandatoryStr);
                    documentList.add(map);
                }
            }
            result_map.put("documentList",documentList);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> deleteEmployeeDocument(Map<String, Object> param, HttpServletRequest request) {
        try{
            String employeeDocumentIds = Utilities.stringValue(param.get("ids"));
            if(employeeDocumentIds.isEmpty()) return ApiResponse.apiValidation("Kindly select at least on row.");
            Set<Long> idsSet = Utilities.getSetOfCommaValue(employeeDocumentIds);
            if(!idsSet.isEmpty()) employeeDocumentRepository.deleteAllById(idsSet);
        }catch (Exception ex){
            return ApiResponse.apiFailure(ex);
        }
        return ApiResponse.apiSuccess();
    }
}
