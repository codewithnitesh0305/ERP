package com.springboot.Repository;

import com.springboot.Model.ClaimRequest;
import com.springboot.Model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRequestRepository extends JpaRepository<ClaimRequest , Integer> {
    public ClaimRequest findById(int id);
    public List<ClaimRequest> findByEmployeeId(int id);
    List<ClaimRequest> findByClaimNo(String claimNo);
    @Modifying
    @Query(value = "UPDATE claim_request_mast SET claim_status = :status, claim_update_by= :update_by WHERE id = :claimId", nativeQuery = true)
    int updateClaimStatus(@Param("claimId") int id,@Param("update_by") String role,  @Param("status") String status);
}
