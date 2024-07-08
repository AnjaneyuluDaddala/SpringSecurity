package com.springSecurityForms.serviceInter;

import java.util.List;

import com.springSecurityForms.model.EmployeeEntity;

public interface EmployeeServiceInter {
	
	List<EmployeeEntity> getAllEmployees();
	void deleteEmployee(Long id);

}
