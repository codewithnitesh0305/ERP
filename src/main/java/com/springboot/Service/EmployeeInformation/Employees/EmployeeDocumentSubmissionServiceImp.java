package com.springboot.Service.EmployeeInformation.Employees;

import com.springboot.Dto.EmployeeInformation.DocumentSubmissionDto;
import com.springboot.Dto.EmployeeInformation.EmployeeDto;
import com.springboot.Exception.ValidationException;
import com.springboot.Model.EmployeeInformation.Employee.EmployeeDocumentSubmission;
import com.springboot.Model.EmployeeInformation.Setup.EmployeeDocument;
import com.springboot.Repository.EmployeeInformation.Employees.EmployeeDocumentSubmissionRepository;
import com.springboot.Repository.EmployeeInformation.Setup.EmployeeDocumentRepository;
import com.springboot.Service.Cloudinary.FileManager;
import com.springboot.Utility.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeDocumentSubmissionServiceImp implements EmployeeDocumentSubmissionService{

    private final EmployeeDocumentRepository employeeDocumentRepository;
    private final EmployeeDocumentSubmissionRepository employeeDocumentSubmissionRepository;

    public void saveUpdateDocumentDetails(EmployeeDto dto, Long employeeId, Long currentEmployeeId, String currentDateTime) throws IOException {
        Long departmentId = dto.getDepartmentId();
        String createdOn = Utilities.getCurrentDateTime();
        List<DocumentSubmissionDto> documentMapList = dto.getDocumentSubmissionList();
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
            for (DocumentSubmissionDto documentSubmissionDto : documentMapList) {
                Long id = Utilities.longValue(documentSubmissionDto.getId());
                Long documentId = Utilities.longValue(documentSubmissionDto.getDocumentId());
                String documentName = Utilities.stringValue(documentSubmissionDto.getDocumentName());
                String expiryDate = Utilities.getUSDateFromIndianDate(Utilities.stringValue(documentSubmissionDto.getExpiryDate()));
                String submissionDate = Utilities.getUSDateFromIndianDate(Utilities.stringValue(documentSubmissionDto.getSubmissionDate()));
                String documentNumber = Utilities.stringNullValue(documentSubmissionDto.getDocumentNumber());
                Boolean isFileChange = Utilities.booleanValue(documentSubmissionDto.getIsFileChange());
                Boolean isDocumentMandatory = employeeDocumentMap.get(documentId);
                if (Boolean.TRUE.equals(isDocumentMandatory) && (documentNumber.isEmpty() || expiryDate.isEmpty() && submissionDate.isEmpty())) {
                    throw new ValidationException(documentName + " details is mandatory");
                }
                EmployeeDocumentSubmission employeeDocumentSubmission;
                MultipartFile multipartPart = documentSubmissionDto.getMultipartFile();
                if (multipartPart != null && existingEmployeeDocumentMap.containsKey(id)) {
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
                        employeeDocumentSubmission.setUpdatedBy(currentEmployeeId);
                        employeeDocumentSubmission.setUpdatedOn(currentDateTime);
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
                employeeDocumentSubmission.setDocumentExpiryDate(expiryDate);
                employeeDocumentSubmission.setSubmissionDate(submissionDate);
                documentSubmissionList.add(employeeDocumentSubmission);
            }
        }
        if (!documentSubmissionList.isEmpty()) employeeDocumentSubmissionRepository.saveAll(documentSubmissionList);
    }
}
