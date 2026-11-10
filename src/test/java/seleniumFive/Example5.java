package seleniumFive;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/** Practical 5: order of @BeforeClass/@BeforeMethod/@AfterMethod/@AfterClass. */
public class Example5 {

    @Test
    public void testMethod1() {
        System.out.println("\nRunning Test -> testMethod1");
    }

    @Test
    public void testMethod2() {
        System.out.println("\nRunning Test -> testMethod2");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("\nThis runs before every method");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("\nThis runs after every method");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("\nThis runs once before class");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("\nThis runs once after class");
    }
}
