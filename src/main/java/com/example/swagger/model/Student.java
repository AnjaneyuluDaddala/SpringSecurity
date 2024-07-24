package com.example.swagger.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="swagger_student")
@Schema( description="unique Entity")
public class Student {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Schema(description="unique identifier of student",example = "1")
    @Column(name="student_id",unique = true,nullable = false)
    private Long id;
    @Schema(description="firstname of student",example = "aj")
    @Column(name="first_name")
    private String firstName;
    @Schema(description="lastName of student",example = "tech")
    @Column(name="last_name")
    private String lastName;
    @Schema(description="email of student",example = "ajtech01@gmail.com")
    @Column(unique = true,nullable = false)
    private String email;

}
