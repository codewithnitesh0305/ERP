package com.springboot.Service;

import com.springboot.Model.ClaimRequest;

import java.util.List;

public interface ClaimRequestService {
    public ClaimRequest createClaim(ClaimRequest claimRequest);
    public ClaimRequest getClaimById(int id);
    public List<ClaimRequest> getAllCaimRequest();
    public List<ClaimRequest> getAllClaimRequestOfUser();
    public ClaimRequest updateClaim(ClaimRequest claimRequest);
    public void deleteClaimRequest(int id);
    public List<ClaimRequest> searchClaims(String claimNo);
    public int updateClaimRequest(int id, boolean status);
}
