package com.nhnacademy.jbgw08_043mvcday1.controller;

import com.nhnacademy.jbgw08_043mvcday1.domain.Student;
import com.nhnacademy.jbgw08_043mvcday1.repository.StudentRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student/login")
public class StudentLoginController {

    private final StudentRepository studentRepository;

    public StudentLoginController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public String login(
            @CookieValue(value = "SESSION", required = false) String sessionId,
                        Model model
    ) {
        if (StringUtils.hasText(sessionId)) {
            model.addAttribute("id", sessionId);
            return "redirect:/student/"+sessionId;
        } else {
            return "loginForm";
        }
    }

    @PostMapping
    public String doLogin(@RequestParam("id") String id,
                          @RequestParam("pwd") String pwd,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          ModelMap modelMap) {
        if (studentRepository.matches(id, pwd)) {
            HttpSession session = request.getSession(true);

            Cookie cookie = new Cookie("SESSION", id);
            response.addCookie(cookie);

            modelMap.put("student",
                    Student.constructPasswordMaskedStudent(studentRepository.getStudent(id)));
            return "studentView";
        } else {
            return "redirect:/student/login";
        }
    }


}
