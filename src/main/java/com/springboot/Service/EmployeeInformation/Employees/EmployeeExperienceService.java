package com.springboot.Service.EmployeeInformation.Employees;

import com.springboot.Dto.EmployeeInformation.EmployeeExperienceDto;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface EmployeeExperienceService {

    public void saveUpdateExperienceDetails(List<EmployeeExperienceDto> employeeExperienceDtoList, Set<Long> deletedEmployeeExperienceIds, Long employeeId, Long currentEmployeeId, String currentDateTime) throws IOException;
}
