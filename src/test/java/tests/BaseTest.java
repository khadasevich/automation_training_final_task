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
        driverManager = new DriverManager(environment, browser);
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

    public void goThroughLogin(String username, String password) {
        loginPage.openLoginPage();
        loginPage.submitUsername(username);
        loginPage.submitPassword(password);
        driverManager.waitUntilItemWillBeShown(gmailMainPage.getComposeButton());
    }

    public void logoutRemoveAccount() {
        gmailMainPage.openProfileMenu();
        driverManager.waitUntilItemWillBeShown(gmailMainPage.getLogOutButton());
        gmailMainPage.logout();
        driverManager.waitUntilItemWillBeShown(selectAccountPage.getRemoveAccountButton());
        selectAccountPage.removeAccount();
        driverManager.waitUntilItemWillBeShown(loginPage.getUsernameField());
    }

    public void sendEmail(String email, String bodyText) {
        gmailMainPage.openComposeEmailWindow();
        driverManager.waitUntilItemWillBeShown(gmailMainPage.getSendButton());
        gmailMainPage.sendEmail(email, bodyText);
        driverManager.waitUntilItemWillBeShown(gmailMainPage.getEmailSentToast());
        driverManager.waitUntilItemPresents(gmailMainPage.getUndoButton());
    }
}
