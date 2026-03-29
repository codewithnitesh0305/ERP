package com.springboot.Repository.Organization;

import com.springboot.Model.Organizations.Caste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CasteRepository extends JpaRepository<Caste,Long> {

    Caste findByName(String name);

    @Query(nativeQuery = true,value = "Select ct.id,ct.name,ct.is_active from organization_caste ct where (:fts IS NULL OR ct.name LIKE :fts)")
    List<Map<String,Object>> getAllCaste(String fts);
}
