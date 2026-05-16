package com.springboot.Repository.Organization;

import com.springboot.Model.Organizations.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GenderRepository extends JpaRepository<Gender,Long> {

    Gender findByName(String name);

    @Query(nativeQuery = true,value = "Select gen.id,gen.name, gen.is_active from organization_gender gen where (:fts IS NULL OR gen.name LIKE :fts)")
    List<Map<String,Object>> getAllGenders(@Param("fts") String fts);

    @Query(nativeQuery = true, value = "SELECT gen.id as value,gen.name as label from organization_gender where gen.is_active = true")
    List<Map<String,Object>> getActiveGenderList();
}
