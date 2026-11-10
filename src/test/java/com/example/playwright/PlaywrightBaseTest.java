package com.example.playwright;

import com.example.BravePath;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.Tracing;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.nio.file.Path;

/**
 * Opens Brave through Playwright before every test and closes it after.
 * On failure it saves a screenshot and a trace you can replay.
 * Run headless with: mvn test -Dsuite=testng-playwright.xml -Dheadless=true
 */
public abstract class PlaywrightBaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeMethod
    public void setUp() {
        playwright = Playwright.create();

        // Brave is Chromium-based, so Playwright's chromium() can drive it
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
            .setExecutablePath(Path.of(BravePath.find()))
            .setHeadless(Boolean.getBoolean("headless")));

        context = browser.newContext(new Browser.NewContextOptions()
            .setViewportSize(1440, 900));

        // Record everything; the trace is only kept if the test fails
        context.tracing().start(new Tracing.StartOptions()
            .setScreenshots(true)
            .setSnapshots(true));

        page = context.newPage();
        page.setDefaultTimeout(20_000);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (playwright == null) {
            return;
        }
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                String name = getClass().getSimpleName() + "-" + result.getName();

                page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Path.of("screenshots", name + ".png"))
                    .setFullPage(true));

                Path trace = Path.of("traces", name + ".zip");
                context.tracing().stop(new Tracing.StopOptions().setPath(trace));
                System.out.println("Trace saved: " + trace
                    + " (open it at https://trace.playwright.dev)");
            } else {
                context.tracing().stop();
            }
        } catch (PlaywrightException e) {
            System.out.println("Could not save failure details: " + e.getMessage());
        } finally {
            // Closes the page, context and browser too
            playwright.close();
            playwright = null;
        }
    }
}
