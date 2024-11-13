package com.nhnacademy.jbgw08_043mvcday1.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student/logout")
public class StudentLogoutContoller {

    @PostMapping
    public String doLogout(
            HttpServletRequest request,
            HttpServletResponse response
    ){
        // 세션 제거
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }

        // SESSION 쿠키 제거
        Cookie cookie = new Cookie("SESSION", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/student/login";
    }

}
