package com.springSecurityForms.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springSecurityForms.model.EmployeeEntity;
import com.springSecurityForms.repository.EmployeeRepository;
import com.springSecurityForms.serviceInter.EmployeeServiceInter;

@Service
public class EmployeeServiceImpl implements EmployeeServiceInter{
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired
	private PasswordEncoder pswdEncode;
	
	
	public EmployeeEntity  saveEmployees(EmployeeEntity emp) {
		
		EmployeeEntity employee =new EmployeeEntity();
		
		employee.setPassword(pswdEncode.encode(emp.getPassword()));
		employee.setRoles(emp.getRoles());
		employee.setAge(emp.getAge());
		employee.setUsername(emp.getUsername());
		employee.setSalary(emp.getSalary());
		
	     EmployeeEntity employees = this.empRepo.save(employee);
		
		return employees;
		
	}
	
	public EmployeeEntity getEmpDetails(Long id) {
		
		return this.empRepo.findById(id).get();
		
	}

	@Override
	public List<EmployeeEntity> getAllEmployees() {
	
		return 	this.empRepo.findAll();
	}

	@Override
	public void deleteEmployee(Long id) {
		this.empRepo.deleteById(id);
		
	}

}
