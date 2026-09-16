package com.springboot.Service.EmployeeInformation.Employees;

import com.springboot.Dto.EmployeeInformation.EmployeeExperienceDto;
import com.springboot.Model.EmployeeInformation.Employee.EmployeeExperience;
import com.springboot.Repository.EmployeeInformation.Employees.EmployeeExperienceRepo;
import com.springboot.Service.Cloudinary.FileManager;
import com.springboot.Utility.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeExperienceServiceImp implements  EmployeeExperienceService{

    private final EmployeeExperienceRepo employeeExperienceRepo;

    public void saveUpdateExperienceDetails(List<EmployeeExperienceDto> employeeExperienceDtoList, Set<Long> deletedEmployeeExperienceIds, Long employeeId, Long currentEmployeeId, String currentDateTime) throws IOException {
        if(Utilities.isCollectionNotEmpty(deletedEmployeeExperienceIds)) employeeExperienceRepo.deleteAllById(deletedEmployeeExperienceIds);
        if(Utilities.isCollectionNotEmpty(employeeExperienceDtoList)){
            List<EmployeeExperience> employeeExperienceList = new ArrayList<>();
            Map<Long, EmployeeExperience> employeeExperienceMap = employeeExperienceRepo.findByEmployeeId(employeeId).stream().collect(Collectors.toMap(EmployeeExperience::getId, Function.identity()));
            for (EmployeeExperienceDto employeeExperienceDto : employeeExperienceDtoList){
                Long id =  Utilities.longValue(employeeExperienceDto.getId());
                EmployeeExperience employeeExperience = employeeExperienceMap.getOrDefault(id, new EmployeeExperience());
                if(id == null){
                    employeeExperience.setCreatedBy(currentEmployeeId);
                    employeeExperience.setCreatedOn(currentDateTime);
                }else{
                    employeeExperience.setUpdatedBy(currentEmployeeId);
                    employeeExperience.setUpdatedOn(currentDateTime);
                }
                Boolean isFileChange = Utilities.booleanValue(employeeExperienceDto.getIsFileChange());
                String qualificationAttachment = null;
                if(isFileChange){
                    qualificationAttachment = FileManager.uploadFile(employeeExperienceDto.getAttachment());
                    String existingExperienceAttachment = employeeExperience.getAttachment();
                    if(existingExperienceAttachment != null && !existingExperienceAttachment.isEmpty()){
                        FileManager.deleteFile(existingExperienceAttachment);
                    }
                    employeeExperience.setAttachment(qualificationAttachment);
                }
                employeeExperience.setEmployeeId(employeeId);
                employeeExperience.setCompanyName(employeeExperienceDto.getCompanyName());
                employeeExperience.setDesignation(Utilities.stringNullValue(employeeExperienceDto.getDesignation()));
                employeeExperienceList.add(employeeExperience);
            }
            employeeExperienceRepo.saveAll(employeeExperienceList);
        }
    }


}
