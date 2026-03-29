package com.springboot.Service;


import com.springboot.Model.Employee;
import com.springboot.Model.Role;
import com.springboot.Repository.EmployeeRepository;
import com.springboot.UploadExcel.Excel;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServiceImp implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Excel excel;

    @Override
   
    public Employee createEmployee(Employee employee) throws UnknownHostException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authority = authentication.getAuthorities().toString();
        employee.setCreatedBy(authority);
        employee.setCreateAt(LocalDate.now());
        employee.setCreateMachine(InetAddress.getLocalHost().getHostAddress());

        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setRole(Role.ROLE_L3);
        return employeeRepository.save(employee);
    }

    @Override
    @Cacheable(value = "employee")
    public List<Employee> getAllEmployee() {
    	System.out.println("All Employees: "+ employeeRepository.findAll());
        return employeeRepository.findAll();
    }

    @Override
    @Cacheable(value = "employee", key="#id")
    public Employee getEmployeeById(int id) {
    	System.out.println("EmployeeId :"+ id);
        return employeeRepository.findById(id);
    }

    @Override
    @CachePut(value = "employee", key="employee.id")
    public Employee updateEmployee(Employee employee) throws UnknownHostException {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authority = authentication.getAuthorities().toString();
        employee.setUpdateBy(authority);
        employee.setUpdateAt(LocalDate.now());
        employee.setUpdateMachine(InetAddress.getLocalHost().getHostAddress());
        return employeeRepository.save(employee);
    }

    @Override
    @CacheEvict(value= "employee", key="#id")
    public boolean deleteEmployee(int id) {
        Employee employee = employeeRepository.findById(id);
        if(!ObjectUtils.isEmpty(employee)){
            employeeRepository.delete(employee);
            return true;
        }
        return false;
    }

	@Override
	public List<Employee> searchEmployee(String name) {
		// TODO Auto-generated method stub
		List<Employee> list = null;
//		if (empCode != null && !empCode.isEmpty()) {
//			list = employeeRepository.findByEmpCode(empCode);
//        } else {
        	list = employeeRepository.findByNameContaining(name);
//        }
		return list;
	}
	
	
	@Override
	public void saveExcelData(MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		try {
			List<Employee> employee = excel.convertExcelToListOfEmployee(file.getInputStream());
			employeeRepository.saveAll(employee);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public ByteArrayInputStream downloadExcelData() throws IOException {
		// TODO Auto-generated method stub
		List<Employee> employee = employeeRepository.findAll();
		ByteArrayInputStream dataToExcel = excel.dataToExcel(employee);
		return dataToExcel;
	}

	@Override
	@Cacheable(value = "employee", key = "'allEmployees'")
	public long countAllEmployees() {
		// TODO Auto-generated method stub
		long count = employeeRepository.count();
		System.out.println("Count all: "+ count);
		return count;
	}

	@Override
	@Cacheable(value = "employee", key = "'roleL2'")
	public long countByRoleL2() {
		// TODO Auto-generated method stub
		Long count = employeeRepository.countEmployeesWithRoleL2();
		System.out.println("Role2: "+ count);
		return count;
	}

	@Override
	@Cacheable(value = "employee", key = "'roleL3'")
	public long countByRoleL3() {
		// TODO Auto-generated method stub
		long count = employeeRepository.countEmployeesWithRoleL3();
		System.out.println("Role3: "+ count);
		return count;
	}

	@Override
	@Cacheable(value = "employee", key = "'roleL1'")
	public long countByRoleL1() {
		// TODO Auto-generated method stub
		Long count = employeeRepository.countEmployeesWithRoleL1();
		System.out.println("Role1: "+ count);
		return count;
	}
}
