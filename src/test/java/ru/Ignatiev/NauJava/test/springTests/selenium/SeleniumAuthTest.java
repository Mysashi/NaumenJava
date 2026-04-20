package ru.Ignatiev.NauJava.test.springTests.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SeleniumAuthTest {

    @Autowired
    private WebDriver driver;


    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testFullAuthCycle() {
        int port = 8080;
        String baseUrl = "http://localhost:" + port;

        driver.get(baseUrl + "/login");
        var usernameField = By.id("username");
        var passwordField = By.id("password");
        var loginButton = By.cssSelector("button[type='submit']");

        driver.findElement(usernameField).sendKeys("admin");
        driver.findElement(passwordField).sendKeys("admin");
        driver.findElement(loginButton).click();

        WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(3));
        wait.until(ExpectedConditions.urlContains("/"));

        Assertions.assertEquals(baseUrl + "/", driver.getCurrentUrl());

        driver.get(baseUrl + "/logout");
        Assertions.assertEquals(baseUrl + "/login", driver.getCurrentUrl());
    }
}

