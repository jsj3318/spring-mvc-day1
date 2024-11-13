package com.nhnacademy.jbgw08_043mvcday1.controller;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentLogoutContoller.class)

class StudentLogoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void doLogout() throws Exception {
        mockMvc.perform(post("/student/logout")
                        .sessionAttr("SESSION", "marco")
                        .cookie(new Cookie("SESSION", "marco")))
                .andExpect(status().is3xxRedirection())
                .andExpect(cookie().value("SESSION", ""))
                .andExpect(cookie().maxAge("SESSION", 0))
                .andExpect(redirectedUrl("/student/login"));
    }

}