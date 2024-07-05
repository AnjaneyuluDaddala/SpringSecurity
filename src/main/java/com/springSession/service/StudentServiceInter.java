package com.springSession.service;

import com.springSession.exception.UserAlreadyExistException;
import com.springSession.model.Student;
import com.springSession.model.StudentDto;

public interface StudentServiceInter {
	
	void register(final StudentDto student)throws UserAlreadyExistException;
	
	boolean CheckIfUserExist(String email);
	
	void sendRegistrationConformationEmail(Student std);
	
	

}
