package com.example.demo.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MainPageTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private WebDriverWait wait;
    private String baseUrl;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testMainPageLoads() {
        // Открываем главную страницу
        driver.get(baseUrl);
        
        // Выводим HTML страницы для отладки
        System.out.println("Page source: " + driver.getPageSource());

        // Проверяем, что страница загрузилась (наличие body)
        WebElement body = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        assertTrue(body.isDisplayed());

        // Проверяем наличие хотя бы одной ссылки на странице
        assertTrue(driver.findElements(By.tagName("a")).size() > 0);
    }

    @Test
    void testNavigationLinks() {
        driver.get(baseUrl);

        // Находим все ссылки на странице
        driver.findElements(By.tagName("a")).forEach(link -> {
            String href = link.getAttribute("href");
            if (href != null && !href.isEmpty()) {
                System.out.println("Found link: " + href);
            }
        });

        // Проверяем, что есть хотя бы одна ссылка
        assertTrue(driver.findElements(By.tagName("a")).size() > 0);
    }
} 