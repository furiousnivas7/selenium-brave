package seleniumEleven;

import com.example.BraveDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

/** Practical 11: a DataProvider runs the same test once per data row. */
public class Example11 {

    private static WebDriver driver;

    @DataProvider(name = "Authentication")
    public static Object[][] credentials() {
        return new Object[][] {
            {"testuser_1", "Test@123"},
            {"testuser_2", "Test@456"}
        };
    }

    // Here we are calling the Data Provider object with its name
    @Test(dataProvider = "Authentication")
    public void test(String sUsername, String sPassword) {
        // Create a new Brave browser (maximized)
        driver = BraveDriver.create();
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Launch the website
            driver.get("https://demo.guru99.com/test/newtours/");

            driver.findElement(By.name("userName")).sendKeys(sUsername);
            driver.findElement(By.name("password")).sendKeys(sPassword);
            driver.findElement(By.name("submit")).click();
            System.out.println("Logged in with: " + sUsername);
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
