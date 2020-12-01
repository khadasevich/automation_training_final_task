package webdriver;

import io.qameta.allure.Attachment;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.concurrent.TimeUnit;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

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