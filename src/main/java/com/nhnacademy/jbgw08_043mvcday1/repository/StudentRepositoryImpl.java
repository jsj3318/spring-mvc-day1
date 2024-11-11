package com.nhnacademy.jbgw08_043mvcday1.repository;

import com.nhnacademy.jbgw08_043mvcday1.domain.Student;
import com.nhnacademy.jbgw08_043mvcday1.exception.StudentAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class StudentRepositoryImpl implements StudentRepository {
    private final Map<String, Student> studentMap = new HashMap<>();

    public StudentRepositoryImpl(){
        studentMap.put("marco", Student.create(
                "marco",
                "4444",
                "마르코",
                "marco@dooray",
                100,
                "great"
        ));
    }

    @Override
    public boolean exists(String id) {
        return studentMap.containsKey(id);
    }

    @Override
    public boolean matches(String id, String password) {
        return Optional.ofNullable(getStudent(id))
                .map(student -> student.getPassword().equals(password))
                .orElse(false);
    }

    @Override
    public Student register(String id, String password, String name, String email, int score, String comment) {
        if(studentMap.containsKey(id)){
            throw new StudentAlreadyExistsException();
        }
        Student student = Student.create(id, password, name, email, score, comment);
        studentMap.put(id, student);

        for(Student st : studentMap.values()){
            log.info(st.getId());
        }

        return student;
    }

    @Override
    public Student getStudent(String id) {
        return studentMap.get(id);
    }

    @Override
    public void update(Student student) {
        if(!studentMap.containsKey(student.getId())){
            return;
        }

        studentMap.put(student.getId(), student);

    }
}
