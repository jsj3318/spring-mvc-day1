package com.nhnacademy.jbgw08_043mvcday1.repository;

import com.nhnacademy.jbgw08_043mvcday1.domain.User;

import java.util.List;

public interface UserRepository {
    boolean exists(String id);
    boolean matches(String id, String password);
    List<User> getUsers();
    User getUser(String id);
    User addUser(String id, String password);

    User addUser(String id, String password, int age);
}
