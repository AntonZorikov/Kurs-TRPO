package com.example.demo.service;

import com.example.demo.model.PawnRequest;
import com.example.demo.repository.PawnRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ExportServiceTest {

    @Mock
    private PawnRequestRepository pawnRequestRepository;

    @InjectMocks
    private ExportService exportService;

    @Test
    void testExportPawnRequestsToCsv() throws Exception {
        PawnRequest request1 = new PawnRequest();
        request1.setId(1L);
        request1.setItemName("Телефон");
        request1.setDescription("iPhone 12");
        request1.setExpectedPrice(50000.0);
        request1.setStatus("NEW");
        request1.setCreatedAt(LocalDateTime.now());
        request1.setAdminComment("Тестовый комментарий");
        request1.setSold(false);

        PawnRequest request2 = new PawnRequest();
        request2.setId(2L);
        request2.setItemName("Ноутбук");
        request2.setDescription("MacBook Pro");
        request2.setExpectedPrice(100000.0);
        request2.setStatus("APPROVED");
        request2.setCreatedAt(LocalDateTime.now());
        request2.setAdminComment("Второй комментарий");
        request2.setSold(true);

        List<PawnRequest> mockRequests = Arrays.asList(request1, request2);

        when(pawnRequestRepository.findAll()).thenReturn(mockRequests);

        StringWriter stringWriter = new StringWriter();
        exportService.exportPawnRequestsToCsv(stringWriter);

        String result = stringWriter.toString();
        assertNotNull(result);
        assertTrue(result.contains("Телефон"));
        assertTrue(result.contains("iPhone 12"));
        assertTrue(result.contains("Ноутбук"));
        assertTrue(result.contains("MacBook Pro"));
        assertTrue(result.contains("NEW"));
        assertTrue(result.contains("APPROVED"));
    }
} 