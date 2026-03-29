package com.springboot.Repository.Organization;

import com.springboot.Model.Organizations.BloodGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BloodGroupRepository extends JpaRepository<BloodGroup,Long> {

    BloodGroup findByName(String name);

    @Query(nativeQuery = true,value = "Select bg.id,bg.name,bg.is_active from organization_blood_group bg where (:fts IS NULL OR bg.name LIKE :fts)")
    List<Map<String,Object>> getAllBloodGroup(@Param("fts") String fts);
}
