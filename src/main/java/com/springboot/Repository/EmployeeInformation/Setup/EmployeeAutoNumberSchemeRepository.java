package com.springboot.Repository.EmployeeInformation.Setup;

import com.springboot.Model.EmployeeInformation.Setup.EmployeeAutoNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EmployeeAutoNumberSchemeRepository extends JpaRepository<EmployeeAutoNumber,Long> {

    EmployeeAutoNumber findByFinancialYearIdAndDeletedOnIsNull(Long financialYearId);

    @Query(nativeQuery = true,value = "SELECT * FROM employee_auto_number_scheme a WHERE ( :financialYearId IS NULL OR a.financial_year_id = :financialYearId OR a.financial_year_id IS NULL ) AND a.is_active = 1 AND a.deleted_on IS NULL LIMIT 1")
    EmployeeAutoNumber getEmployeeAutoNoSchemeByFinancialYearId(@Param("financialYearId") Long financialYearId);

    List<EmployeeAutoNumber> findByDeletedOnIsNullOrderByIdDesc();
}
