package com.example.playwright;

import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/** Playwright version of BraveTest: fill and submit the practice form. */
public class WebFormPwTest extends PlaywrightBaseTest {

    @Test(description = "Submit the Selenium practice web form with Playwright")
    public void submitWebForm() {
        page.navigate("https://www.selenium.dev/selenium/web/web-form.html");

        page.locator("[name=my-text]").fill("Playwright");
        page.locator("button").click();

        // assertThat retries until the text appears (or times out)
        assertThat(page.locator("#message")).hasText("Received!");
    }
}
