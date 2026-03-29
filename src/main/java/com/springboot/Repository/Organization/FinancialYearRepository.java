package com.springboot.Repository.Organization;

import com.springboot.Model.Organizations.FinancialYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FinancialYearRepository extends JpaRepository<FinancialYear,Long> {

    FinancialYear findByFinancialYearName(String name);

    @Query(nativeQuery = true,value = "Select id,financial_year_name,from_date,to_date from financial_year where (:fts IS NULL OR :fts = '' OR financial_year_name LIKE CONCAT('%', :fts, '%'))")
    List<Map<String,Object>> getFinancialYear(@Param("fts") String fts);

}
