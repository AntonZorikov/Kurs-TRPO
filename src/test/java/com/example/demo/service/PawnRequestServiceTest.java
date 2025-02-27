package com.example.demo.service;

import com.example.demo.model.PawnRequest;
import com.example.demo.repository.PawnRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PawnRequestServiceTest {

    @Mock
    private PawnRequestRepository pawnRequestRepository;

    @InjectMocks
    private PawnRequestService pawnRequestService;

    @Test
    void testGetPendingRequests() {
        PawnRequest request1 = new PawnRequest();
        request1.setId(1L);
        request1.setItemName("Телефон");
        request1.setStatus("NEW");
        request1.setCreatedAt(LocalDateTime.now());

        PawnRequest request2 = new PawnRequest();
        request2.setId(2L);
        request2.setItemName("Ноутбук");
        request2.setStatus("NEW");
        request2.setCreatedAt(LocalDateTime.now());

        List<PawnRequest> mockRequests = Arrays.asList(request1, request2);

        when(pawnRequestRepository.findByStatus("NEW")).thenReturn(mockRequests);

        List<PawnRequest> result = pawnRequestService.getPendingRequests();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("NEW", result.get(0).getStatus());
        assertEquals("Телефон", result.get(0).getItemName());
    }
} 