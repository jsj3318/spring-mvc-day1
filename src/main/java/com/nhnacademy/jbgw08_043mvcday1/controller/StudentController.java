package com.nhnacademy.jbgw08_043mvcday1.controller;

import com.nhnacademy.jbgw08_043mvcday1.domain.Student;
import com.nhnacademy.jbgw08_043mvcday1.domain.StudentRegisterRequest;
import com.nhnacademy.jbgw08_043mvcday1.exception.StudentNotFoundException;
import com.nhnacademy.jbgw08_043mvcday1.exception.ValidationFailedException;
import com.nhnacademy.jbgw08_043mvcday1.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
        Student student = studentRepository.getStudent(studentId);
        if(student == null){
            throw new StudentNotFoundException();
        }
        return student;
    }

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound(Model model){
        model.addAttribute("exception", "Student not found");
        return "error";
    }

    // 일반적인 정보 출력
    @GetMapping(value = "/{studentId}", params = {"!hideScore"})
    public String viewStudent( Model model ) {
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

        Student student = (Student) model.getAttribute("student");

        if(hide.equals("yes")){
            // 점수, 평가 정보를 숨긴다
            model.addAttribute("student",
                    Student.constructMoreMaskedStudent(student));
        } else {
            // 비번만 숨긴다
            model.addAttribute("student",
                    Student.constructPasswordMaskedStudent(student));
        }
        return "studentView";
    }

    @GetMapping("/{studentId}/modify")
    public String studentModifyForm() {

        return "studentModify";
    }

    @PostMapping("/{studentId}/modify")
    public String studentModify(
            Model model,
            @Validated @ModelAttribute StudentRegisterRequest request,
            BindingResult bindingResult
    ) {
        // 입력값 검사
        if(bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        Student student = Student.create(
                        request.getId(),
                        request.getPassword(),
                        request.getName(),
                        request.getEmail(),
                        request.getScore(),
                        request.getComment()
        );
        studentRepository.update(student);
        return "redirect:/student/"+student.getId();

    }

}
