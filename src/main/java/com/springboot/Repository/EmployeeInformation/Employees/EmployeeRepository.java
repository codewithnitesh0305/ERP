package com.springboot.Repository.EmployeeInformation.Employees;

import com.springboot.Model.EmployeeInformation.Employee.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees,Long> {
}
