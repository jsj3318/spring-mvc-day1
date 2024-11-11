package com.nhnacademy.jbgw08_043mvcday1.domain;

import lombok.Value;

@Value
public class StudentRegisterRequest {
    String id;
    String password;
    String name;
    String email;
    int score;
    String comment;
}
