package webdriver;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import tools.PropertiesReader;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Waiters {

    private static FluentWait<WebDriver> fluentWait;
    static Properties properties;
    static String path = "src\\test\\resources\\timers.properties";

    public static void setTimeout(WebDriver driver) {
        properties = PropertiesReader.getProperties(path);
        driver.manage().timeouts().implicitlyWait(
                Long.parseLong(properties.getProperty("IMPLICITLY_WAIT")), TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(
                Long.parseLong(properties.getProperty("PAGE_LOAD_TIMEOUT")), TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(
                Long.parseLong(properties.getProperty("SCRIPT_TIMEOUT")), TimeUnit.SECONDS);
    }

    public static void removeTimeout(WebDriver driver) {
        properties = PropertiesReader.getProperties(path);
        driver.manage().timeouts().implicitlyWait(
                Long.parseLong(properties.getProperty("RESET_TIMEOUT")), TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(
                Long.parseLong(properties.getProperty("RESET_TIMEOUT")), TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(
                Long.parseLong(properties.getProperty("RESET_TIMEOUT")), TimeUnit.SECONDS);
    }

    public static void waitUntilItemWillBeShown(WebElement element, WebDriver driver) {
        properties = PropertiesReader.getProperties(path);
        fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(Long.parseLong(properties.getProperty("FLUENT_DURATION"))))
                .pollingEvery(Duration.ofMillis(Long.parseLong(properties.getProperty("POLLING_INTERVAL"))))
                .ignoring(TimeoutException.class);
        fluentWait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitUntilItemPresents(WebElement element, WebDriver driver) {
        properties = PropertiesReader.getProperties(path);
        fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(Long.parseLong(properties.getProperty("FLUENT_DURATION"))))
                .pollingEvery(Duration.ofMillis(Long.parseLong(properties.getProperty("POLLING_INTERVAL"))))
                .ignoring(TimeoutException.class);
        fluentWait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static void waitUntilItemIsClickable(WebElement element, WebDriver driver) {
        properties = PropertiesReader.getProperties(path);
        fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(Long.parseLong(properties.getProperty("FLUENT_DURATION"))))
                .pollingEvery(Duration.ofMillis(Long.parseLong(properties.getProperty("POLLING_INTERVAL"))))
                .ignoring(TimeoutException.class);
        fluentWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitWhileAlertPresent(WebDriver driver) {
        properties = PropertiesReader.getProperties(path);
        WebDriverWait wait = new WebDriverWait(driver,
                Long.parseLong(properties.getProperty("WAIT_CONDITION")));
        try {
            wait.until(ExpectedConditions.alertIsPresent());
        } catch (TimeoutException ignored) {
        }
    }
}
