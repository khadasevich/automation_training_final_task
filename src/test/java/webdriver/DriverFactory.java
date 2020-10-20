package webdriver;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class DriverFactory {

    private final String browser;
    WebDriver driver;
    File file = new File("src\\test\\resources\\saucelabcreds.properties");

    public DriverFactory(String browser) {
        this.browser = browser.toLowerCase();
    }

    public WebDriver getDriver() {
        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                driver = new ChromeDriver();
                break;

            case "firefox":
                System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
                driver = new FirefoxDriver();
                break;
        }
        return driver;
    }

    public WebDriver getDriverGrid() {
        String hubUrl = "http://192.168.0.2:4444/wd/hub";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        switch (browser) {
            case "chrome":
                capabilities.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
                break;
            case "firefox":
                capabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
                break;
        }
        try {
            driver = new RemoteWebDriver(new URL(hubUrl), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }

    public WebDriver getSauceDriverGrid() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String URL = "https://" + properties.getProperty("USERNAME") + ":" + properties.getProperty("ACCESS_KEY") +
                "@ondemand.saucelabs.com:443/wd/hub";
        MutableCapabilities sauceOptions = new MutableCapabilities();
        DesiredCapabilities browserOptions = new DesiredCapabilities();
        switch (browser) {
            case "chrome":
                browserOptions.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
                browserOptions.setCapability("platformName", "Windows 10");
                browserOptions.setCapability("browserVersion", "latest");
                browserOptions.setCapability("sauce:options", sauceOptions);
                break;
            case "firefox":
                browserOptions.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
                browserOptions.setCapability("platformName", "Windows 10");
                browserOptions.setCapability("browserVersion", "latest");
                browserOptions.setCapability("sauce:options", sauceOptions);
                break;
        }
        try {
            driver = new RemoteWebDriver(new URL(URL), browserOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }
}
