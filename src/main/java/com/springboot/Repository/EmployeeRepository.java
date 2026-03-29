package com.springboot.Repository;

import com.springboot.Model.Employee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    public Employee findById(int id);
    public Employee findByEmail(String email);
    List<Employee> findByEmpCode(String empCode);
    List<Employee> findByNameContaining(String name);
    long count();
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.role = 'Role_L1'")
    Long countEmployeesWithRoleL1();
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.role = 'Role_L2'")
    Long countEmployeesWithRoleL2();
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.role = 'Role_L3'")
    Long countEmployeesWithRoleL3();
}
