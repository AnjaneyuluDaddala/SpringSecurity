package com.devdolphins.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.devdolphins.models.Employees;
import com.devdolphins.repos.EmployeeRepo;
import com.devdolphins.service.EmployeeServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

	@Mock
	private EmployeeRepo employeeRepo;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	private Employees employee1;
	private Employees employee2;

	@BeforeEach
	void setUp() {
		employee1 = new Employees();
		employee1.setEmployeeId("DEDOL-0001");
		employee1.setAdhaar(123456789012L);
		employee1.setPan("ABCDE1234F");
		employee1.setMobile(9876543210L);
		employee1.setEmail("employee1@example.com");
		employee1.setStatus(true);

		employee2 = new Employees();
		employee2.setEmployeeId("DEDOL-0002");
		employee2.setAdhaar(123456789013L);
		employee2.setPan("ABCDE1234G");
		employee2.setMobile(9876543211L);
		employee2.setEmail("employee2@example.com");
		employee2.setStatus(false);
	}

	@Test
	void testGetAllEmployees() {
		when(employeeRepo.findAll()).thenReturn(Arrays.asList(employee1, employee2));
		List<Employees> employees = employeeService.getAllEmployees();
		assertEquals(2, employees.size());

	}

	@Test
	void testGetAllActiveEmployees() {
		when(employeeRepo.findByStatus(true)).thenReturn(Arrays.asList(employee1));
		List<Employees> employees = employeeService.getAllActiveEmployees();
		assertEquals(1, employees.size());
		verify(employeeRepo, times(1)).findByStatus(true);
	}

	@Test
	void testGetEmployeeByEmployeeId() {
		when(employeeRepo.findByEmployeeId("DEDOL-0001")).thenReturn(employee1);
		Optional<Employees> employee = employeeService.getEmployeeByEmployeeId("DEDOL-0001");
		assertTrue(employee.isPresent());
		assertEquals("DEDOL-0001", employee.get().getEmployeeId());
		verify(employeeRepo, times(1)).findByEmployeeId("DEDOL-0001");
	}

	@Test
	void testUpdatePassword() {
		when(employeeRepo.findByEmployeeId("DEDOL-0001")).thenReturn(employee1);
		when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");
		when(employeeRepo.save(any(Employees.class))).thenReturn(employee1);

		employeeService.updatePassword("DEDOL-0001", "newPassword");
		assertEquals("encodedPassword", employee1.getPassword());
		verify(employeeRepo, times(1)).save(any(Employees.class));
	}

	@Test
	void testUpdateEmployeeRole() {
		employee1.setRole("HR");

		when(employeeRepo.findByEmployeeId("DEDOL-0001")).thenReturn(employee1);
		when(employeeRepo.save(any(Employees.class))).thenReturn(employee1);
		assertEquals("HR", employee1.getRole());
		employeeService.updateEmployeeRole("DEDOL-0001", "ADMIN");
		assertEquals("ADMIN", employee1.getRole());
		verify(employeeRepo, times(1)).save(any(Employees.class));
	}

	@Test
	void testAddEmployee_Success() throws Exception {

		when(employeeRepo.save(any(Employees.class))).thenAnswer(invocation -> {
			Employees savedEmployee = invocation.getArgument(0);
			savedEmployee.setEmployeeId("DEDOL-0003"); // Mock new ID assignment
			return savedEmployee;
		});
		when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");

		Employees newEmployee = new Employees(); // Create a new employee object
		newEmployee.setEmail("newemployee@example.com");
		newEmployee.setRole("USER");
		newEmployee.setTotalLeaves(12);
		newEmployee.setStatus(true);

		Employees addedEmployee = employeeService.addEmployee(newEmployee);

		// Verify the returned employee details
		assertNotNull(addedEmployee);
		assertEquals("DEDOL-0003", addedEmployee.getEmployeeId()); // Ensure the new ID is set correctly
		assertEquals("USER", addedEmployee.getRole());
		assertEquals(12, addedEmployee.getTotalLeaves());

		verify(employeeRepo, times(1)).save(any(Employees.class));
	}

	@Test
	void testIsUnique() {
		when(employeeRepo.existsByEmail("employee1@example.com")).thenReturn(false);

		Employees existingEmployee = new Employees();
		existingEmployee.setEmployeeId("DEDOL-0001");
		existingEmployee.setEmail("employee1@example.com");

		boolean unique = employeeService.isUnique(existingEmployee);
		assertTrue(unique);
		verify(employeeRepo, times(1)).existsByEmail("employee1@example.com");
	}
	
}
