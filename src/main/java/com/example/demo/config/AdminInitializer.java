package com.example.demo.config;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserService userService;

    @Autowired
    public AdminInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        // Создаем администратора
        if (userService.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("Admin123!");
            admin.setEmail("admin@example.com");
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setRole("ADMIN");
            userService.registerUser(admin);
        }

        // Создаем обычного пользователя
        if (userService.findByUsername("user") == null) {
            User user = new User();
            user.setUsername("user");
            user.setPassword("q12345");
            user.setEmail("user@example.com");
            user.setFirstName("User");
            user.setLastName("User");
            user.setRole("USER");
            userService.registerUser(user);
        }
    }
} 