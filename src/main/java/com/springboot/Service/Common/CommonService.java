package com.springboot.Service.Common;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public interface CommonService {

    Map<String,Object> getFinancialYear(Map<String,Object> param, HttpServletRequest request);
    Map<String,Object> getCountry(Map<String,Object> param,HttpServletRequest request);
}
