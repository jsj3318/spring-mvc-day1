package com.nhnacademy.jbgw08_043mvcday1.domain;

import jakarta.validation.constraints.*;
import lombok.Value;

@Value
public class StudentRegisterRequest {
    String id;

    String password;

    @NotBlank
    String name;

    @Email
    String email;

    @Max(100) @Min(0)
    int score;

    @NotBlank @Size(max = 200)
    String comment;

}
