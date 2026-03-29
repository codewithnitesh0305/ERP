package com.springboot.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.Model.Leave;

@Repository
public interface LeavRepository extends JpaRepository<Leave, Integer>{

	public Leave findById(int id);
	 @Modifying
	 @Query(value = "UPDATE Leave_mast SET leave_status = :leaveStatus, approve_by= :approveby WHERE id = :leaveId", nativeQuery = true)
	 int updateLeaveStatus(@Param("leaveId") int id,@Param("approveby") String role,  @Param("leaveStatus") String status);
	 public List<Leave> findByEmployeeId(int id);
}
