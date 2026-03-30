package com.springboot.Repository.EmployeeInformation.Setup;

import com.springboot.Model.EmployeeInformation.Setup.EmployeeType;
import com.springboot.Model.Organizations.BloodGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EmployeeTypeRepository extends JpaRepository<EmployeeType,Long> {

    EmployeeType findByName(String name);

    @Query(nativeQuery = true,value = "Select empType.id,empType.name from employee_type empType where (:fts IS NULL OR empType.name LIKE :fts)")
    List<Map<String,Object>> getAllEmployeeType(@Param("fts") String fts);
}
