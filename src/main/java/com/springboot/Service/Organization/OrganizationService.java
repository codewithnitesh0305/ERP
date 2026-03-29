package com.springboot.Service.Organization;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface OrganizationService {

    public ResponseEntity<?> saveUpdateOrganization(MultipartFile multipartFile, Map<String, Object> param, HttpServletRequest request);
    public Map<String,Object> getOrganizationDetails(Map<String,Object> param,HttpServletRequest request);
}
