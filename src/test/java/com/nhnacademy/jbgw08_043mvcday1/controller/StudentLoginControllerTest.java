package com.nhnacademy.jbgw08_043mvcday1.controller;

import com.nhnacademy.jbgw08_043mvcday1.domain.Student;
import com.nhnacademy.jbgw08_043mvcday1.repository.StudentRepository;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentLoginController.class)
@ContextConfiguration(classes = {StudentRepository.class})
class StudentLoginControllerTest {

    private MockMvc mockMvc;

    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);

        mockMvc = MockMvcBuilders.standaloneSetup(new StudentLoginController(studentRepository))
                .build();

        Student student = Student.create(
                "marco",
                "4444",
                "마르코",
                "marco@nhn.com",
                100,
                "good"
        );

        when(studentRepository.matches("marco","4444")).thenReturn(true);
        when(studentRepository.getStudent("marco")).thenReturn(student);

        when(studentRepository.matches("marco","123")).thenReturn(false);

    }

    @Test
    void login() throws Exception {
        mockMvc.perform(get("/student/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginForm"));
    }

    @Test
    void alreadyLogin() throws Exception {
        mockMvc.perform(get("/student/login")
                .cookie(new Cookie("SESSION", "marco")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/student/marco"));
    }

    @Test
    void doLogin_success() throws Exception {
        mockMvc.perform(post("/student/login")
                .param("id", "marco")
                .param("pwd", "4444"))
                .andExpect(status().isOk())
                .andExpect(view().name("studentView"))
                .andExpect(model().attributeExists("student"))
                .andExpect(cookie().exists("SESSION"))
                .andExpect(cookie().value("SESSION", "marco"));
    }

    @Test
    void doLogin_fail() throws Exception {
        mockMvc.perform(post("/student/login")
                        .param("id", "marco")
                        .param("pwd", "123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/student/login"));
    }

}