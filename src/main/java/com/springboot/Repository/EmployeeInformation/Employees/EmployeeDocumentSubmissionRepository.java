package com.springboot.Repository.EmployeeInformation.Employees;

import com.springboot.Model.EmployeeInformation.Employee.EmployeeDocumentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDocumentSubmissionRepository extends JpaRepository<EmployeeDocumentSubmission,Long> {
}
