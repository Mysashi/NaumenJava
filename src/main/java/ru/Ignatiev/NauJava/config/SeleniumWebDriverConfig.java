package ru.Ignatiev.NauJava.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class SeleniumWebDriverConfig {

    @Bean
    public WebDriver webDriver() {
        String driverPath = new File("msedgedriver.exe").getAbsolutePath();
        System.setProperty("webdriver.edge.driver", driverPath);
        EdgeOptions options = new EdgeOptions();
        return new EdgeDriver(options);
    }
}