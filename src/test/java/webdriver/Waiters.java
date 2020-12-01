package webdriver;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Waiters {

    private static FluentWait<WebDriver> fluentWait;
    private static WebDriverWait wait;

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
