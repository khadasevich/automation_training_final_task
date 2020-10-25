package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import pages.GmailMainPage;
import pages.LoginPage;
import pages.SelectAccountPage;
import webdriver.DriverManager;

public class BaseTest {

    WebDriver driver;
    DriverManager driverManager;
    LoginPage loginPage;
    GmailMainPage gmailMainPage;
    SelectAccountPage selectAccountPage;

    public DriverManager getDriver() {
        return driverManager;
    }

    @BeforeClass
    @Parameters({"browser", "environment"})
    protected void setUp(@Optional("chrome") String browser, @Optional("local") String environment) {
        driverManager = new DriverManager("local", "chrome");
        driver = driverManager.getWebDriver();
        loginPage = new LoginPage(driver);
        gmailMainPage = new GmailMainPage(driver);
        selectAccountPage = new SelectAccountPage(driver);
        driverManager.maximizeWindow();
        driverManager.setTimeout();
    }

    @AfterClass
    public void tearDown() {
        driverManager.quitDriver();
    }
}
