package com.springboot.Service.Organization.Organization;

import com.springboot.Dto.Organization.OrganizationDTO.OrganizationRequestDto;
import com.springboot.Dto.Organization.OrganizationDTO.OrganizationResponseDto;
import com.springboot.Exception.ResourceNotFoundException;
import com.springboot.Model.Organizations.Organization;
import com.springboot.Model.Organizations.OrganizationBranch;
import com.springboot.Repository.Organization.OrganizationBranchRepository;
import com.springboot.Repository.Organization.OrganizationRepository;
import com.springboot.Service.Cloudinary.FileManager;
import com.springboot.Utility.ApiResponse;
import com.springboot.Utility.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImp implements OrganizationService{

    private final OrganizationRepository organizationRepository;
    private final OrganizationBranchRepository organizationBranchRepository;

    @Transactional
    @Override
    public ResponseEntity<?> saveUpdateOrganization(MultipartFile file, OrganizationRequestDto organizationRequestDto, HttpServletRequest request) throws IOException {
        Long id = organizationRequestDto.getId();
        Boolean isFileChange = organizationRequestDto.getIsChange();
        Boolean isMoreBranch = organizationRequestDto.getIsMoreBranch();
        String currentDateTime = Utilities.getCurrentDateTime();
        Organization organization = id == null ? new Organization() : organizationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Organization not found."));
        String organizationLogo = null;
        if(isFileChange){
            organizationLogo = FileManager.uploadFile(file);
            String existingLogo = organization.getOrganizationLogo();
            if(existingLogo != null && !existingLogo.isEmpty()){
                FileManager.deleteFile(existingLogo);
            }
            if(organizationLogo != null && !organizationLogo.isEmpty()){
                organization.setOrganizationLogo(organizationLogo);
            }else{
                organization.setOrganizationLogo(null);
            }
        }
        organization.setName(organizationRequestDto.getName());
        organization.setCode(organizationRequestDto.getCode());
        organization.setAffiliationNo(organizationRequestDto.getAffiliationNo());
        organization.setContactCode(organizationRequestDto.getContactCode());
        organization.setContactNo(organizationRequestDto.getContactNo());
        organization.setEmailId(organizationRequestDto.getEmailId());
        organization.setCurrencyId(organizationRequestDto.getCurrencyId());
        organization.setWebSiteUrl(organizationRequestDto.getWebSiteUrl());

        if(isMoreBranch){
            OrganizationBranch organizationBranch = new OrganizationBranch();
            organizationBranch.setName(organizationRequestDto.getName());
            organizationBranch.setOrganizationCode(organizationRequestDto.getCode());
            organizationBranch.setAffiliatedNo(organizationRequestDto.getAffiliationNo());
            organizationBranch.setContactNo(organizationRequestDto.getContactCode());
            organizationBranch.setContactNo(organizationRequestDto.getContactNo());
            organizationBranch.setEmailId(organizationRequestDto.getEmailId());
            organizationBranch.setCurrencyId(organizationRequestDto.getCurrencyId());
            organizationBranch.setWebsiteUrl(organizationRequestDto.getWebSiteUrl());

            organizationBranch.setAddress(organizationRequestDto.getAddress());
            organizationBranch.setCountryId(organizationRequestDto.getCountryId());
            organizationBranch.setStateId(organizationRequestDto.getStateId());
            organizationBranch.setCityId(organizationRequestDto.getCityId());
            organizationBranch.setPinCode(organizationRequestDto.getPinCode());

            organizationBranchRepository.save(organizationBranch);
        }

        organization.setAddress(organizationRequestDto.getAddress());
        organization.setCountryId(organizationRequestDto.getCountryId());
        organization.setStateId(organizationRequestDto.getStateId());
        organization.setCityId(organizationRequestDto.getCityId());
        organization.setPinCode(organizationRequestDto.getPinCode());

        organization.setFaceBookUrl(organizationRequestDto.getFaceBookUrl());
        organization.setLinkedInUrl(organizationRequestDto.getLinkedInUrl());
        organization.setYouTubeUrl(organizationRequestDto.getYouTubeUrl());
        organization.setInstagramUrl(organizationRequestDto.getInstagramUrl());
        organization.setXUrl(organizationRequestDto.getXUrl());

        if(id == null){
            organization.setCreatedBy(null);
            organization.setCreatedOn(currentDateTime);
        }else{
            organization.setUpdatedBy(null);
            organization.setUpdatedOn(currentDateTime);
        }

        organizationRepository.save(organization);
        return ApiResponse.apiSuccess();
    }

    @Override
    public OrganizationResponseDto getOrganizationDetails(Long organizationId, HttpServletRequest request) {
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new ResourceNotFoundException("Organization not found"));
        OrganizationResponseDto organizationResponseDto = new OrganizationResponseDto();
        organizationResponseDto.setName(organization.getName());
        organizationResponseDto.setCode(organization.getCode());
        organizationResponseDto.setAffiliationNo(organization.getAffiliationNo());
        organizationResponseDto.setContactCode(organization.getContactCode());
        organizationResponseDto.setContactNo(organization.getContactNo());
        organizationResponseDto.setEmailId(organization.getEmailId());
        organizationResponseDto.setCurrencyId(organization.getCurrencyId());
        organizationResponseDto.setWebSiteUrl(organization.getWebSiteUrl());
        organizationResponseDto.setIsMoreBranch(organization.getIsMoreBranch());

        organizationResponseDto.setAddress(organization.getAddress());
        organizationResponseDto.setCountryId(organization.getCountryId());
        organizationResponseDto.setStateId(organization.getStateId());
        organizationResponseDto.setCityId(organization.getCityId());
        organizationResponseDto.setPinCode(organization.getPinCode());

        organizationResponseDto.setFaceBookUrl(organization.getFaceBookUrl());
        organizationResponseDto.setLinkedInUrl(organization.getLinkedInUrl());
        organizationResponseDto.setYouTubeUrl(organization.getYouTubeUrl());
        organizationResponseDto.setInstagramUrl(organization.getInstagramUrl());
        organizationResponseDto.setXUrl(organization.getXUrl());
        return organizationResponseDto;
    }
}
