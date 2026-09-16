package com.springboot.Repository.EmployeeInformation.Employees;

import com.springboot.Model.EmployeeInformation.Employee.EmployeeQualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;

@Repository
public interface EmployeeQualificationRepo extends JpaRepository<EmployeeQualification,Long>{

    List<EmployeeQualification> findByEmployeeId(Long employeeId);
}
