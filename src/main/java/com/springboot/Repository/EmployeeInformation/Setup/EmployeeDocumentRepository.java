package com.springboot.Repository.EmployeeInformation.Setup;

import com.springboot.Model.EmployeeInformation.Setup.EmployeeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EmployeeDocumentRepository extends JpaRepository<EmployeeDocument,Long> {

    EmployeeDocument findByName(String name);

    @Query(nativeQuery = true, value = "Select * from employee_document empDoc where (:fts IS NULL OR empDoc.name LIKE :fts)")
    List<Map<String,Object>> getAllDocument(@Param("fts") String fts);
}
