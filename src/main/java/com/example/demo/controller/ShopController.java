package com.example.demo.controller;

import com.example.demo.model.PawnRequest;
import com.example.demo.model.User;
import com.example.demo.service.PawnRequestService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private final PawnRequestService pawnRequestService;
    private final UserService userService;

    @Autowired
    public ShopController(PawnRequestService pawnRequestService, UserService userService) {
        this.pawnRequestService = pawnRequestService;
        this.userService = userService;
    }

    @GetMapping
    public String showShop(Model model) {
        model.addAttribute("items", pawnRequestService.getApprovedRequests());
        return "shop/index";
    }

    @PostMapping("/buy/{id}")
    public String buyItem(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        PawnRequest item = pawnRequestService.findById(id);
        if (item == null || item.getSold() || !"APPROVED".equals(item.getStatus())) {
            return "redirect:/shop?error=notAvailable";
        }

        if (user.getBalance() < item.getSellingPrice()) {
            return "redirect:/shop?error=insufficientFunds";
        }

        // Выполняем покупку
        user.setBalance(user.getBalance() - item.getSellingPrice());
        item.setSold(true);
        
        userService.updateUser(user);
        pawnRequestService.updateRequest(item);
        
        session.setAttribute("user", user);
        
        return "redirect:/shop?success=true";
    }
} 