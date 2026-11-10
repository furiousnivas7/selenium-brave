package seleniumSix;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

/**
 * Practical 6: without priority, TestNG runs tests in alphabetical
 * order of method name: Four, One, Three, Two.
 */
public class Example6 {

    public WebDriver driver;

    @Test
    public void One() {
        System.out.println("This is the Test Case number One");
    }

    @Test
    public void Two() {
        System.out.println("This is the Test Case number Two");
    }

    @Test
    public void Three() {
        System.out.println("This is the Test Case number Three");
    }

    @Test
    public void Four() {
        System.out.println("This is the Test Case number Four");
    }
}
