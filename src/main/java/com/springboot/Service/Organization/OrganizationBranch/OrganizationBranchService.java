package com.springboot.Service.Organization.OrganizationBranch;

import com.springboot.Dto.Organization.OrganizationBranchDTO.OrganizationBranchRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface OrganizationBranchService {

    public ResponseEntity<?> saveUpdateOrganizationBranch(OrganizationBranchRequestDto organizationBranchRequestDto, HttpServletRequest request);
    public Map<String,Object> getOrganizationDetails(Long organizationBranchId,HttpServletRequest request);
}
