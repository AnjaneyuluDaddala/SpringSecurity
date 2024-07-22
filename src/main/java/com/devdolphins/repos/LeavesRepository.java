package com.devdolphins.repos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devdolphins.models.Employees;
import com.devdolphins.models.Leaves;

public interface LeavesRepository extends JpaRepository<Leaves, Integer> {

	List<Leaves> findByStatus(String status);

	List<Leaves> findByDateOfLeave(LocalDate dateOfLeave);

	Leaves findByLeaveId(Integer leaveId);

	List<Leaves> findByEmployeeAndStatus(Employees employee, String string);

}
