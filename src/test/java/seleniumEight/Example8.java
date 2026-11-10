package seleniumEight;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

/** Practical 8: enabled = false stops Three from running at all. */
public class Example8 {

    public WebDriver driver;

    @Test(priority = 0)
    public void One() {
        System.out.println("This is the Test Case number One");
    }

    @Test(priority = 1)
    public void Two() {
        System.out.println("This is the Test Case number Two");
    }

    @Test(priority = 2, enabled = false)
    public void Three() {
        System.out.println("This is the Test Case number Three");
    }

    @Test(priority = 3)
    public void Four() {
        System.out.println("This is the Test Case number Four");
    }
}
