package com.springboot.Repository.CustomRepo;

import com.springboot.Utility.Utilities;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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


}
