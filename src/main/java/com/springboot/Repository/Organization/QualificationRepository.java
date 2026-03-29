package com.springboot.Repository.Organization;

import com.springboot.Model.Organizations.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface QualificationRepository extends JpaRepository<Qualification,Long> {

    Qualification findByName(String name);

    @Query(nativeQuery = true,value = "Select qul.id,qul.name,qul.is_active from organization_qualification qul where (:fts IS NULL OR qul.name LIKE :fts)")
    List<Map<String,Object>> getAllQualification(@Param("fts") String fts);
}
