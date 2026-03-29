package com.springboot.Service;

import com.springboot.Model.Employee;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface EmployeeService {
    public Employee createEmployee(Employee employee) throws UnknownHostException;
    public List<Employee> getAllEmployee();
    public Employee getEmployeeById(int id);
    public Employee updateEmployee(Employee employee) throws UnknownHostException;
    public boolean deleteEmployee(int id);
    public List<Employee> searchEmployee(String name);
    public void saveExcelData(MultipartFile file) throws IOException;
    public ByteArrayInputStream downloadExcelData() throws IOException;
    public long countAllEmployees();
    public long countByRoleL1();
    public long countByRoleL2();
    public long countByRoleL3();
    
}
