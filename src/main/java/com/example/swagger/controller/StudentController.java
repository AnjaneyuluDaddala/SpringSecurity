package com.example.swagger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.swagger.model.Student;
import com.example.swagger.serviceImpl.StudentServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/swagger")
@Tag(name = "studentController",description = "controlling for student operations")
public class StudentController {

    @Autowired 
    private StudentServiceImpl service;

    @Operation(summary = "welcome page",description="display starting homepage")
    @ApiResponse(responseCode = "200",description = "homepage display succesfully")
    @GetMapping("/")
    public String getWelcome() {
        return "welcome page";
    }
    

    @Operation(summary = "creating new student",description="display page on created user")
    @ApiResponse(responseCode = "200",description = "user created succesfully")
    @PostMapping("/createStd")
    public Student createrStd(@RequestBody Student student){

        return this.service.createStd(student);

    }

    @Operation(summary = "Get single student",description="display page for id based student")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200",description = "student profile displayed"),
        @ApiResponse(responseCode = "404",description = "student not found")
    })
    @GetMapping("/student/{id}")
    public Student getById(@PathVariable Long id){
        return this.service.getById(id);
    }

    @Operation(summary = "get all students",description="display all students")
    @ApiResponse(responseCode = "200",description = "dispaly student list succesfully")
    @GetMapping("/list")
    public List<Student> getAllStudents(){
        return this.service.getAllStudents();
    }

    @Operation(summary = "delete students",description="remove students")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200",description = "student remove succesfully"),
        @ApiResponse(responseCode = "404",description = "student not found")
    })
    @DeleteMapping("/remove")
    public void removeStudnets(){
         this.service.deleteAllStudents();
    }

}
