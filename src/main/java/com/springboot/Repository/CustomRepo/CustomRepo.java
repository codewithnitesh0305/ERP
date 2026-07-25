package com.springboot.Repository.CustomRepo;

import com.springboot.Utility.Utilities;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CustomRepo {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Map<String,Object>> countryDetailList(String filter){
        filter = Utilities.filterValue(filter);
        String query = "Select id,country_name as countryName,nationality_name as nationalityName, phone_code as countryCode,max_length as countryCodeMaxLength FROM country_codes" + filter;
        return Utilities.getToupleRecordsWithObjects(entityManager,query,null);
    }

    public List<Map<String,Object>> getAllCountry(){
        String query = "Select id as value, name as label from country";
        return Utilities.getToupleRecordsWithObjects(entityManager,query,null);
    }

    public List<Map<String,Object>> getAllStates(){
        String query = "Select id as value, name as label,country_id as countryId from state";
        return Utilities.getToupleRecordsWithObjects(entityManager,query,null);
    }

    public List<Map<String,Object>> getAllCity(){
        String query = "Select id as value, name as label,state_id as stateId from city";
        return Utilities.getToupleRecordsWithObjects(entityManager,query,null);
    }

    public List<Map<String,Object>> getAllCountryMobileCode(){
        String query = "Select id as value, phone_code as label from country_codes";
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

    public List<Map<String,Object>> getAllDepartmentList(){
        String query = "Select dep.id as value, dep.name as label from organization_department dep";
        return Utilities.getToupleRecordsWithObjects(entityManager,query,null);
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

    public List<Map<String,Object>> getAllDesignationList(){
        String query = "Select deg.id as value, deg.name as label from organization_designation deg";
        return Utilities.getToupleRecordsWithObjects(entityManager,query,null);
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

    public List<Map<String,Object>> getAllEmployeeTypeList(){
        String query = "Select empTyp.id as value, empTyp.name as label from employee_type empTyp";
        return Utilities.getToupleRecordsWithObjects(entityManager,query,null);
    }


    public List<Map<String,Object>> customizeDataList(String query, String filter,String groupBy,String orderBy){
        filter = Utilities.filterValue(filter);
        groupBy = Utilities.groupByValue(groupBy);
        orderBy = Utilities.orderByValue(orderBy);
         query = query + filter + groupBy + orderBy;
        return Utilities.getToupleRecordsWithObjects(entityManager,query,null);
    }

    public Integer getContactNoLengthByContactCode(String contactCode) {
        if (contactCode == null || contactCode.isBlank()) return null;
        String query = "SELECT max_length AS max_length FROM country_codes WHERE phone_code = ?1";
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, contactCode);
        LinkedList<Map<String, Object>> result = Utilities.getToupleRecordsWithObjects(entityManager, query, params);
        if (result.isEmpty()) return null;
        Object value = result.getFirst().get("max_length");
        return value != null ? Utilities.integerValue(value) : null;
    }

}
