package com.project.LibraryManagementSystem.service;

import com.project.LibraryManagementSystem.entity.User;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {

    User registerUser(User user);

    void registerUser(String username, String password);

    List<User> searchUsersByEnabled(boolean enabled);


    void approveUser(Long userId);

    List<User> searchUsers(String username);

    List<User> getPendingUsers();

    void save(@Valid User user);
}


