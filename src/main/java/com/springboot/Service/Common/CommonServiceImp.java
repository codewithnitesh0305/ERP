package com.springboot.Service.Common;

import com.springboot.Repository.CustomRepo.CustomRepo;
import com.springboot.Repository.Organization.FinancialYearRepository;
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
        List<Map<String, Object>> allCountryCountryDetails = customRepo.countryDetailList(null);
        List<Map<String, Object>> allStates = customRepo.getAllStates();
        List<Map<String, Object>> allCity = customRepo.getAllCity();

        resultMap.put("allCountryCountryDetails",allCountryCountryDetails);
        resultMap.put("allStates",allStates);
        resultMap.put("allCities",allCity);
        resultMap.put("defaultNationalityId","");
        resultMap.put("defaultCountryCode","");
        return resultMap;
    }
}
