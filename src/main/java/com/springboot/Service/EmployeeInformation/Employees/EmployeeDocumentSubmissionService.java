package com.springboot.Service.EmployeeInformation.Employees;

import com.springboot.Dto.EmployeeInformation.EmployeeDto;

import java.io.IOException;

public interface EmployeeDocumentSubmissionService {

    void saveUpdateDocumentDetails(EmployeeDto dto, Long employeeId, Long currentEmployeeId, String currentDateTime) throws IOException;
}
