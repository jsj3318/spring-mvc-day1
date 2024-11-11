package com.nhnacademy.jbgw08_043mvcday1.repository;


import com.nhnacademy.jbgw08_043mvcday1.domain.Student;

public interface StudentRepository {
    boolean exists(String id);

    Student register(String name, String email, int score, String comment);

    Student getStudent(String id);

}
