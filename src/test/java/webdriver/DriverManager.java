package webdriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static FluentWait<WebDriver> fluentWait;
    private static WebDriverWait wait;

    public static void initWebDriver(String environment, String browser) {
        DriverFactory newDriver = new DriverFactory(browser);
        if (null == driver.get()) {
            try {
                if (environment.equalsIgnoreCase("local")) {
                    driver.set(newDriver.getLocalDriver());
                } else if (environment.equalsIgnoreCase("grid")) {
                    driver.set(newDriver.getRemoteGrid());
                } else if (environment.equalsIgnoreCase("sauce")) {
                    driver.set(newDriver.getSauceDriver());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static WebDriver getWebDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        driver.get().quit();
        driver.set(null);
    }

    public static void setTimeout() {
        driver.get().manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        driver.get().manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.get().manage().timeouts().setScriptTimeout(40, TimeUnit.SECONDS);
    }

    public static void removeTimeout() {
        driver.get().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        driver.get().manage().timeouts().pageLoadTimeout(0, TimeUnit.SECONDS);
        driver.get().manage().timeouts().setScriptTimeout(0, TimeUnit.SECONDS);
    }

    public static void maximizeWindow() {
        driver.get().manage().window().maximize();
    }

    public static void waitUntilItemWillBeShown(WebElement element) {
        fluentWait = new FluentWait<>(driver.get())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500)).ignoring(TimeoutException.class);
        fluentWait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitUntilItemPresents(WebElement element) {
        fluentWait = new FluentWait<>(driver.get())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500)).ignoring(TimeoutException.class);
        fluentWait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static void waitUntilItemIsClickable(WebElement element) {
        fluentWait = new FluentWait<>(driver.get())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500)).ignoring(TimeoutException.class);
        fluentWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitWhileAlertPresent() {
        wait = new WebDriverWait(driver.get(), 2);
        try {
            wait.until(ExpectedConditions.alertIsPresent());
        } catch (TimeoutException ignored) {
        }
    }

    @Attachment
    public static byte[] takeScreenshot() {
        TakesScreenshot screenshot = ((TakesScreenshot) driver.get());
        return screenshot.getScreenshotAs(OutputType.BYTES);
    }

    @Attachment
    public static String getBrowserVersion() {
        Capabilities caps = ((RemoteWebDriver) driver.get()).getCapabilities();
        String browserName = caps.getBrowserName();
        String browserVersion = caps.getVersion();
        return browserName + " " + browserVersion;
    }
}