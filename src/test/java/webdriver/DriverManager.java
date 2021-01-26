package webdriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import tools.PropertiesReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static String browser = null;
    private static String environment = null;
    static Properties properties;
    final static ChromeOptions chromeOptions = new ChromeOptions();
    final static FirefoxOptions firefoxOptions = new FirefoxOptions();
    String path = "src\\test\\resources\\driver.properties";

    public DriverManager(String environment, String browser) {
        DriverManager.browser = browser.toLowerCase();
        DriverManager.environment = environment;
        properties = PropertiesReader.getProperties(path);
    }

    public static void initWebDriver() {
        if (null == driver.get()) {
            try {
                if (environment.equalsIgnoreCase("local")) {
                    getLocalDriver();
                } else if (environment.equalsIgnoreCase("grid")) {
                    getRemoteGrid();
                } else if (environment.equalsIgnoreCase("sauce")) {
                    getSauceDriver();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static WebDriver getWebDriver() {
        if (null == driver.get()) {
            try {
                initWebDriver();
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

    public void maximizeWindow() {
        driver.get().manage().window().maximize();
    }

    public static void getLocalDriver() {
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver.set(new ChromeDriver(chromeOptions));
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver.set(new FirefoxDriver(firefoxOptions));
                break;
        }
    }

    public static void getRemoteGrid() {
        String hubUrl = properties.getProperty("HUBURL");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        switch (browser) {
            case "chrome":
                capabilities.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
                capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                break;
            case "firefox":
                capabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
                capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
                break;
        }
        try {
            driver.set(new RemoteWebDriver(new URL(hubUrl), capabilities));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void getSauceDriver() {
        String URL = "https://" + properties.getProperty("USERNAME") + ":" + properties.getProperty("ACCESS_KEY") +
                "@ondemand.saucelabs.com:443/wd/hub";
        MutableCapabilities sauceOptions = new MutableCapabilities();
        DesiredCapabilities browserOptions = new DesiredCapabilities();
        switch (browser) {
            case "chrome":
                browserOptions.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
                browserOptions.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                browserOptions.setCapability("platformName", properties.getProperty("PLATFORM"));
                browserOptions.setCapability("browserVersion", properties.getProperty("BROWSER"));
                browserOptions.setCapability("sauce:options", sauceOptions);
                break;
            case "firefox":
                browserOptions.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
                browserOptions.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
                browserOptions.setCapability("platformName", properties.getProperty("PLATFORM"));
                browserOptions.setCapability("browserVersion", properties.getProperty("BROWSER"));
                browserOptions.setCapability("sauce:options", sauceOptions);
                break;
        }
        try {
            driver.set(new RemoteWebDriver(new URL(URL), browserOptions));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}