package tools;

import io.qameta.allure.Attachment;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import webdriver.DriverManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AllureService {

    @Attachment
    public static String attachDateAndTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    @Attachment
    public static String attachOSName() {
        return System.getProperty("os.name");
    }

    @Attachment
    public static byte[] attachScreenshot() {
        TakesScreenshot screenshot = ((TakesScreenshot) DriverManager.getWebDriver());
        return screenshot.getScreenshotAs(OutputType.BYTES);
    }

    @Attachment
    public static String attachBrowserVersion() {
        Capabilities caps = ((RemoteWebDriver) DriverManager.getWebDriver()).getCapabilities();
        String browserName = caps.getBrowserName();
        String browserVersion = caps.getVersion();
        return browserName + " " + browserVersion;
    }
}
