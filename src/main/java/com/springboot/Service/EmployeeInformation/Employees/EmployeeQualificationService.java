package com.springboot.Service.EmployeeInformation.Employees;

import com.springboot.Dto.EmployeeInformation.EmployeeQualificationDto;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface EmployeeQualificationService {

    void saveUpdateQualificationDetails(List<EmployeeQualificationDto> employeeQualificationDtoList, Set<Long> deletedEmployeeQualificationIds, Long employeeId, Long currentEmployeeId, String currentDateTime) throws IOException;
}
