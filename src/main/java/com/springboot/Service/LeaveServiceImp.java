package com.springboot.Service;


import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.springboot.Model.Employee;
import com.springboot.Model.Leave;
import com.springboot.Repository.EmployeeRepository;
import com.springboot.Repository.LeavRepository;

import jakarta.transaction.Transactional;

@Service
public class LeaveServiceImp implements LeaveService{

	@Autowired
	private LeavRepository leavRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public Leave createLeave(Leave leave) {
		// TODO Auto-generated method stub
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Employee employee = employeeRepository.findByEmail(email);
        if(ObjectUtils.isEmpty(employee)) {
        	throw new RuntimeException("Invalid Employee");
        }
        leave.setEmployee(employee);
		return leavRepository.save(leave);
	}

	@Override
	@Cacheable(value = "leave",key = "#id")
	public Leave getLeaveById(int id) {
		// TODO Auto-generated method stub
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Employee employee = employeeRepository.findByEmail(email);
        if(ObjectUtils.isEmpty(employee)){
            throw new RuntimeException("Invalid Employee");
        }
        Leave leave = leavRepository.findById(id);
        if(!leave.getEmployee().getEmail().equals(email)) {
        	throw new RuntimeException("Unauthorized access to this claim");
        }
		return leave;
	}

	@Override
	@Cacheable(value = "leave")
	public List<Leave> getAllLeaveOfUser() {
		// TODO Auto-generated method stub
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        System.out.println("Email: "+ email);
        Employee employee = employeeRepository.findByEmail(email);
        if(ObjectUtils.isEmpty(employee)){
            throw new RuntimeException("Invalid User");
        }
		return leavRepository.findByEmployeeId(employee.getId());
	}

	@Override
	@CachePut(value = "leave", key="leave.id")
	public Leave updateLeave(Leave leave) {
		// TODO Auto-generated method stub
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String email = authentication.getName();
         Employee employee = employeeRepository.findByEmail(email);
         if(ObjectUtils.isEmpty(employee)){
             throw new RuntimeException("Invalid Employee");
         }
         leave.setEmployee(employee);
		return leavRepository.save(leave);
	}

	@Override
	@CacheEvict(value = "leave", key = "#id")
	public void deleteLeave(int id) {
		// TODO Auto-generated method stub
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Employee employee = employeeRepository.findByEmail(email);
        if(ObjectUtils.isEmpty(employee)){
            throw new RuntimeException("Invalid Employee");
        }
		Leave leave = leavRepository.findById(id);
		if (!leave.getEmployee().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized access to this leave");
        }
		leavRepository.delete(leave);
	}

	@Override
	@Transactional
	@CacheEvict(value = "leave", allEntries = true)
	public int updateLeaveRequest(int id, boolean status) {
		// TODO Auto-generated method stub
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Employee employee = employeeRepository.findByEmail(email);
		int updateLeaveStatus = 0;
		if (status) {
			// Leave Approve
			updateLeaveStatus = leavRepository.updateLeaveStatus(id, employee.getRole().name(), "Approved");
			return updateLeaveStatus;
		} else {
			// Leave Reject
			updateLeaveStatus = leavRepository.updateLeaveStatus(id, employee.getRole().name(), "Rejected");
			return updateLeaveStatus;
		}
	}

	@Override
	@Cacheable(value = "leave")
	public List<Leave> getAllLeave() {
		// TODO Auto-generated method stub
		return leavRepository.findAll();
	}

}
