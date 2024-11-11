package com.nhnacademy.jbgw08_043mvcday1.repository;

import com.nhnacademy.springbootmvc.domain.User;
import com.nhnacademy.springbootmvc.exception.UserAlreadyExistsException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Map<String, User> userMap = new HashMap<>();

    public UserRepositoryImpl() {
        userMap.put("tom", User.create("tom", "1234"));
        userMap.put("jake", User.create("jake", "5678"));
        userMap.put("ethan", User.create("ethan", "1010"));
    }

    @Override
    public boolean exists(String id) {
        return userMap.containsKey(id);
    }

    @Override
    public boolean matches(String id, String password) {
        return Optional.ofNullable(getUser(id))
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

    @Override
    public List<User> getUsers() {
        return userMap.values().stream().toList();
    }

    @Override
    public User getUser(String id) {
        return userMap.get(id);
    }

    @Override
    public User addUser(String id, String password) {
        return addUser(id, password, 0);
    }

    @Override
    public User addUser(String id, String password, int age) {
        if (exists(id)) {
            throw new UserAlreadyExistsException();
        }

        User user = User.create(id, password);
        user.setAge(age);

        userMap.put(id, user);

        return user;
    }
}
