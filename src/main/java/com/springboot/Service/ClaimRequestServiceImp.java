package com.springboot.Service;

import com.springboot.Model.ClaimRequest;
import com.springboot.Model.Employee;
import com.springboot.Repository.ClaimRequestRepository;
import com.springboot.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class ClaimRequestServiceImp implements ClaimRequestService {

    @Autowired
    public ClaimRequestRepository claimRequestRepository;

    @Autowired
    public EmployeeRepository employeeRepository;


    @Override
    public ClaimRequest createClaim(ClaimRequest claimRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Employee employee = employeeRepository.findByEmail(email);
        if(ObjectUtils.isEmpty(employee)){
            throw new RuntimeException("Invalid Employee");
        }
        claimRequest.setEmployee(employee);
        return claimRequestRepository.save(claimRequest);
    }

    @Override
    @Cacheable(value = "claimRequest", key="#id")
    public ClaimRequest getClaimById(int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Employee employee = employeeRepository.findByEmail(email);
        if(ObjectUtils.isEmpty(employee)){
            throw new RuntimeException("Invalid Employee");
        }
        ClaimRequest claimRequest = claimRequestRepository.findById(id);
        if(!claimRequest.getEmployee().getEmail().equals(email)) {
        	throw new RuntimeException("Unauthorized access to this claim");
        }
        return claimRequest;
    }

    @Override
    @Cacheable(value = "claimRequest")
    public List<ClaimRequest> getAllClaimRequestOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Employee employee = employeeRepository.findByEmail(email);
        if(ObjectUtils.isEmpty(employee)){
            throw new RuntimeException("Invalid User");
        }
        return claimRequestRepository.findByEmployeeId(employee.getId());
    }

    @Override
    @CachePut(value = "claimRequest", key="claimRequest.id")
    public ClaimRequest updateClaim(ClaimRequest claimRequest) {
    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String email = authentication.getName();
         Employee employee = employeeRepository.findByEmail(email);
         if(ObjectUtils.isEmpty(employee)){
             throw new RuntimeException("Invalid Employee");
         }
         claimRequest.setEmployee(employee);
        return claimRequestRepository.save(claimRequest);
    }

    @Override
    @CacheEvict(value = "claimRequest", key = "#id")
    public void deleteClaimRequest(int id) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Employee employee = employeeRepository.findByEmail(email);
        if(ObjectUtils.isEmpty(employee)){
            throw new RuntimeException("Invalid Employee");
        }
        ClaimRequest claimRequest =  claimRequestRepository.findById(id);
        if(ObjectUtils.isEmpty(claimRequest)) {
        	throw new UsernameNotFoundException("Employee with Id: "+id+" not found");
        }
        if (!claimRequest.getEmployee().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized access to this claim");
        }
         claimRequestRepository.delete(claimRequest);
    }

	@Override
	public List<ClaimRequest> searchClaims(String claimNo) {
		// TODO Auto-generated method stub
		List<ClaimRequest> list = claimRequestRepository.findByClaimNo(claimNo);
		if(list.isEmpty()) {
			throw new RuntimeException("Claim with claim id "+claimNo+" not found");
		}
		return list;
	}

	@Override
	@Transactional
	@CacheEvict(value = "claimRequest", allEntries = true)
	public int updateClaimRequest(int id, boolean status) {
		// TODO Auto-generated method stub
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String email = authentication.getName();
         Employee employee = employeeRepository.findByEmail(email);
         int updateClaimStatus = 0;
		if(status) {
			//Claim Approve
			updateClaimStatus = claimRequestRepository.updateClaimStatus(id, employee.getRole().name(), "Approved");
			return updateClaimStatus;
		}else {
			//Claim Reject
			updateClaimStatus = claimRequestRepository.updateClaimStatus(id,employee.getRole().name(), "Rejected");
			return updateClaimStatus;
		}
	}

	@Override
	@Cacheable(value = "claimRequest")
	public List<ClaimRequest> getAllCaimRequest() {
		// TODO Auto-generated method stub
		return claimRequestRepository.findAll();
	}
}
