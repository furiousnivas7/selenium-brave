package com.example.playwright;

import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

/** Playwright version of EbayTest. */
public class EbayPwTest extends PlaywrightBaseTest {

    private static final String TITLE_CSS = ".s-card__title, .s-item__title";
    private static final String PRICE_CSS = ".s-card__price, .s-item__price";

    @Test(description = "Searching eBay for a Dell laptop returns products")
    public void searchReturnsProducts() {
        page.navigate("https://www.ebay.com/");

        // eBay serves an error page when it flags the browser as automated
        if (page.title().contains("Error Page")) {
            throw new SkipException(
                "eBay blocked this automated browser; try again later");
        }

        Locator searchBox = page.locator("[name=_nkw]");
        searchBox.fill("Dell Laptop");
        searchBox.press("Enter");

        // Wait for the first product title to appear
        page.locator(TITLE_CSS).first().waitFor();
        System.out.println("URL: " + page.url());

        int count = 0;
        for (Locator product : page.locator("li.s-card, li.s-item").all()) {
            Locator title = product.locator(TITLE_CSS);
            if (title.count() == 0) {
                continue;
            }

            String name = title.first().innerText().trim();
            // Skip blank titles and eBay's hidden "Shop on eBay" placeholder cards
            if (name.isBlank() || name.equals("Shop on eBay")) {
                continue;
            }

            Locator price = product.locator(PRICE_CSS);
            String priceText = price.count() == 0
                ? "Price unavailable"
                : price.first().innerText().trim();

            count++;
            System.out.println(count + ". " + name);
            System.out.println("   Price: " + priceText);

            if (count == 5) {
                break;
            }
        }

        Assert.assertTrue(count > 0, "No product results found.");
    }
}
