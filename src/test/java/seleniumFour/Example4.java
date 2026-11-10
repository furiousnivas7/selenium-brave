package seleniumFour;

import com.example.BraveDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

/** Practical 4: TestNG basics (@BeforeMethod, @Test, @AfterMethod). */
public class Example4 {

    WebDriver driver;

    @BeforeMethod
    public void beforeMethod() {
        // Create a new Brave browser (maximized)
        driver = BraveDriver.create();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Launch the website
        driver.get("https://demo.guru99.com/test/newtours/");
    }

    @Test
    public void testMethod1() {
        driver.findElement(By.name("userName")).sendKeys("tutorials");
        driver.findElement(By.name("password")).sendKeys("tutorials");
        driver.findElement(By.name("submit")).click();
        System.out.println("Login Successfully");
    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }
}
