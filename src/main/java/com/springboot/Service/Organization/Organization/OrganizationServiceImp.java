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
    public ResponseEntity<?> saveUpdateOrganization(OrganizationRequestDto organizationRequestDto, HttpServletRequest request) throws IOException {
        Long id = organizationRequestDto.getId();
        Boolean isFileChange = organizationRequestDto.getIsChange();
        Boolean isMoreBranch = organizationRequestDto.getIsMoreBranch();
        String currentDateTime = Utilities.getCurrentDateTime();
        Organization organization = id == null ? new Organization() : organizationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Organization not found."));
        String organizationLogo = FileManager.uploadFile(organizationRequestDto.getFile());
        if(isFileChange){
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

        if(!isMoreBranch){
            OrganizationBranch organizationBranch = id == null ? new OrganizationBranch() : organizationBranchRepository.findByOrganizationIdAndIsActiveTrue(id).orElseThrow(() -> new ResourceNotFoundException("Organization Branch not found."));
            organizationBranch.setName(organizationRequestDto.getName());
            organizationBranch.setOrganizationCode(organizationRequestDto.getCode());
            organizationBranch.setAffiliatedNo(organizationRequestDto.getAffiliationNo());
            organizationBranch.setContactCountryCode(organizationRequestDto.getContactCode());
            organizationBranch.setContactNo(organizationRequestDto.getContactNo());
            organizationBranch.setEmailId(organizationRequestDto.getEmailId());
            organizationBranch.setCurrencyId(organizationRequestDto.getCurrencyId());
            organizationBranch.setWebsiteUrl(organizationRequestDto.getWebSiteUrl());
            organizationBranch.setIsActive(true);

            organizationBranch.setAddress(organizationRequestDto.getAddress());
            organizationBranch.setCountryId(organizationRequestDto.getCountryId());
            organizationBranch.setStateId(organizationRequestDto.getStateId());
            organizationBranch.setCityId(organizationRequestDto.getCityId());
            organizationBranch.setPinCode(organizationRequestDto.getPinCode());
            if(id == null){
                organizationBranch.setCreatedBy(null);
                organizationBranch.setCreateOn(currentDateTime);
            }else{
                organizationBranch.setUpdateBy(null);
                organizationBranch.setUpdatedOn(currentDateTime);
            }
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
        organizationResponseDto.setId(Utilities.longValue(organization.getId()));
        organizationResponseDto.setOrganizationImage(Utilities.getServingUrlFromImageString(organization.getOrganizationLogo()));
        organizationResponseDto.setName(Utilities.stringValue(organization.getName()));
        organizationResponseDto.setCode(Utilities.stringValue(organization.getCode()));
        organizationResponseDto.setAffiliationNo(Utilities.stringValue(organization.getAffiliationNo()));
        organizationResponseDto.setContactCode(Utilities.stringValue(organization.getContactCode()));
        organizationResponseDto.setContactNo(Utilities.stringValue(organization.getContactNo()));
        organizationResponseDto.setEmailId(Utilities.stringValue(organization.getEmailId()));
        organizationResponseDto.setCurrencyId(Utilities.longValue(organization.getCurrencyId()));
        organizationResponseDto.setWebSiteUrl(Utilities.stringValue(organization.getWebSiteUrl()));
        organizationResponseDto.setIsMoreBranch(Utilities.booleanValue(organization.getIsMoreBranch()));

        organizationResponseDto.setAddress(Utilities.stringValue(organization.getAddress()));
        organizationResponseDto.setCountryId(Utilities.longValue(organization.getCountryId()));
        organizationResponseDto.setStateId(Utilities.longValue(organization.getStateId()));
        organizationResponseDto.setCityId(Utilities.longValue(organization.getCityId()));
        organizationResponseDto.setPinCode(Utilities.longValue(organization.getPinCode()));

        organizationResponseDto.setFaceBookUrl(Utilities.stringValue(organization.getFaceBookUrl()));
        organizationResponseDto.setLinkedInUrl(Utilities.stringValue(organization.getLinkedInUrl()));
        organizationResponseDto.setYouTubeUrl(Utilities.stringValue(organization.getYouTubeUrl()));
        organizationResponseDto.setInstagramUrl(Utilities.stringValue(organization.getInstagramUrl()));
        organizationResponseDto.setXUrl(Utilities.stringValue(organization.getXUrl()));
        return organizationResponseDto;
    }
}
