package seleniumTwelve;

import com.example.BraveDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * Practical 12: the username and password come from the
 * parameter tags in testng-day02.xml, so run it through that file.
 */
public class Example12 {

    private static WebDriver driver;

    @Test
    @Parameters({"sUsername", "sPassword"})
    public void test(String sUsername, String sPassword) {
        // Create a new Brave browser (maximized)
        driver = BraveDriver.create();
        try {
            // Launch the website
            driver.get("https://demo.guru99.com/test/newtours/");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

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
