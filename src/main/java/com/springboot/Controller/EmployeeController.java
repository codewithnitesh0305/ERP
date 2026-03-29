package com.springboot.Controller;

import com.springboot.Model.ClaimRequest;
import com.springboot.Model.Leave;
import com.springboot.Service.ClaimRequestService;
import com.springboot.Service.LeaveService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*@RestController
@RequestMapping("/employee")*/
public class EmployeeController {

    @Autowired
    public ClaimRequestService claimRequestService;
    
    @Autowired
    public LeaveService leaveService;

    @PostMapping("/CreateClaim")
    public ResponseEntity<?> createClaimRequest(@RequestBody ClaimRequest claimRequest){
        ClaimRequest createClaim = claimRequestService.createClaim(claimRequest);
        return new ResponseEntity<ClaimRequest>(createClaim,HttpStatus.CREATED);
    }

    @GetMapping("/Claim/{id}")
    public ResponseEntity<?> getClaimRequestById(@PathVariable("id") int id){
        ClaimRequest claimRequest = claimRequestService.getClaimById(id);
        return new ResponseEntity<>(claimRequest,HttpStatus.OK);
        
        
    }

    @GetMapping("/Claim")
    public ResponseEntity<?> getAllClaimRequest(){
        List<ClaimRequest> claimRequests = claimRequestService.getAllClaimRequestOfUser();
        return new ResponseEntity<List<ClaimRequest>>(claimRequests,HttpStatus.OK);
    }

    @PutMapping("/Claim/{id}")
    public ResponseEntity<?> updateClaimRequest(@PathVariable("id") int id, @RequestBody ClaimRequest claimRequest){
    	claimRequest.setId(id);
         ClaimRequest updateClaim = claimRequestService.updateClaim(claimRequest);
         return new ResponseEntity<ClaimRequest>(updateClaim,HttpStatus.CREATED);
    }

    @DeleteMapping("/Claim/{id}")
    public ResponseEntity<?> deleteClaimRequest(@PathVariable("id") int id){
        claimRequestService.deleteClaimRequest(id);
        return new ResponseEntity<String>("Claim Delete Successfully....", HttpStatus.OK);
    }
    
    @PostMapping("/CreateLeave")
    public ResponseEntity<?> createLeave(@RequestBody Leave leave){
    	Leave createLeave = leaveService.createLeave(leave);
    	return new ResponseEntity<Leave>(createLeave,HttpStatus.CREATED);
    }
    
    @GetMapping("/Leave/{id}")
    public ResponseEntity<?> getLeaveById(@PathVariable("id") int id){
    	Leave leaveById = leaveService.getLeaveById(id);
    	return new ResponseEntity<Leave>(leaveById,HttpStatus.OK);
    }
    
    @GetMapping("/Leave")
    public ResponseEntity<?> getAllLeave(){
    	List<Leave> allLeave = leaveService.getAllLeaveOfUser();
    	return new ResponseEntity<List<Leave>>(allLeave,HttpStatus.OK);
    }
    
    @PutMapping("/Leave/{id}")
    public ResponseEntity<?> updateLeave(@PathVariable("id") int id, @RequestBody Leave leave){
    	Leave updateLeave = leaveService.updateLeave(leave);
    	return new ResponseEntity<Leave>(updateLeave,HttpStatus.OK);
    }
    
    @DeleteMapping("/Leave/{id}")
    public ResponseEntity<?> deleteLeave(@PathVariable("id") int id){
    	leaveService.deleteLeave(id);
    	return new ResponseEntity<String>("Delete Leave Successfully...",HttpStatus.OK);
    }
   
}
