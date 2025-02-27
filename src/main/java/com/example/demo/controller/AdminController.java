package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.PawnRequestService;
import com.example.demo.service.ExportService;
import com.example.demo.model.PawnRequest;
import com.example.demo.repository.PawnRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PawnRequestService pawnRequestService;

    @Autowired
    private ExportService exportService;

    @Autowired
    private PawnRequestRepository pawnRequestRepository;

    @GetMapping("/panel")
    public String adminPanel(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null && "ADMIN".equals(user.getRole())) {
            model.addAttribute("pendingRequests", pawnRequestService.getPendingRequests());
            return "admin/panel";
        }
        return "redirect:/login";
    }

    @PostMapping("/request/{id}")
    public String processRequest(@PathVariable Long id, 
                               @RequestParam String action,
                               @RequestParam String comment,
                               HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && "ADMIN".equals(user.getRole())) {
            pawnRequestService.updateRequestStatus(id, action, comment);
            return "redirect:/admin/panel";
        }
        return "redirect:/login";
    }

    @GetMapping("/export-pawn-requests")
    public void exportPawnRequests(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=pawn-requests.csv");
        
        // Добавляем BOM для Excel
        response.getOutputStream().write(0xEF);
        response.getOutputStream().write(0xBB);
        response.getOutputStream().write(0xBF);
        
        Writer writer = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8);
        exportService.exportPawnRequestsToCsv(writer);
    }

    @GetMapping("/requests")
    public List<PawnRequest> getAllRequests() {
        return pawnRequestRepository.findAll();
    }
} 