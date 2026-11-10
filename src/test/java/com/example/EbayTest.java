package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.util.List;

public class EbayTest extends BaseTest {

    private static final String TITLE_CSS = ".s-card__title, .s-item__title";
    private static final String PRICE_CSS = ".s-card__price, .s-item__price";

    @Test(description = "Searching eBay for a Dell laptop returns products")
    public void searchReturnsProducts() {
        driver.get("https://www.ebay.com/");

        // eBay serves an error page when it flags the browser as automated
        // (always in headless mode, and after many runs in a short time)
        if (driver.getTitle().contains("Error Page")) {
            throw new SkipException(
                "eBay blocked this automated browser; try again later");
        }

        WebElement searchBox = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.name("_nkw")));
        searchBox.sendKeys("Dell Laptop");
        searchBox.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(TITLE_CSS)));

        System.out.println("URL: " + driver.getCurrentUrl());

        List<WebElement> products =
            driver.findElements(By.cssSelector("li.s-card, li.s-item"));

        int count = 0;
        for (WebElement product : products) {
            List<WebElement> titles =
                product.findElements(By.cssSelector(TITLE_CSS));
            if (titles.isEmpty()) {
                continue;
            }

            String name = titles.get(0).getText();
            if (name.isBlank()) {
                continue;
            }

            List<WebElement> prices =
                product.findElements(By.cssSelector(PRICE_CSS));
            String price = prices.isEmpty()
                ? "Price unavailable"
                : prices.get(0).getText();

            count++;
            System.out.println(count + ". " + name);
            System.out.println("   Price: " + price);

            if (count == 5) {
                break;
            }
        }

        Assert.assertTrue(count > 0, "No product results found.");
    }
}
