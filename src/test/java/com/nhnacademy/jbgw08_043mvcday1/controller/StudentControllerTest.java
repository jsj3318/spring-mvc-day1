package com.nhnacademy.jbgw08_043mvcday1.controller;

import com.nhnacademy.jbgw08_043mvcday1.domain.Student;
import com.nhnacademy.jbgw08_043mvcday1.exception.StudentNotFoundException;
import com.nhnacademy.jbgw08_043mvcday1.exception.ValidationFailedException;
import com.nhnacademy.jbgw08_043mvcday1.repository.StudentRepository;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        Student student = Student.create("marco", "4444", "마르코", "marco@nhn.com", 100, "king");
        when(studentRepository.getStudent("marco")).thenReturn(student);
    }

    @Test
    void viewStudent_fail() throws Exception {
        mockMvc.perform(get("/student/jsj")
                .cookie(new Cookie("SESSION", "marco")))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof StudentNotFoundException));
    }

    @Test
    void viewStudent() throws Exception {
        mockMvc.perform(get("/student/{studentId}","marco")
                        .cookie(new Cookie("SESSION", "marco")))
                .andExpect(status().isOk())
                .andExpect(view().name("studentView"))
                .andExpect(model().attributeExists("student"))
                .andExpect(content().string(containsString("마르코")));
    }


    @Test
    void viewStudent_hideScore() throws Exception {
        mockMvc.perform(get("/student/{studentId}","marco")
                        .param("hideScore","yes")
                        .cookie(new Cookie("SESSION", "marco")))
                .andExpect(status().isOk())
                .andExpect(view().name("studentView"))
                .andExpect(model().attributeExists("student"))
                .andExpect(content().string(containsString("<span>???</span>")));
    }

    @Test
    void viewStudent_hideScore_no() throws Exception {
        mockMvc.perform(get("/student/{studentId}","marco")
                        .param("hideScore","no")
                        .cookie(new Cookie("SESSION", "marco")))
                .andExpect(status().isOk())
                .andExpect(view().name("studentView"))
                .andExpect(model().attributeExists("student"))
                .andExpect(content().string(containsString("<span>100</span>")));
    }

    @Test
    void studentModifyForm() throws Exception {
        mockMvc.perform(get("/student/marco/modify")
                        .cookie(new Cookie("SESSION", "marco")))
                .andExpect(status().isOk())
                .andExpect(view().name("studentModify"));
    }

    @Test
    void studentModify_Success() throws Exception {
        mockMvc.perform(post("/student/marco/modify")
                        .cookie(new Cookie("SESSION", "marco"))
                        .param("id", "marco")
                        .param("password", "4444")
                        .param("name", "마르코")
                        .param("email", "marco@nhn.com")
                        .param("score", "100")
                        .param("comment", "god"))
                .andExpect(status().isOk())
                .andExpect(view().name("studentView"))
                .andExpect(model().attributeExists("student"))
                .andExpect(model().attribute("student", hasProperty("name", is("마르코"))));
    }

    @Test
    void studentModify_Fail() throws Exception {
        mockMvc.perform(post("/student/marco/modify")
                        .cookie(new Cookie("SESSION", "marco"))
                        .param("id", "marco")
                        .param("password", "asd")
                        .param("name", "")
                        .param("email", "marco")
                        .param("score", "-10")
                        .param("comment", ""))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationFailedException));
    }

}