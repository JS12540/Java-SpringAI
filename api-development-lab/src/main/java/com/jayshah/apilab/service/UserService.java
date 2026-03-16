package com.jayshah.apilab.service;

import com.jayshah.apilab.model.User;
import com.jayshah.apilab.util.DataStore;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public List<User> getUsers() {
        log.info("Fetching all users, total count: {}", DataStore.users.size());
        return DataStore.users;
    }

    public User createUser(User user) {
        log.info("Creating user: id={}, name={}, age={}", user.getId(), user.getName(), user.getAge());
        DataStore.users.add(user);
        log.info("User created successfully: {}", user.getName());
        return user;
    }
}