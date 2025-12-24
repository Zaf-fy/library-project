package com.project.LibraryManagementSystem.controller;

import com.project.LibraryManagementSystem.entity.User;
import com.project.LibraryManagementSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("user") User user,
            BindingResult result,
            Model model) {

        // Set role before validation
        user.setRole("USER");
        user.setEnabled(false);

        // Validate manually using Spring Validator
        // or use javax.validation programmatically
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            result.rejectValue("username", "error.user", "Username is required");
        } else if (user.getUsername().length() < 4 || user.getUsername().length() > 20) {
            result.rejectValue("username", "error.user", "Username must be 4-20 characters");
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            result.rejectValue("password", "error.user", "Password is required");
        } else if (user.getPassword().length() < 6) {
            result.rejectValue("password", "error.user", "Password must be at least 6 characters");
        }

        if (result.hasErrors()) {
            return "register"; // errors will now show in Thymeleaf
        }

        // Save user
        userService.save(user);

        return "redirect:/login?registered";
    }



    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
}