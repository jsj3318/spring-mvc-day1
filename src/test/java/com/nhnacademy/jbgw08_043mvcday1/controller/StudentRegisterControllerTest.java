package com.nhnacademy.jbgw08_043mvcday1.controller;

import com.nhnacademy.jbgw08_043mvcday1.domain.Student;
import com.nhnacademy.jbgw08_043mvcday1.exception.ValidationFailedException;
import com.nhnacademy.jbgw08_043mvcday1.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentRegisterController.class)
//@ContextConfiguration(classes = {StudentRepository.class})
class StudentRegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void studentRegisterForm() throws Exception {
        mockMvc.perform(get("/student/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("studentRegister"));
    }

    @Test
    void registerStudent_Success() throws Exception {
        Student student = Student.create("marco", "4444", "마르코", "marco@nhn.com", 100, "king");
        given(studentRepository.register("marco", "4444", "마르코", "marco@nhn.com", 100, "king"))
                .willReturn(student);

        mockMvc.perform(post("/student/register")
                        .param("id", "marco")
                        .param("password", "4444")
                        .param("name", "마르코")
                        .param("email", "marco@nhn.com")
                        .param("score", "100")
                        .param("comment", "king"))
                .andExpect(status().isOk())
                .andExpect(view().name("studentView"))
                .andExpect(model().attributeExists("student"))
                .andExpect(model().attribute("student", hasProperty("name", is("마르코"))));
    }

    @Test
    void registerStudent_Fail() throws Exception {

        mockMvc.perform(post("/student/register")
                        .param("id", "")
                        .param("password", "asd")
                        .param("name", "")
                        .param("email", "marco")
                        .param("score", "-10")
                        .param("comment", ""))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationFailedException));
    }


}