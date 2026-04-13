package web.steps;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {

    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }

    private static void initDriver() {
        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();

        String headless = System.getProperty("webdriver.chrome.headless", "false");
        if ("true".equalsIgnoreCase(headless)) {
            options.addArguments("--headless");
            System.out.println("Running Firefox in headless mode");
        }

        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        System.out.println("FirefoxDriver initialized successfully");
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            System.out.println("FirefoxDriver closed");
        }
    }
}