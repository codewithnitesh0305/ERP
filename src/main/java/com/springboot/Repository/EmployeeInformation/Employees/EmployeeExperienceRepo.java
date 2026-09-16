package com.springboot.Repository.EmployeeInformation.Employees;

import com.springboot.Model.EmployeeInformation.Employee.EmployeeExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeExperienceRepo extends JpaRepository<EmployeeExperience,Long> {

    Optional<EmployeeExperience> findByEmployeeId(Long employeeId);
}
