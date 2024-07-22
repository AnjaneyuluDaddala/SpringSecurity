package com.devdolphins.service;

import java.util.List;

import com.devdolphins.dto.LeaveBalanceDTO;
import com.devdolphins.dto.LeaveDTO;

public interface LeavesService {

    void addLeaves(String authenticatedEmployeeId, LeaveDTO leaveDTO);
    List<LeaveDTO> getPendingLeaves();
    List<LeaveDTO> employeeOnleave();
    List<LeaveDTO> getMyLeaves(String employeeId);
    
    void denyLeave(Integer leaveId, String denyReason);
    void approveLeave(Integer leaveId);
	void deleteLeave(Integer leaveId);
	LeaveBalanceDTO getLeaveBalance(String employeeId);
}
