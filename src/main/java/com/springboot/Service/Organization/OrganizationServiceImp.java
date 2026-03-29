package com.springboot.Service.Organization;

import com.springboot.Model.Organizations.Organization;
import com.springboot.Repository.CustomRepo.CustomRepo;
import com.springboot.Repository.Organization.OrganizationRepository;
import com.springboot.Service.Cloudinary.FileManager;
import com.springboot.Utility.ApiResponse;
import com.springboot.Utility.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class OrganizationServiceImp implements OrganizationService{

    private OrganizationRepository organizationRepository;
    private FileManager cloudinaryService;
    private CustomRepo customRepo;

    @Override
    @Transactional
    public ResponseEntity<?> saveUpdateOrganization(MultipartFile file, Map<String, Object> param, HttpServletRequest request) {
        try{
            Long organizationId = Utilities.longValue(param.get("id"));
            String organizationName = Utilities.stringValue(param.get("organizationName"));
            String organizationCode = Utilities.stringValue(param.get("organizationCode"));
            String affiliatedNo = Utilities.stringValue(param.get("affiliatedNo"));
            Long contactCodeId = Utilities.longValue(param.get("contactCodeId"));
            String contactNo = Utilities.stringValue(param.get("contactNo"));
            String emailId = Utilities.stringValue(param.get("emailId"));
            Long currencyId = Utilities.longValue(param.get("currencyId"));
            Boolean isLogoUpdate = Utilities.booleanValue(param.get("isLogoUpdate"));
            String websiteUrl = Utilities.stringValue(param.get("websiteUrl"));

            String address = Utilities.stringValue(param.get("address"));
            Long countryId = Utilities.longValue(param.get("countryId"));
            Long stateId = Utilities.longValue(param.get("stateId"));
            Long cityId = Utilities.longValue(param.get("cityId"));
            String pinNo = Utilities.stringValue(param.get("pinNo"));

            String facebookUrl = Utilities.stringValue(param.get("facebookUrl"));
            String instagramUrl = Utilities.stringValue(param.get("instagramUrl"));
            String youtubeUrl = Utilities.stringValue(param.get("youtubeUrl"));
            String linkedinUrl = Utilities.stringValue(param.get("linkedinUrl"));
            String xUrl = Utilities.stringValue(param.get("xUrl"));

            if(file == null || file.isEmpty()){
                return ApiResponse.apiValidation("Organization logo is required.");
            }
            if(organizationCode.isEmpty()){
                return ApiResponse.apiValidation("Organization name is required.");
            }
            if(organizationName.isEmpty()){
                return ApiResponse.apiValidation("Organization code is required.");
            }
            if(contactCodeId == null){
                return ApiResponse.apiValidation("Contact Code is required.");
            }
            if(contactNo.isEmpty()){
                return ApiResponse.apiValidation("Contact No. is required.");
            }
            if(contactNo.length() > 10){
                return ApiResponse.apiValidation("Contact No. should be 10 digits.");
            }
            if(currencyId == null){
                return ApiResponse.apiValidation("Currency should be selected");
            }
            if(address.isEmpty()){
                return ApiResponse.apiValidation("Address is required.");
            }
            if(countryId == null){
                return ApiResponse.apiValidation("Country should be selected.");
            }
            if(stateId == null){
                return ApiResponse.apiValidation("State should be selected.");
            }
            if(cityId == null){
                return ApiResponse.apiValidation("City should be selected.");
            }

            Organization existingOrganization = organizationRepository.findByNameAndOrganizationCodeAndAffiliatedNo(organizationName,organizationCode,affiliatedNo);
            if((existingOrganization.getName().equals(organizationName) && existingOrganization.getOrganizationCode().equals(organizationCode) && existingOrganization.getAffiliatedNo().equals(affiliatedNo))  && !(existingOrganization.getId().equals(organizationId))){
                return ApiResponse.apiValidation("Organization already exist");
            }


            Organization organization = organizationId != null ? organizationRepository.findById(organizationId).orElseThrow(() -> new RuntimeException("Organization not found.")) : new Organization();
            JSONObject jsonObject = new JSONObject();
            if(isLogoUpdate){
                String  organizationLogo = Utilities.stringValue(cloudinaryService.uploadFileInCloud(file).get("imageUrl"));
                 jsonObject.put("serving_url",organizationLogo);
            }
            organization.setOrganizationLogo(jsonObject.toString());
            organization.setName(organizationName);
            organization.setOrganizationCode(organizationCode);
            organization.setAffiliatedNo(affiliatedNo);
            organization.setMobileCodeId(contactCodeId);
            organization.setContactNo(contactNo);
            organization.setOrganizationEmailId(emailId);
            organization.setCurrencyId(currencyId);
            organization.setWebsiteUrl(websiteUrl);

            organization.setAddress(address);
            organization.setCountryId(countryId);
            organization.setState(stateId);
            organization.setCityId(cityId);
            organization.setPinCode(pinNo);

            organization.setFacebookUrl(facebookUrl);
            organization.setInstagramUrl(instagramUrl);
            organization.setYoutubeUrl(youtubeUrl);
            organization.setLinkedinUtl(linkedinUrl);
            organization.setXUrl(xUrl);

            if(organizationId == null){
                organization.setIsActive(true);
                organization.setCreatedBy(null);
                organization.setCreateOn(LocalDateTime.now());
            }else{
                organization.setUpdateBy(null);
                organization.setUpdatedOn(LocalDateTime.now());
            }
            organizationRepository.save(organization);
        }catch (Exception e){
            return ApiResponse.apiFailure(e);
        }
        return ApiResponse.apiSuccess();
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
