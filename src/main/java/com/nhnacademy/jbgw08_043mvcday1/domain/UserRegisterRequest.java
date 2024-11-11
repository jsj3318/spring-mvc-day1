package com.nhnacademy.jbgw08_043mvcday1.domain;

import lombok.Value;

@Value
public class UserRegisterRequest {
    String id;
    String password;
    int age;

}
