package com.springboot.Repository.Organization;

import com.springboot.Model.Organizations.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
   Department findByName(String name);

    @Query(nativeQuery = true, value = "SELECT dept.id AS departmentId, dept.name AS departmentName, dept.is_active AS isActive FROM organization_department dept WHERE (:fts IS NULL OR :fts = '' OR dept.name LIKE CONCAT('%', :fts, '%'))")
    List<Map<String,Object>> getAllDepartment(@Param("fts") String fts);
}
