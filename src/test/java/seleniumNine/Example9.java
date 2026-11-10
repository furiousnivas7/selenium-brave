package seleniumNine;

import com.example.BraveDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

/** Practical 9: Assert.assertTrue stops the test if the check fails. */
public class Example9 {

    private static WebDriver driver;

    @Test
    public void Assertion() {
        // Create a new Brave browser (maximized)
        driver = BraveDriver.create();
        try {
            // Launch the website
            driver.get("https://demo.guru99.com/test/newtours/");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Find the submit button on the page
            WebElement btnSubmit = driver.findElement(By.name("submit"));

            // The test only continues if the submit button is displayed
            Assert.assertTrue(btnSubmit.isDisplayed());

            // Clicked only if the assertion above passed
            btnSubmit.click();
        } finally {
            // Close the browser even if the assertion fails
            driver.quit();
        }
    }
}
