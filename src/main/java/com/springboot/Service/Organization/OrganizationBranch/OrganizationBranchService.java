package com.springboot.Service.Organization.OrganizationBranch;

import com.springboot.Dto.Organization.OrganizationBranchDTO.OrganizationBranchRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface OrganizationBranchService {

    public ResponseEntity<?> saveUpdateOrganizationBranch(MultipartFile multipartFile, OrganizationBranchRequestDto organizationBranchRequestDto, HttpServletRequest request);
    public Map<String,Object> getOrganizationDetails(Map<String,Object> param,HttpServletRequest request);
}
