package com.springSecurityForms.serviceInter;

import com.springSecurityForms.employeeDto.EmployeeRegisterDto;
import com.springSecurityForms.model.EmployeeEntity;

public interface EmployeeServiceInter {
	EmployeeEntity save(EmployeeRegisterDto EmpRegisterDTO);

}
