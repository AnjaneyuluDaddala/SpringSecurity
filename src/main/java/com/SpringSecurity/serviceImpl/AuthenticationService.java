package com.SpringSecurity.serviceImpl;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SpringSecurity.model.AuthenticationResponse;
import com.SpringSecurity.model.Employee;
import com.SpringSecurity.model.Tokens;
import com.SpringSecurity.repository.EmployeeRepository;
import com.SpringSecurity.repository.TokenRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthenticationService {


	    private final EmployeeRepository empRepo;
	    private final PasswordEncoder passwordEncoder;
	    private final JwtServiceImpl jwtService;

	    private final TokenRepository tokenRepository;

	    private final AuthenticationManager authenticationManager;

	    public AuthenticationService(EmployeeRepository empRepo,
	                                 PasswordEncoder passwordEncoder,
	                                 JwtServiceImpl jwtService,
	                                 TokenRepository tokenRepository,
	                                 AuthenticationManager authenticationManager) {
	        this.empRepo = empRepo;
	        this.passwordEncoder = passwordEncoder;
	        this.jwtService = jwtService;
	        this.tokenRepository = tokenRepository;
	        this.authenticationManager = authenticationManager;
	    }
	    
	    
	    

	   
	    public AuthenticationResponse register(Employee request) {

	        // check if user already exist. if exist than authenticate the user
	        if(empRepo.findByUsername(request.getUsername()).isPresent()) {
	            return new AuthenticationResponse(null, null,"User already exist");
	        }

	        Employee employee = new Employee();
	        employee.setFirstName(request.getFirstName());
	        employee.setLastName(request.getLastName());
	        employee.setUsername(request.getUsername());
	        employee.setPassword(passwordEncoder.encode(request.getPassword()));


	        employee.setRole(request.getRole());

	          Employee emp = empRepo.save(employee);
	          
	          //saving the token

	        String accessToken = jwtService.generateAccessToken(emp);
	        String refreshToken = jwtService.generateRefreshToken(emp);

	        saveUserToken(accessToken, refreshToken, emp);

	        return new AuthenticationResponse(accessToken, refreshToken,"User registration was successful");

	    }

	    //use login
	    public AuthenticationResponse authenticate(Employee request) {
	        authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        request.getUsername(),
	                        request.getPassword()
	                )
	        );
           //getting emp from database.
	        Employee emp = empRepo.findByUsername(request.getUsername()).orElseThrow();
	        String accessToken = jwtService.generateAccessToken(emp);
	        String refreshToken = jwtService.generateRefreshToken(emp);

	        revokeAllTokenByUser(emp);
	        saveUserToken(accessToken, refreshToken, emp);

	        return new AuthenticationResponse(accessToken, refreshToken, "User login was successful");

	    }
	    
	    
	    private void revokeAllTokenByUser(Employee emp) {
	        List<Tokens> validTokens = tokenRepository.findAllAccessTokensByUser(emp.getId());
	        if(validTokens.isEmpty()) {
	            return;
	        }

	        validTokens.forEach(t-> {
	            t.setLoggedOut(true);
	        });

	        tokenRepository.saveAll(validTokens);
	    }
	    
	    private void saveUserToken(String accessToken, String refreshToken, Employee emp) {
	        Tokens token = new Tokens();
	        token.setAccessToken(accessToken);
	        token.setRefreshToken(refreshToken);
	        token.setLoggedOut(false);
	        token.setEmployee(emp);
	        tokenRepository.save(token);
	    }

	    public ResponseEntity refreshToken(
	            HttpServletRequest request,
	            HttpServletResponse response) {
	        // extract the token from authorization header
	        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

	        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
	            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	        }

	        String token = authHeader.substring(7);

	        // extract username from token
	        String username = jwtService.extractUsername(token);

	        // check if the user exist in database
	        Employee emp = empRepo.findByUsername(username)
	                .orElseThrow(()->new RuntimeException("No user found"));

	        // check if the token is valid
	        if(jwtService.isValidRefreshToken(token, emp)) {
	            // generate access token
	            String accessToken = jwtService.generateAccessToken(emp);
	            String refreshToken = jwtService.generateRefreshToken(emp);

	            revokeAllTokenByUser(emp);
	            saveUserToken(accessToken, refreshToken, emp);

	            return new ResponseEntity(new AuthenticationResponse(accessToken, refreshToken, "New token generated"), HttpStatus.OK);
	        }

	        return new ResponseEntity(HttpStatus.UNAUTHORIZED);

	    }
	    
	}
