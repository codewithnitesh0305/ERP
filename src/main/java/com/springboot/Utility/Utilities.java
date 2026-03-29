package com.springboot.Utility;

import com.springboot.Config.CustomeEmployee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Utilities {

    public static String stringValue(Object value) {
        return value == null ? "" : String.valueOf(value);
    }
    public static Long longValue(Object value) {
        if (value == null) return null;
        if (value instanceof Long) return (Long) value;
        if (value instanceof Number) return ((Number) value).longValue();
        try {
            return Long.parseLong(value.toString().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Integer integerValue(Object value) {
        if (value == null) return null;
        if (value instanceof Integer) return (Integer) value;
        if (value instanceof Number) return ((Number) value).intValue();
        try {
            return Integer.parseInt(value.toString().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Boolean booleanValue(Object value) {
        if (value == null) {
            return false;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        String strValue = value.toString().trim().toLowerCase();
        return strValue.equals("true") || strValue.equals("1") || strValue.equals("yes");
    }

    public static String filterValue(String filter) {
        if (filter == null || filter.trim().isEmpty()) {
            return "";
        }
        filter = filter.replaceAll("(?i)\\bwhere\\b", "").trim();
        return " WHERE " + filter;
    }

    public static String groupByValue(String groupBy) {
        if (groupBy == null || groupBy.trim().isEmpty()) {
            return "";
        }
        groupBy = groupBy.replaceAll("(?i)\\bgroup\\s+by\\b", "").trim();
        return " GROUP BY " + groupBy;
    }

    public static String orderByValue(String orderBy) {
        if (orderBy == null || orderBy.trim().isEmpty()) {
            return "";
        }
        orderBy = orderBy.replaceAll("(?i)\\bgroup\\s+by\\b", "").trim();
        return " ORDER BY " + orderBy;
    }
    public static Set<Long> getSetOfCommaValue(String value){
        Set<Long> dataSet = new LinkedHashSet<>();
        if(!value.isEmpty()){
            String[] ids = value.split(",");
            for(String id : ids){
                dataSet.add(Utilities.longValue(id));
            }
        }
        return  dataSet;
    }

    public static String getFormattedStartNumber(Integer number, Integer size) {
        if (number == null || size == null) return null;
        return String.format("%0" + size + "d", number);
    }

    public static String getCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    public static String getUSDateFromIndianDate(String date){
        try{
            if(date != null && !date.isEmpty()){
                DateTimeFormatter indianDateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                DateTimeFormatter usDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(date,indianDateFormat);
                return localDate.format(usDateFormat);
            }
        }catch (Exception ex){
            return "Invalid Date";
        }
        return  "";
    }

    public static LinkedList<Map<String, Object>> getToupleRecordsWithObjects(EntityManager entityManager, String query, Map<Integer, Object> param) {
        Query jpaQuery = entityManager.createNativeQuery(query, Tuple.class);
        if (param != null) {
            for (Map.Entry<Integer, Object> parameter : param.entrySet()) {
                jpaQuery.setParameter(parameter.getKey(), parameter.getValue());
            }
        }
        List<Tuple> result = jpaQuery.getResultList();
        LinkedList<Map<String, Object>> returnList = new LinkedList<>();
        for (Tuple row : result) {
            Map<String, Object> map = new HashMap<>();
            List<TupleElement<?>> elements = row.getElements();
            for (TupleElement<?> element : elements) {
                map.put(element.getAlias(), row.get(element.getAlias()));
            }
            returnList.add(map);
        }
        return returnList;
    }

}
