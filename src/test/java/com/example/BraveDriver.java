package com.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Starts a Brave browser (maximized, or headless with -Dheadless=true).
 * Selenium Manager downloads the matching driver, so no chromedriver.exe
 * path is needed.
 */
public final class BraveDriver {

    private BraveDriver() {
    }

    public static WebDriver create() {
        ChromeOptions options = new ChromeOptions();
        options.setBinary(BravePath.find());

        boolean headless = Boolean.getBoolean("headless");
        if (headless) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }

        WebDriver driver = new ChromeDriver(options);

        if (!headless) {
            driver.manage().window().maximize();
        }
        return driver;
    }
}
