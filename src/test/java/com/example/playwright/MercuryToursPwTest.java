package com.example.playwright;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.SelectOption;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Path;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/** Playwright version of MercuryToursTest. */
public class MercuryToursPwTest extends PlaywrightBaseTest {

    private static final String HOME_URL =
        "https://demo.guru99.com/test/newtours/";
    private static final String FLIGHTS_URL =
        "https://demo.guru99.com/test/newtours/reservation.php";

    @Test(description = "Log in with the demo mercury/mercury account")
    public void loginWithValidCredentials() {
        page.navigate(HOME_URL);

        page.locator("[name=userName]").fill("mercury");
        page.locator("[name=password]").fill("mercury");
        page.locator("[name=submit]").click();

        try {
            page.waitForCondition(() ->
                page.url().contains("login_sucess")
                || page.getByText("SIGN-OFF").count() > 0,
                new Page.WaitForConditionOptions().setTimeout(10_000));
        } catch (PlaywrightException e) {
            Assert.fail("Login not verified. URL: " + page.url()
                + ", title: " + page.title());
        }
    }

    @Test(description = "Fill in a one-way business class flight")
    public void fillFlightForm() {
        // Whenever the offer popup shows up, close it and carry on.
        // No fixed wait: Playwright only runs this if the popup blocks a step.
        page.addLocatorHandler(page.locator(".cb-box__inner-bg"),
            popup -> {
                System.out.println("Offer popup closed");
                popup.locator(".cb-close").click();
            });

        page.navigate(FLIGHTS_URL);

        Locator oneWay = page.locator("input[name=tripType][value=oneway]");
        Locator business = page.locator("input[name=servClass][value=Business]");

        oneWay.check();
        selectByLabel("passCount", "2");
        selectByLabel("fromPort", "London");
        page.locator("[name=fromMonth]").selectOption(new SelectOption().setIndex(5));
        selectByLabel("fromDay", "15");
        selectByLabel("toPort", "Frankfurt");
        page.locator("[name=toMonth]").selectOption(new SelectOption().setIndex(6));
        selectByLabel("toDay", "20");
        business.check();
        selectByLabel("airline", "Unified Airlines");

        assertThat(oneWay).isChecked();
        assertThat(business).isChecked();
        Assert.assertEquals(selectedText("passCount"), "2");
        Assert.assertEquals(selectedText("fromPort"), "London");
        Assert.assertEquals(selectedText("fromDay"), "15");
        Assert.assertEquals(selectedText("toPort"), "Frankfurt");
        Assert.assertEquals(selectedText("toDay"), "20");
        Assert.assertEquals(selectedText("airline"), "Unified Airlines");

        System.out.println("Departure: " + selectedText("fromMonth")
            + " " + selectedText("fromDay"));
        System.out.println("Return: " + selectedText("toMonth")
            + " " + selectedText("toDay"));

        page.screenshot(new Page.ScreenshotOptions()
            .setPath(Path.of("mercury-flight-form-playwright.png")));
    }

    private void selectByLabel(String name, String label) {
        page.locator("[name=" + name + "]")
            .selectOption(new SelectOption().setLabel(label));
    }

    private String selectedText(String name) {
        return ((String) page.locator("[name=" + name + "]")
            .evaluate("s => s.options[s.selectedIndex].text")).trim();
    }
}
