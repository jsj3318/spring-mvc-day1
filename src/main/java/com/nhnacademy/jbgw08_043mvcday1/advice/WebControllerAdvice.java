package com.nhnacademy.jbgw08_043mvcday1.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class WebControllerAdvice {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        log.error("", ex);

        model.addAttribute("exception", ex);
        return "error";
    }

}
