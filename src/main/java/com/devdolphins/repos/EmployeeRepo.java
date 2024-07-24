package com.devdolphins.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devdolphins.models.Employees;

import feign.Param;

@Repository
public interface EmployeeRepo extends JpaRepository<Employees, String> {


	Employees findByFirstnameAndEmail(String firstname, String email);

	Employees findByFirstname(String firstname);
	
	Employees findByEmployeeId(String employeeid);

	Employees findByLastname(String lastname);
	
	List<Employees> findByRole(String role);


	List<Employees> findByStatus(boolean b);

	@Query("SELECT e FROM Employees e WHERE e.role <> 'ADMIN'")
	List<Employees> findAllEmployeesExceptAdmin();
	
    List<Employees> findByStatusTrue();

	boolean existsByAdhaar(Long adhaar);
    boolean existsByPan(String pan);
    boolean existsByMobile(Long mobile);
    boolean existsByEmail(String email);
    

	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Employees e WHERE e.role = :role")
    boolean existsByRole(@Param("role") String role);
    

}
