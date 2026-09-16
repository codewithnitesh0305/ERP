package com.springboot.Service.EmployeeInformation.Employees;

import com.springboot.Dto.EmployeeInformation.EmployeeQualificationDto;
import com.springboot.Model.EmployeeInformation.Employee.EmployeeQualification;
import com.springboot.Repository.EmployeeInformation.Employees.EmployeeQualificationRepo;
import com.springboot.Service.Cloudinary.FileManager;
import com.springboot.Utility.Utilities;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeQualificationServiceImp implements EmployeeQualificationService {

    private final EmployeeQualificationRepo employeeQualificationRepo;

    public void saveUpdateQualificationDetails(List<EmployeeQualificationDto> employeeQualificationDtoList, Set<Long> deletedEmployeeQualificationIds, Long employeeId, Long currentEmployeeId, String currentDateTime) throws IOException {
        if(Utilities.isCollectionNotEmpty(deletedEmployeeQualificationIds)) employeeQualificationRepo.deleteAllById(deletedEmployeeQualificationIds);
        if (Utilities.isCollectionNotEmpty(employeeQualificationDtoList)) {
            Map<Long, EmployeeQualification> employeeQualificationMap = employeeQualificationRepo.findByEmployeeId(employeeId).stream().collect(Collectors.toMap(EmployeeQualification::getId, Function.identity()));
            List<EmployeeQualification> employeeQualificationList = new ArrayList<>();

            for (EmployeeQualificationDto qualificationDto : employeeQualificationDtoList) {
                Long id = Utilities.longValue(qualificationDto.getId());
                EmployeeQualification employeeQualification = employeeQualificationMap.getOrDefault(id, new EmployeeQualification());
                if (id == null) {
                    employeeQualification.setCreatedBy(currentEmployeeId);
                    employeeQualification.setCreatedOn(currentDateTime);
                } else {
                    employeeQualification.setUpdateBy(currentEmployeeId);
                    employeeQualification.setUpdatedOn(currentDateTime);
                }
                employeeQualification.setEmployeeId(employeeId);
                employeeQualification.setQualificationId(Utilities.longValue(qualificationDto.getQualificationId()));
                employeeQualification.setSpecialization(Utilities.stringNullValue(qualificationDto.getSpecialization()));
                employeeQualification.setBoardUniversity(Utilities.stringNullValue(qualificationDto.getBoardUniversity()));
                employeeQualification.setCompletionYear(Utilities.integerValue(qualificationDto.getCompletionYear()));
                employeeQualification.setResultValue(Utilities.integerValue(qualificationDto.getResultValue()));
                Boolean isFileChange = Utilities.booleanValue(qualificationDto.getIsFileChange());
                String qualificationAttachment = null;
                if (isFileChange) {
                    qualificationAttachment = FileManager.uploadFile(qualificationDto.getAttachment());
                    String existingQualificationAttachment = employeeQualification.getAttachment();
                    if (existingQualificationAttachment != null && !existingQualificationAttachment.isEmpty()) {
                        FileManager.deleteFile(existingQualificationAttachment);
                    }
                    employeeQualification.setAttachment(qualificationAttachment);
                }
                employeeQualificationList.add(employeeQualification);
            }
            if (Utilities.isCollectionNotEmpty(employeeQualificationList)) employeeQualificationRepo.saveAll(employeeQualificationList);
        }
    }
}
