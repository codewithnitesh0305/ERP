package com.springboot.Service;

import java.util.List;

import com.springboot.Model.Leave;

public interface LeaveService {

	public Leave createLeave(Leave leave);
	public Leave getLeaveById(int id);
	public List<Leave> getAllLeave();
	public List<Leave> getAllLeaveOfUser();
	public Leave updateLeave(Leave leave);
	public void deleteLeave(int id);
	public int updateLeaveRequest(int id, boolean status); 
}
