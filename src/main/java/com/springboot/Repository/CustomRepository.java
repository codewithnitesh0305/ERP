package com.springboot.Repository;

import com.springboot.Utility.Utilities;
import jakarta.persistence.EntityManager;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;
import java.util.Map;

public class CustomRepository {

    private EntityManager entityManager;

    public List<Map<String,Object>> customQuery(String query,String filter,String groupBy,String orderBy,Integer limit,Map<Integer,Object> param){
        filter = Utilities.filterValue(filter);
        groupBy = Utilities.groupByValue(groupBy);
        orderBy = Utilities.orderByValue(orderBy);

        query = query + filter + groupBy + orderBy;
        List<Map<String,Object>> result_list = Utilities.getToupleRecordsWithObjects(entityManager,query,param);
        return  result_list;
    }
}
