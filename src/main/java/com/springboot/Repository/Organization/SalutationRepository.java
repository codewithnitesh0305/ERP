package com.springboot.Repository.Organization;

import com.springboot.Model.Organizations.Salutation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SalutationRepository extends JpaRepository<Salutation,Long> {

    Salutation findByName(String name);

    @Query(nativeQuery = true,value = "Select sal.id,sal.name,sal.is_active from organization_salutation sal where (:fts IS NULL OR sal.name LIKE :fts)")
    List<Map<String,Object>> getAllSalutation(@Param("fts") String fts);
}
