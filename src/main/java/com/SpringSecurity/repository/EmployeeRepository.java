package com.SpringSecurity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SpringSecurity.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	
	Optional<Employee> findByUsername(String username);

}
