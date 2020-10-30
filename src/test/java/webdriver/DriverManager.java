package webdriver;

import io.qameta.allure.Attachment;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class DriverManager {

    private final String environment;
    private final String browser;
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static FluentWait<WebDriver> fluentWait;

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
                } else if (environment.equalsIgnoreCase("sauce")) {
                    driver.set(newDriver.getSauceDriverGrid());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return driver.get();
    }

    public void quitDriver() {
        driver.get().quit();
        driver.set(null);
    }

    public void setTimeout() {
        driver.get().manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        driver.get().manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.get().manage().timeouts().setScriptTimeout(40, TimeUnit.SECONDS);
    }

    public void removeTimeout() {
        driver.get().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        driver.get().manage().timeouts().pageLoadTimeout(0, TimeUnit.SECONDS);
        driver.get().manage().timeouts().setScriptTimeout(0, TimeUnit.SECONDS);
    }

    public void maximizeWindow() {
        driver.get().manage().window().maximize();
    }

    public void waitUntilItemWillBeShown (WebElement element) {
        fluentWait = new FluentWait<>(driver.get())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500)).ignoring(TimeoutException.class);
        fluentWait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitUntilItemPresents (WebElement element) {
        fluentWait = new FluentWait<>(driver.get())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500)).ignoring(TimeoutException.class);
        fluentWait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitUntilIsClickable (WebElement element) {
        fluentWait = new FluentWait<>(driver.get())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500)).ignoring(TimeoutException.class);
        fluentWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void hoverElement(WebElement element) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver.get();
        String strJavaScript = "var element = arguments[0];"
                + "var mouseEventObj = document.createEvent('MouseEvents');"
                + "mouseEventObj.initEvent( 'mouseover', true, true );"
                + "element.dispatchEvent(mouseEventObj);";
        javascriptExecutor.executeScript(strJavaScript, element);
    }

    public void waitWhileAlertPresent(){
        WebDriverWait wait = new WebDriverWait(driver.get(), 2);
        try {
            wait.until(ExpectedConditions.alertIsPresent());
        } catch (TimeoutException ignored) {
        }
    }

    @Attachment
    public byte[] takeScreenshot() {
        TakesScreenshot screenshot = ((TakesScreenshot) driver.get());
        return screenshot.getScreenshotAs(OutputType.BYTES);
    }

    @Attachment
    public String browserVersion() {
        Capabilities caps = ((RemoteWebDriver) driver.get()).getCapabilities();
        String browserName = caps.getBrowserName();
        String browserVersion = caps.getVersion();
        return browserName+" "+browserVersion;
    }
}