package com.example.demo.config;

import com.example.demo.model.PawnRequest;
import com.example.demo.model.User;
import com.example.demo.service.PawnRequestService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class TestDataInitializer implements CommandLineRunner {

    private final PawnRequestService pawnRequestService;
    private final UserService userService;

    @Autowired
    public TestDataInitializer(PawnRequestService pawnRequestService, UserService userService) {
        this.pawnRequestService = pawnRequestService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        // Получаем пользователя для привязки заявок
        User user = userService.findByUsername("user");
        if (user == null) {
            return;
        }

        // Создаем тестовые данные только если в базе нет заявок
        if (pawnRequestService.getApprovedRequests().isEmpty()) {
            List<PawnRequest> testRequests = Arrays.asList(
                createRequest(user, "iPhone 13 Pro", "Смартфон в отличном состоянии, полный комплект", 
                            50000.0, "iphone13.jpg"),
                            
                createRequest(user, "MacBook Pro 2021", "Ноутбук, 16GB RAM, 512GB SSD", 
                            120000.0, "macbook.jpg"),
                            
                createRequest(user, "PlayStation 5", "Игровая приставка с двумя геймпадами", 
                            45000.0, "ps5.jpg"),
                            
                createRequest(user, "Canon EOS R5", "Профессиональная камера с объективом 24-70mm", 
                            180000.0, "canon.jpg"),
                            
                createRequest(user, "iPad Pro 12.9", "Планшет 256GB с Apple Pencil", 
                            75000.0, "ipad.jpg"),
                            
                createRequest(user, "Rolex Submariner", "Часы в идеальном состоянии, коробка и документы", 
                            850000.0, "rolex.jpg"),
                            
                createRequest(user, "MacBook Air M1", "Ноутбук в отличном состоянии", 
                            80000.0, "macbookair.jpg"),
                            
                createRequest(user, "Samsung Galaxy S21", "Смартфон с чехлом и защитным стеклом", 
                            35000.0, "samsung.jpg")
            );

            // Сохраняем все тестовые заявки
            for (PawnRequest request : testRequests) {
                try {
                    pawnRequestService.createRequest(request);
                    // Сразу одобряем заявку
                    pawnRequestService.updateRequestStatus(request.getId(), "APPROVED", "Одобрено автоматически");
                } catch (Exception e) {
                    System.out.println("Ошибка при создании тестовой заявки: " + e.getMessage());
                }
            }
        }
    }

    private PawnRequest createRequest(User user, String itemName, String description, 
                                    Double expectedPrice, String imageUrl) {
        PawnRequest request = new PawnRequest();
        request.setUser(user);
        request.setItemName(itemName);
        request.setDescription(description);
        request.setExpectedPrice(expectedPrice);
        request.setImageUrl(imageUrl);
        request.setCreatedAt(LocalDateTime.now());
        request.setStatus("PENDING");
        request.setSold(false);
        request.setSellingPrice(expectedPrice * 1.2); // 120% от заявленной стоимости
        return request;
    }
} 