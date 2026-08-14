package com.springboot.Repository.EmployeeInformation.Employees;

import com.springboot.Model.EmployeeInformation.Employee.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees,Long> {
    Optional<Employees> findByEmailId(String email);

    boolean existsByContactNoAndIdNot(String contactNo, Long id);
    boolean existsByEmailIdAndIdNot(String emailId, Long id);

    @Query(nativeQuery = true, value = """
    SELECT
        emp.id AS employeeId,
        emp.branch_id AS branchId,
        emp.full_name AS employeeName,
        emp.employee_profile_image AS employeeProfileImage,
        emp.email_id AS emailId,
        emp.user_type_id AS userTypeId,
        emp.department_id AS departmentId,
        dep.name AS departmentName,
        emp.designation_id AS designationId,
        deg.name AS designationName,
        branch.organization_id AS organizationId,
        branch.organization_name AS branchName,
        branch.branch_logo AS branchLogo
    FROM employees emp
    LEFT JOIN organization_department dep ON dep.id = emp.department_id
    LEFT JOIN organization_designation deg ON deg.id = emp.designation_id
    INNER JOIN organization_branch branch ON branch.id = emp.branch_id
    WHERE emp.email_id = :emailId
    """)
    Map<String, Object> getEmployeeDetails(@Param("emailId") String emailId);
}
