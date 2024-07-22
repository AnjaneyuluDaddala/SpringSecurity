package com.devdolphins.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.devdolphins.dto.LeaveDTO;
import com.devdolphins.models.Leaves;
import com.devdolphins.repos.EmployeeRepo;
import com.devdolphins.repos.LeavesRepository;
import com.devdolphins.service.LeavesServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

 class LeavesServiceImplTest {
	 
	 private AutoCloseable closeable;

    @Mock
    private EmployeeRepo employeesRepository;

    @Mock
    private LeavesRepository leavesRepository;

    @InjectMocks
    private LeavesServiceImpl leavesService;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }
    
    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }


  

    @Test
     void testAddLeaves_EmployeeNotFound() {
        // Mock authenticated employee
        String authenticatedEmployeeId = "emp123";

        // Mock repository behavior for employee not found
        when(employeesRepository.findById(anyString())).thenReturn(Optional.empty());

        // Call service method and assert exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            leavesService.addLeaves(authenticatedEmployeeId, new LeaveDTO());
        });

        // Verify interactions
        verify(employeesRepository, times(1)).findById(authenticatedEmployeeId);
        verify(leavesRepository, never()).save(any(Leaves.class));

        // Assert the exception message
        assert exception.getMessage().contains("Authenticated employee not found.");
    }
}
