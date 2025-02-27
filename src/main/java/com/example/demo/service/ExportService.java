package com.example.demo.service;

import com.example.demo.model.PawnRequest;
import com.example.demo.repository.PawnRequestRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ExportService {
    
    @Autowired
    private PawnRequestRepository pawnRequestRepository;
    
    public void exportPawnRequestsToCsv(Writer writer) throws IOException {
        List<PawnRequest> requests = pawnRequestRepository.findAll();
        
        CSVPrinter csvPrinter = new CSVPrinter(writer, 
            CSVFormat.DEFAULT.withHeader("ID", "Item Name", "Description", "Expected Price", 
                          "Selling Price", "Status", "Created At", "Admin Comment", "Sold")
        );
        
        for (PawnRequest request : requests) {
            csvPrinter.printRecord(
                request.getId(),
                request.getItemName(),
                request.getDescription(),
                request.getExpectedPrice(),
                request.getSellingPrice(),
                request.getStatus(),
                request.getCreatedAt(),
                request.getAdminComment(),
                request.getSold()
            );
        }
        
        csvPrinter.flush();
        csvPrinter.close();
    }
} 