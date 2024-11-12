package com.nhnacademy.jbgw08_043mvcday1.controller;

import com.nhnacademy.jbgw08_043mvcday1.domain.Student;
import com.nhnacademy.jbgw08_043mvcday1.domain.StudentRegisterRequest;
import com.nhnacademy.jbgw08_043mvcday1.exception.ValidationFailedException;
import com.nhnacademy.jbgw08_043mvcday1.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/student/register")
public class StudentRegisterController {
    private final StudentRepository studentRepository;

    public StudentRegisterController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public String studentRegisterForm() {
        return "studentRegister";
    }

    @PostMapping
    public ModelAndView registerStudent(
            @Validated @ModelAttribute StudentRegisterRequest request,
            BindingResult bindingResult
            ) {
        // 입력값 검사
        if(bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        Student student = studentRepository.register(
                request.getId(),
                request.getPassword(),
                request.getName(),
                request.getEmail(),
                request.getScore(),
                request.getComment()
        );
        ModelAndView mav = new ModelAndView("studentView");
        mav.addObject("student", Student.constructPasswordMaskedStudent(student));

        return mav;
    }

}