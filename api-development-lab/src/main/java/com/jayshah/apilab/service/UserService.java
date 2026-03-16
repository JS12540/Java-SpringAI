package com.jayshah.apilab.service;

import com.jayshah.apilab.model.User;
import com.jayshah.apilab.util.DataStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public List<User> getUsers() {
        return DataStore.users;
    }

    public User createUser(User user) {
        DataStore.users.add(user);
        return user;
    }

}