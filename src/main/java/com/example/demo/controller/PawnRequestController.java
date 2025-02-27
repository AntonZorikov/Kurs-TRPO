package com.example.demo.controller;

import com.example.demo.model.PawnRequest;
import com.example.demo.model.User;
import com.example.demo.service.PawnRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/pawn")
public class PawnRequestController {
    
    @Autowired
    private PawnRequestService pawnRequestService;

    private final String UPLOAD_DIR = "uploads/";

    @GetMapping("/new")
    public String showNewRequestForm(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "pawn/new-request";
    }

    @PostMapping("/new")
    public String createRequest(@ModelAttribute PawnRequest request,
                              @RequestParam("image") MultipartFile image,
                              HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        try {
            // Создаем директорию, если она не существует
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Генерируем уникальное имя файла
            String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            
            // Сохраняем файл
            Files.copy(image.getInputStream(), filePath);
            
            // Сохраняем путь к файлу в базе данных
            request.setImageUrl(fileName);
            
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/pawn/new?error=true";
        }

        request.setUser(user);
        pawnRequestService.createRequest(request);
        return "redirect:/pawn/my-requests";
    }

    @GetMapping("/my-requests")
    public String showMyRequests(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("requests", pawnRequestService.getUserRequests(user.getId()));
        return "pawn/my-requests";
    }

    @GetMapping("/approved")
    public String showApprovedRequests(Model model) {
        model.addAttribute("requests", pawnRequestService.getApprovedRequests());
        return "pawn/approved-requests";
    }
} 