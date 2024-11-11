package com.nhnacademy.jbgw08_043mvcday1.controller;

import com.nhnacademy.jbgw08_043mvcday1.domain.Student;
import com.nhnacademy.jbgw08_043mvcday1.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @ModelAttribute("student")
    public Student getStudent(@PathVariable("studentId") String studentId){
        return studentRepository.getStudent(studentId);
    }

    // 일반적인 정보 출력
    @GetMapping(value = "/{studentId}", params = {"!hideScore"})
    public String viewStudent(Model model) {
        Student student = (Student) model.getAttribute("student");
        model.addAttribute("student",
                Student.constructPasswordMaskedStudent(student));
        return "studentView";
    }

    // 점수, 평가 항목을 가리고 정보 출력
    @GetMapping(value = "/{studentId}", params = {"hideScore"})
    public String viewStudent(
            Model model,
            @RequestParam("hideScore") String hide
    ) {
        if(hide.equals("yes")){
            // 점수, 평가 정보를 숨긴다
            Student student = (Student) model.getAttribute("student");
            model.addAttribute("student",
                    Student.constructMoreMaskedStudent(student));
        }
        return "studentView";
    }

    @GetMapping("/{studentId}/modify")
    public String studentModifyForm() {
        return "studentModify";
    }

    @PostMapping("/{studentId}/modify")
    public String modifyUser() {
        return "studentView";
    }

}
