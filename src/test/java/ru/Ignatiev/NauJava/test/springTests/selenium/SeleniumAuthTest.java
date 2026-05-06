package ru.Ignatiev.NauJava.test.springTests.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumAuthTest {

    private static final Logger log = LoggerFactory.getLogger(SeleniumAuthTest.class);
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
        var usernameField = By.name("username");
        var passwordField = By.name("password");
        var loginButton = By.cssSelector("button[type='submit']");

        driver.findElement(usernameField).sendKeys("user");
        driver.findElement(passwordField).sendKeys("user");
        driver.findElement(loginButton).click();
        log.info("Current driver url: {}", driver.getCurrentUrl());
        WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(3));
        wait.until(ExpectedConditions.urlToBe(baseUrl + "/"));

        Assertions.assertEquals(baseUrl + "/", driver.getCurrentUrl());

        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(normalize-space(), 'Выйти')]")
        ));
        logoutButton.click();
        Assertions.assertEquals(baseUrl + "/login", driver.getCurrentUrl());
    }
}

