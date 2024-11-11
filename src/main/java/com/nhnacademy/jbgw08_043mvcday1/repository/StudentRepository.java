package com.nhnacademy.jbgw08_043mvcday1.repository;


import com.nhnacademy.jbgw08_043mvcday1.domain.Student;

public interface StudentRepository {
    boolean exists(String id);

    boolean matches(String id, String password);
    
    Student register(String id, String password, String name, String email, int score, String comment);

    Student getStudent(String id);

}
