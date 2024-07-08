package com.springSecurityForms.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springSecurityForms.employeeDto.EmployeeRegisterDto;
import com.springSecurityForms.model.EmployeeEntity;
import com.springSecurityForms.repository.EmployeeRepository;
import com.springSecurityForms.serviceInter.EmployeeServiceInter;

@Service
public class EmployeeServiceImpl implements EmployeeServiceInter{
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired
	private PasswordEncoder pswdEncode;
	

	
	public EmployeeEntity getEmpDetails(Long id) {
		
		return this.empRepo.findById(id).get();
		
	}

	@Override
	public EmployeeEntity save(EmployeeRegisterDto emp) {
		
        EmployeeEntity employee =new EmployeeEntity();
		
		employee.setPassword(pswdEncode.encode(emp.getPassword()));
		employee.setRoles(emp.getRole());
		employee.setAge(emp.getAge());
		employee.setEmail(emp.getEmail());
		employee.setSalary(emp.getSalary());
		employee.setName(emp.getName());
		
	     EmployeeEntity employees = this.empRepo.save(employee);
		
		return employees;
	}


}
