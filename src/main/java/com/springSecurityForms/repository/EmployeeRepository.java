package com.springSecurityForms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springSecurityForms.model.EmployeeEntity;

@Repository
public interface EmployeeRepository  extends JpaRepository<EmployeeEntity, Long>{
	
    Optional<EmployeeEntity> findByEmail(String username);
    Boolean existsByEmail(String username);

}
