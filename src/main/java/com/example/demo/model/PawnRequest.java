package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pawn_requests")
public class PawnRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String itemName;
    private String description;
    
    @Column(length = 1000)
    private String imageUrl;
    private Double expectedPrice;
    private String status; // PENDING, APPROVED, REJECTED
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    private LocalDateTime createdAt;
    private String adminComment;
    
    @Column(nullable = true)
    private Double sellingPrice;
    
    private Boolean sold = false;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        status = "PENDING";
        if (expectedPrice != null) {
            sellingPrice = expectedPrice * 1.2;
        }
    }
} 