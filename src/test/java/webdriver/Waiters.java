package webdriver;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Waiters {

    private static FluentWait<WebDriver> fluentWait;
    private static WebDriverWait wait;

    public static void setTimeout(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(40, TimeUnit.SECONDS);
    }

    public static void removeTimeout(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(0, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(0, TimeUnit.SECONDS);
    }

    public static void waitUntilItemWillBeShown(WebElement element, WebDriver driver) {
        fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500)).ignoring(TimeoutException.class);
        fluentWait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitUntilItemPresents(WebElement element, WebDriver driver) {
        fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500)).ignoring(TimeoutException.class);
        fluentWait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static void waitUntilItemIsClickable(WebElement element, WebDriver driver) {
        fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500)).ignoring(TimeoutException.class);
        fluentWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitWhileAlertPresent(WebDriver driver) {
        wait = new WebDriverWait(driver, 2);
        try {
            wait.until(ExpectedConditions.alertIsPresent());
        } catch (TimeoutException ignored) {
        }
    }
}
