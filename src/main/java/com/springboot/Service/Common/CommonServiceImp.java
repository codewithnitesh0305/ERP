package com.springboot.Service.Common;

import com.springboot.Exception.ResourceNotFoundException;
import com.springboot.Model.Organizations.OrganizationBranch;
import com.springboot.Repository.CustomRepo.CustomRepo;
import com.springboot.Repository.Organization.FinancialYearRepository;
import com.springboot.Repository.Organization.OrganizationBranchRepository;
import com.springboot.Utility.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommonServiceImp implements CommonService{

    private final CustomRepo customRepo;
    private final FinancialYearRepository financialYearRepository;
    private final OrganizationBranchRepository organizationBranchRepository;

    @Override
    public Map<String, Object> getFinancialYear(Map<String, Object> param, HttpServletRequest request) {
        Map<String,Object> resultMap = new LinkedHashMap<>();
        List<Map<String, Object>> financialYearList = financialYearRepository.getFinancialYearList();
        Long activeFinancialYear = financialYearRepository.findIdByIsActiveTrue();
        resultMap.put("financialYearList",financialYearList);
        resultMap.put("activeFinancialYear",activeFinancialYear);
        return resultMap;
    }

    @Override
    public Map<String, Object> getCountry(Map<String, Object> param, HttpServletRequest request) {
        Map<String,Object> resultMap = new LinkedHashMap<>();
        Long organizationBranchId = 1L;
        List<Map<String, Object>> countryDetailList = customRepo.countryDetailList(null);
        List<Map<String, Object>> allStates = customRepo.getAllStates();
        List<Map<String, Object>> allCity = customRepo.getAllCity();
        OrganizationBranch organizationBranch = organizationBranchRepository.findById(organizationBranchId).orElseThrow(() -> new ResourceNotFoundException("Organization not found."));
        String defaultCountryCode = Utilities.stringValue(organizationBranch.getContactCountryCode());
        String defaultCountryId = Utilities.stringValue(organizationBranch.getCountryId());

        resultMap.put("countryDetailList",countryDetailList);
        resultMap.put("allStates",allStates);
        resultMap.put("allCities",allCity);
        resultMap.put("defaultNationalityId",defaultCountryId);
        resultMap.put("defaultCountryCode",defaultCountryCode);
        return resultMap;
    }
}
