package com.nhnacademy.jbgw08_043mvcday1.repository;

import com.nhnacademy.jbgw08_043mvcday1.domain.Student;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepositoryImpl implements StudentRepository {
    @Override
    public boolean exists(String id) {
        return false;
    }

    @Override
    public boolean matches(String id, String password) {
        return false;
    }

    @Override
    public Student register(String id, String password, String name, String email, int score, String comment) {
        return null;
    }

    @Override
    public Student getStudent(String id) {
        return null;
    }
}
