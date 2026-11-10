package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

/** Smoke test: Brave starts, fills a form and submits it. */
public class BraveTest extends BaseTest {

    @Test(description = "Submit the Selenium practice web form")
    public void submitWebForm() {
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.name("my-text"))).sendKeys("Selenium");
        driver.findElement(By.cssSelector("button")).click();

        String result = wait.until(ExpectedConditions
            .visibilityOfElementLocated(By.id("message"))).getText();
        System.out.println("Result: " + result);

        Assert.assertEquals(result, "Received!");
    }
}
