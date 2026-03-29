package com.springboot.Repository.Organization;

import com.springboot.Model.Organizations.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProfessionRepository extends JpaRepository<Profession,Long> {

    Profession findByName(String name);

    @Query(nativeQuery = true,value = "Select prof.id,prof.name,prof.is_active from organization_profession prof where (:fts IS NULL OR prof.name LIKE :fts)")
    List<Map<String,Object>> getAllProfession(@Param("fts") String fts);
}
