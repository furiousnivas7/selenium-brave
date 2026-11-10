package com.example;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

/**
 * Opens a fresh Brave window before every test and closes it afterwards.
 * Run headless with: mvn test -Dheadless=true
 */
public abstract class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setBinary(BravePath.find());

        boolean headless = Boolean.getBoolean("headless");
        if (headless) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        if (!headless) {
            driver.manage().window().maximize();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driver == null) {
            return;
        }
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                saveScreenshot("screenshots/" + result.getName() + ".png");
            }
        } finally {
            driver.quit();
            driver = null;
        }
    }

    protected void saveScreenshot(String fileName) {
        try {
            Path path = Path.of(fileName);
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.write(path,
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            System.out.println("Screenshot saved: " + path);
        } catch (IOException | WebDriverException e) {
            System.out.println("Could not save screenshot: " + e.getMessage());
        }
    }
}
