package com.springSecurityForms.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springSecurityForms.model.EmployeeEntity;
import com.springSecurityForms.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl {
	
	@Autowired
	private EmployeeRepository empRepo;
	
	
	public EmployeeEntity  saveEmployees(EmployeeEntity emp) {
		
	     EmployeeEntity employees = this.empRepo.save(emp);
		
		return employees;
		
	}

}
