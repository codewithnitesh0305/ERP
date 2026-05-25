package com.springboot.Service.Organization.OrganizationBranch;

import com.springboot.Dto.Organization.OrganizationBranchDTO.OrganizationBranchRequestDto;
import com.springboot.Model.Organizations.OrganizationBranch;
import com.springboot.Repository.CustomRepo.CustomRepo;
import com.springboot.Repository.Organization.OrganizationBranchRepository;
import com.springboot.Service.Cloudinary.FileManager;
import com.springboot.Utility.ApiResponse;
import com.springboot.Utility.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class OrganizationBranchServiceImp implements OrganizationBranchService {

    private OrganizationBranchRepository organizationRepository;
    private CustomRepo customRepo;

    @Override
    @Transactional
    public ResponseEntity<?> saveUpdateOrganizationBranch(MultipartFile file, OrganizationBranchRequestDto dto, HttpServletRequest request) {
        try {

            Long organizationId = dto.getId();
            OrganizationBranch existingOrganization = organizationRepository.findByNameAndOrganizationCodeAndAffiliatedNo(dto.getName(), dto.getOrganizationCode(), dto.getAffiliatedNo());
            if (existingOrganization != null && !existingOrganization.getId().equals(organizationId)) return ApiResponse.apiValidation("Organization already exists.");

            OrganizationBranch organization = organizationId != null ? organizationRepository.findById(organizationId).orElseThrow(() -> new RuntimeException("Organization not found.")) : new OrganizationBranch();
            if (Boolean.TRUE.equals(dto.getIsLogoChange())) {
                String existingLogo = Utilities.stringValue(organization.getOrganizationLogo());
                if (!existingLogo.isEmpty()) {
                    FileManager.deleteFile(existingLogo);
                }
                String organizationLogo = FileManager.uploadFile(file);
                if (organizationLogo != null && !organizationLogo.isEmpty()) {
                    organization.setOrganizationLogo(organizationLogo);
                } else {
                    organization.setOrganizationLogo(null);
                }
            }

            organization.setName(dto.getName());
            organization.setOrganizationCode(dto.getOrganizationCode());
            organization.setAffiliatedNo(dto.getAffiliatedNo());
            organization.setContactCountryCode(dto.getContactCountryCode());
            organization.setContactNo(dto.getContactNo());
            organization.setEmailId(dto.getEmailId());
            organization.setCurrencyId(dto.getCurrencyId());

            organization.setAddress(dto.getAddress());
            organization.setCountryId(dto.getCountryId());
            organization.setStateId(dto.getStateId());
            organization.setCityId(dto.getCityId());
            organization.setPinCode(dto.getPinCode());

            if (organizationId == null) {
                organization.setIsActive(true);
                organization.setCreatedBy(null);
                organization.setCreateOn(Utilities.getCurrentDateTime());
            } else {
                organization.setUpdateBy(null);
                organization.setUpdatedOn(Utilities.getCurrentDateTime());
            }
            organizationRepository.save(organization);
            return ApiResponse.apiSuccess();
        } catch (Exception e) {
            return ApiResponse.apiFailure(e);
        }
    }

    @Override
    public Map<String, Object> getOrganizationDetails(Map<String, Object> param, HttpServletRequest request) {
        Map<String,Object> result_map = new LinkedHashMap<>();
        List<Map<String, Object>> cityList = customRepo.getAllCity();
        List<Map<String, Object>> statesList = customRepo.getAllStates();
        List<Map<String, Object>> countryList = customRepo.getAllCountry();
        List<Map<String, Object>> allCountryMobileCodeList = customRepo.getAllCountryMobileCode();
        Long organizationId = Utilities.longValue(param.get("id")) ;
        if(organizationId != null){
            List<Map<String,Object>> organizationDetailList = organizationRepository.getOrganizationDetails(organizationId);
            result_map.put("organizationList",organizationDetailList);
        }
        result_map.put("cityList",cityList);
        result_map.put("statesList",statesList);
        result_map.put("countryList",countryList);
        result_map.put("allCountryMobileCodeList",allCountryMobileCodeList);
        return result_map;
    }
}
