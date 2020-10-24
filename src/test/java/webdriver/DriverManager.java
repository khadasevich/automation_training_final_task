package webdriver;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DriverManager {

    private final String environment;
    private final String browser;
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public DriverManager(String environment, String browser) {
        this.environment = environment;
        this.browser = browser;
    }

    public WebDriver getWebDriver() {
        DriverFactory newDriver = new DriverFactory(browser);
        if (null == driver.get()) {
            try {
                if (environment.equalsIgnoreCase("local")) {
                    driver.set(newDriver.getDriver());
                } else if (environment.equalsIgnoreCase("grid")) {
                    driver.set(newDriver.getDriverGrid());
                } else {
                    driver.set(newDriver.getSauceDriverGrid());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return driver.get();
    }

    public static void quitDriver() {
        driver.get().close();
        driver.get().quit();
    }

    public static void setTimeout() {
        driver.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get().manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.get().manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
    }

    public static void removeTimeout() {
        driver.get().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        driver.get().manage().timeouts().pageLoadTimeout(0, TimeUnit.SECONDS);
        driver.get().manage().timeouts().setScriptTimeout(0, TimeUnit.SECONDS);
    }

    public static void maximizeWindow() {
        driver.get().manage().window().maximize();
    }

    public static void takeScreenshot(String pathToFile) {
        TakesScreenshot screenshot = ((TakesScreenshot) driver.get());
        File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(pathToFile);
        try {
            FileUtils.copyFile(sourceFile, destinationFile);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
