package com.springboot.Repository.CustomRepo;

import com.springboot.Utility.Utilities;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class CustomRepo {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Map<String,Object>> getAllCountryMobileCode(String filter){
        filter = Utilities.filterValue(filter);
        String query = "Select phone_code,max_length FROM country_mobile" + filter;
        return Utilities.getToupleRecordsWithObjects(entityManager,query,null);
    }

    public List<Map<String,Object>> getAllCountry(){
        String query = "Select id as value, name as label from country";
        return Utilities.getToupleRecordsWithObjects(entityManager,query,null);
    }

    public List<Map<String,Object>> getAllStates(){
        String query = "Select id as value, name as label from state";
        return Utilities.getToupleRecordsWithObjects(entityManager,query,null);
    }

    public List<Map<String,Object>> getAllCity(){
        String query = "Select id as value, name as label from city";
        return Utilities.getToupleRecordsWithObjects(entityManager,query,null);
    }

    public List<Map<String,Object>> getAllCountryMobileCode(){
        String query = "Select id as value, phone_code as label from country_mobile";
        return Utilities.getToupleRecordsWithObjects(entityManager,query,null);
    }

    public List<Map<String,Object>> getAllFinancialYear(String filter,String orderBy){
        filter = Utilities.filterValue(filter);
        orderBy = Utilities.orderByValue(orderBy);
        String query = "Select id as value ,financial_year_name as label from financial_year" + filter + orderBy;
        return Utilities.getToupleRecordsWithObjects(entityManager,query,null);
    }

    public Map<String,Object> getAllDepartment(){
        Map<String,Object> result_map = new LinkedHashMap<>();
        String query = "Select dep.id as value, dep.name as label from organization_department dep";
        List<Map<String,Object>> departmentList = Utilities.getToupleRecordsWithObjects(entityManager,query,null);
        Map<Long,String> departmentMap = departmentList.stream().collect(Collectors.toMap(dep -> Utilities.longValue(dep.get("value")),dep -> Utilities.stringValue(dep.get("label"))));
        result_map.put("departmentList",departmentList);
        result_map.put("departmentMap",departmentMap);
        return result_map;
    }

    public Map<String,Object> getAllDesignation(){
        Map<String,Object> result_map = new LinkedHashMap<>();
        String query = "Select deg.id as value, deg.name as label from organization_designation deg";
        List<Map<String,Object>> designationList = Utilities.getToupleRecordsWithObjects(entityManager,query,null);
        Map<Long,String> designationMap = designationList.stream().collect(Collectors.toMap(dep -> Utilities.longValue(dep.get("value")),dep -> Utilities.stringValue(dep.get("label"))));
        result_map.put("designationList",designationList);
        result_map.put("designationMap",designationMap);
        return result_map;
    }

    public Map<String,Object> getAllEmployeeType(){
        Map<String,Object> result_map = new LinkedHashMap<>();
        String query = "Select empTyp.id as value, empTyp.name as label from employee_type empTyp";
        List<Map<String,Object>> employeeTypeList = Utilities.getToupleRecordsWithObjects(entityManager,query,null);
        Map<Long,String> employeeTypeMap = employeeTypeList.stream().collect(Collectors.toMap(dep -> Utilities.longValue(dep.get("value")),dep -> Utilities.stringValue(dep.get("label"))));
        result_map.put("employeeTypeList",employeeTypeList);
        result_map.put("employeeTypeMap",employeeTypeMap);
        return result_map;
    }

    public List<Map<String,Object>> customizeDataList(String query, String filter,String groupBy,String orderBy){
        filter = Utilities.filterValue(filter);
        groupBy = Utilities.groupByValue(groupBy);
        orderBy = Utilities.orderByValue(orderBy);
        query += query + filter + groupBy + orderBy;
        return Utilities.getToupleRecordsWithObjects(entityManager,query,null);
    }

}
