package com.springSession.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springSession.exception.UserAlreadyExistException;
import com.springSession.model.SecurityToken;
import com.springSession.model.Student;
import com.springSession.model.StudentDto;
import com.springSession.repository.StudentRepository;
import com.springSession.repository.TokensRepository;
import com.springSession.service.SecurityTokenService;
import com.springSession.service.StudentServiceInter;

@Service
public class StudentServiceImpl implements StudentServiceInter{
	
	private final StudentRepository studentRepo;
	
	private final PasswordEncoder pswdEncode;
	
	private final SecurityTokenService secureTokenService;
	
	private final TokensRepository tokenRepo;

	
   @Autowired
   private StudentServiceImpl(PasswordEncoder pswdEncode,StudentRepository studentRepo,
		                      SecurityTokenService secureTokenService,TokensRepository tokenRepo) {
        this.pswdEncode = pswdEncode;
        this.studentRepo=studentRepo;
        this.secureTokenService=secureTokenService;
        this.tokenRepo=tokenRepo;
    }

	@Override
	public void register(StudentDto student) throws UserAlreadyExistException {
		
		
		if(CheckIfUserExist(student.getEmail())) {
			throw new UserAlreadyExistException("User already exists for this email");
		}
		
		Student std=new Student();
		std.setName(student.getName());
		std.setPassword(pswdEncode.encode(student.getPassword()));
		std.setCollegeName(student.getCollegeName());
		std.setCourse(student.getCourse());
		std.setEmail(student.getEmail());
		
		this.studentRepo.save(std);
		
		
	}

	@Override
	public boolean CheckIfUserExist(String email) {
		return this.studentRepo.findByEmail(email)!=null?true:false;
	}

	@Override
	public void sendRegistrationConformationEmail(Student std) {
    
		SecurityToken secureToken = secureTokenService.createSecureToken();
		secureToken.setStudent(std);
		tokenRepo.save(secureToken);
		

	}

}
