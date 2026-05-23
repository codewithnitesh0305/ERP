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

    @Query(nativeQuery = true, value = """
    SELECT * FROM employee_auto_number_scheme a 
    WHERE (a.financial_year_id IS NULL OR :financialYearId IS NULL OR a.financial_year_id = :financialYearId )
        AND (a.department_id IS NULL OR :departmentId IS NULL OR a.department_id = :departmentId)
        AND a.deleted_on IS NULL 
        AND a.deleted_by IS NULL
    """)
    EmployeeAutoNumber getEmployeeAutoNoSchemeByFinancialYearIdAndDepartmentId(@Param("financialYearId") Long financialYearId, @Param("departmentId") Long departmentId);

    List<EmployeeAutoNumber> findByDeletedOnIsNullOrderByIdDesc();

    @Query(nativeQuery = true, value = "SELECT * FROM employee_auto_number_scheme e WHERE (e.financialYear.id = :financialYearId OR e.financialYear IS NULL) AND (e.department.id = :departmentId OR e.department IS NULL)")
    EmployeeAutoNumber findEmployeeAutoNo(@Param("financialYearId") Long financialYearId, @Param("departmentId") Long departmentId);


}
