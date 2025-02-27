package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/balance")
public class BalanceController {

    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public String showAddBalanceForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("currentBalance", user.getBalance());
        return "balance/add";
    }

    @PostMapping("/add")
    public String addBalance(@RequestParam Double amount, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        if (amount <= 0) {
            return "redirect:/balance/add?error=invalid";
        }

        user.setBalance(user.getBalance() + amount);
        userService.updateUser(user);
        session.setAttribute("user", user); // Обновляем данные в сессии
        
        return "redirect:/balance/add?success=true";
    }
} 