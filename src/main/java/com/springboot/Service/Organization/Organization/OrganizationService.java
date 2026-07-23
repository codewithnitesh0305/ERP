package com.springboot.Service.Organization.Organization;

import com.springboot.Dto.Organization.OrganizationDTO.OrganizationRequestDto;
import com.springboot.Dto.Organization.OrganizationDTO.OrganizationResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface OrganizationService {

    ResponseEntity<?> saveUpdateOrganization(OrganizationRequestDto organizationRequestDto, HttpServletRequest request) throws IOException;
    OrganizationResponseDto getOrganizationDetails(Long organizationId,HttpServletRequest request);

}
