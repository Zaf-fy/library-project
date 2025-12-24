package com.project.LibraryManagementSystem.service;

import com.project.LibraryManagementSystem.JpaRepository.UserRepository;
import com.project.LibraryManagementSystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        user.setEnabled(false); // librarian approval needed
        return userRepository.save(user);
    }

    @Override
    public void registerUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        user.setEnabled(false); // admin approval
        userRepository.save(user);
    }

    @Override
    public List<User> searchUsersByEnabled(boolean enabled) {
        return userRepository.findByEnabled(enabled);
    }

    @Override
    public void approveUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public List<User> searchUsers(String username) {

        return userRepository.findByUsernameContaining(username);
    }

    @Override
    public List<User> getPendingUsers() {
        return userRepository.findByEnabledFalse();
    }

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
