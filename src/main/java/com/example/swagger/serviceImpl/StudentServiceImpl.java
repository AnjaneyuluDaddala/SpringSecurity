package com.example.swagger.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.swagger.model.Student;
import com.example.swagger.repository.studentRepository;
import com.example.swagger.service.StudentService;

@Service
public class StudentServiceImpl extends StudentService{

    @Autowired
    private studentRepository stdRepo;


    public Student createStd(Student std){

        return stdRepo.save(std);

    }

    public Student getById(Long id){
        return stdRepo.findById(id).get();

    }

    public List<Student>getAllStudents(){

        return stdRepo.findAll();
        
    }


    public void deleteAllStudents(){
        stdRepo.deleteAll();
    }
}
