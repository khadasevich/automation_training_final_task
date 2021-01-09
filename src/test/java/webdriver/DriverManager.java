package webdriver;

import org.openqa.selenium.*;

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

    public static WebDriver getWebDriver(String environment, String browser) {
        if (null == driver.get()) {
            try {
                initWebDriver(environment, browser);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return driver.get();
    }

    public static void quitDriver() {
        driver.get().quit();
        driver.set(null);
    }

    public static void maximizeWindow() {
        driver.get().manage().window().maximize();
    }
}