package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class MercuryToursTest extends BaseTest {

    private static final String HOME_URL =
        "https://demo.guru99.com/test/newtours/";
    private static final String FLIGHTS_URL =
        "https://demo.guru99.com/test/newtours/reservation.php";
    private static final By POPUP = By.cssSelector(".cb-box__inner-bg");

    @Test(description = "Log in with the demo mercury/mercury account")
    public void loginWithValidCredentials() {
        driver.get(HOME_URL);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.name("userName"))).sendKeys("mercury");
        driver.findElement(By.name("password")).sendKeys("mercury");
        driver.findElement(By.name("submit")).click();

        try {
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(d ->
                d.getCurrentUrl().contains("login_sucess")
                || !d.findElements(By.linkText("SIGN-OFF")).isEmpty());
        } catch (TimeoutException e) {
            Assert.fail("Login not verified. URL: " + driver.getCurrentUrl()
                + ", title: " + driver.getTitle());
        }
    }

    // The flight form can be opened without logging in, so this test
    // does not depend on loginWithValidCredentials.
    @Test(description = "Fill in a one-way business class flight")
    public void fillFlightForm() {
        driver.get(FLIGHTS_URL);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.name("tripType")));

        WebElement oneWay = driver.findElement(
            By.cssSelector("input[name='tripType'][value='oneway']"));
        oneWay.click();

        Select passengers = select("passCount");
        passengers.selectByVisibleText("2");

        Select departure = select("fromPort");
        departure.selectByVisibleText("London");

        Select fromMonth = select("fromMonth");
        fromMonth.selectByIndex(5);

        Select fromDay = select("fromDay");
        fromDay.selectByVisibleText("15");

        Select arrival = select("toPort");
        arrival.selectByVisibleText("Frankfurt");

        Select toMonth = select("toMonth");
        toMonth.selectByIndex(6);

        Select toDay = select("toDay");
        toDay.selectByVisibleText("20");

        closeOfferPopupIfShown();

        WebElement businessClass = wait.until(
            ExpectedConditions.elementToBeClickable(By.cssSelector(
                "input[name='servClass'][value='Business']")));
        businessClass.click();

        Select airline = select("airline");
        airline.selectByVisibleText("Unified Airlines");

        Assert.assertTrue(oneWay.isSelected(), "One Way not selected");
        Assert.assertTrue(businessClass.isSelected(),
            "Business class not selected");
        Assert.assertEquals(selected(passengers), "2");
        Assert.assertEquals(selected(departure), "London");
        Assert.assertEquals(selected(fromDay), "15");
        Assert.assertEquals(selected(arrival), "Frankfurt");
        Assert.assertEquals(selected(toDay), "20");
        Assert.assertEquals(selected(airline), "Unified Airlines");

        System.out.println("Departure: " + selected(fromMonth)
            + " " + selected(fromDay));
        System.out.println("Return: " + selected(toMonth)
            + " " + selected(toDay));

        saveScreenshot("mercury-flight-form.png");
    }

    private Select select(String name) {
        return new Select(driver.findElement(By.name(name)));
    }

    private static String selected(Select select) {
        return select.getFirstSelectedOption().getText().trim();
    }

    private void closeOfferPopupIfShown() {
        WebDriverWait popupWait =
            new WebDriverWait(driver, Duration.ofSeconds(8));
        try {
            WebElement popup = popupWait.until(
                ExpectedConditions.visibilityOfElementLocated(POPUP));
            popup.findElement(By.cssSelector(".cb-close")).click();
            popupWait.until(
                ExpectedConditions.invisibilityOfElementLocated(POPUP));
            System.out.println("Offer popup closed");
        } catch (TimeoutException e) {
            System.out.println("No offer popup, continuing");
        }
    }
}
