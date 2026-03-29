package com.springboot.Repository.Organization;

import com.springboot.Model.Organizations.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DesignationRepository extends JpaRepository<Designation,Long> {

    Designation findByName(String name);

    @Query(nativeQuery = true, value = "Select deg.id,deg.name,deg.is_active from organization_designation deg where (:fts IS NULL OR deg.name LIKE :fts)")
    List<Map<String,Object>> getAllDesignation(@Param("fts") String fts);
}
