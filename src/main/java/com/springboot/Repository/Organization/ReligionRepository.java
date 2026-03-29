package com.springboot.Repository.Organization;

import com.springboot.Model.Organizations.Religion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReligionRepository extends JpaRepository<Religion,Long> {

    Religion findByName(String name);

    @Query(nativeQuery = true,value = "Select reg.id,reg.name,reg.is_active from organization_religion reg where (:fts IS NULL OR reg.name LIKE :fts)")
    List<Map<String,Object>> getAllReligion(@Param("fts") String fts);
}
