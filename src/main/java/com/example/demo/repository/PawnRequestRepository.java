package com.example.demo.repository;

import com.example.demo.model.PawnRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PawnRequestRepository extends JpaRepository<PawnRequest, Long> {
    List<PawnRequest> findByStatus(String status);
    List<PawnRequest> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<PawnRequest> findByStatusOrderByCreatedAtDesc(String status);
} 