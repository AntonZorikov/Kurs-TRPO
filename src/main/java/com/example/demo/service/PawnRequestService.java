package com.example.demo.service;

import com.example.demo.model.PawnRequest;
import com.example.demo.repository.PawnRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PawnRequestService {
    private final PawnRequestRepository pawnRequestRepository;

    @Autowired
    public PawnRequestService(PawnRequestRepository pawnRequestRepository) {
        this.pawnRequestRepository = pawnRequestRepository;
    }

    public PawnRequest createRequest(PawnRequest request) {
        return pawnRequestRepository.save(request);
    }

    public List<PawnRequest> getUserRequests(Long userId) {
        return pawnRequestRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<PawnRequest> getPendingRequests() {
        return pawnRequestRepository.findByStatus("NEW");
    }

    public List<PawnRequest> getApprovedRequests() {
        return pawnRequestRepository.findByStatusOrderByCreatedAtDesc("APPROVED");
    }

    public PawnRequest updateRequestStatus(Long requestId, String status, String comment) {
        PawnRequest request = pawnRequestRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(status);
        request.setAdminComment(comment);
        return pawnRequestRepository.save(request);
    }

    public PawnRequest findById(Long id) {
        return pawnRequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Request not found"));
    }

    public PawnRequest updateRequest(PawnRequest request) {
        return pawnRequestRepository.save(request);
    }

    public PawnRequest approveRequest(Long id, String comment) {
        PawnRequest request = pawnRequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Request not found"));
        
        request.setStatus("APPROVED");
        request.setAdminComment(comment);
        return pawnRequestRepository.save(request);
    }
    
    public PawnRequest rejectRequest(Long id, String comment) {
        PawnRequest request = pawnRequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Request not found"));
        
        request.setStatus("REJECTED");
        request.setAdminComment(comment);
        return pawnRequestRepository.save(request);
    }
} 