package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.GmailMainPage;
import pages.LoginPage;
import pages.SelectAccountPage;
import webdriver.DriverManager;

public class BasicActionsForTest {

    WebDriver driver;
    LoginPage loginPage;
    GmailMainPage gmailMainPage;
    SelectAccountPage selectAccountPage;

    @BeforeClass
    @Parameters({"browser", "environment"})
    protected void setUp(@Optional("firefox") String browser, @Optional("local") String environment) {
        DriverManager.initWebDriver(environment, browser);
        driver = DriverManager.getWebDriver();
        loginPage = new LoginPage(driver);
        gmailMainPage = new GmailMainPage(driver);
        selectAccountPage = new SelectAccountPage(driver);
        DriverManager.maximizeWindow();
        DriverManager.setTimeout();
    }

    @AfterClass
    public void tearDown() {
        DriverManager.quitDriver();
    }

    public void goThroughLogin(String username, String password) {
        loginPage.openLoginPage();
        DriverManager.waitUntilItemWillBeShown(loginPage.getUsernameField());
        loginPage.submitUsername(username);
        DriverManager.waitUntilItemWillBeShown(loginPage.getPasswordField());
        loginPage.submitPassword(password);
        DriverManager.waitUntilItemIsClickable(gmailMainPage.getComposeButton());
    }

    public void removeAccountAfterLogout() {
        DriverManager.waitUntilItemIsClickable(gmailMainPage.getUserIcon());
        gmailMainPage.openProfileMenu();
        DriverManager.waitUntilItemIsClickable(gmailMainPage.getLogOutButton());
        DriverManager.waitWhileAlertPresent();
        gmailMainPage.gmailLogout();
        DriverManager.waitUntilItemIsClickable(selectAccountPage.getRemoveAccount());
        selectAccountPage.removeAccount();
        DriverManager.waitUntilItemWillBeShown(loginPage.getUsernameField());
    }

    public void sendEmail(String email, String bodyText) {
        gmailMainPage.openComposeEmailWindow();
        DriverManager.waitUntilItemIsClickable(gmailMainPage.getUndoButton());
        gmailMainPage.sendEmail(email, bodyText);
        DriverManager.waitUntilItemWillBeShown(gmailMainPage.getEmailSentToast());
        DriverManager.waitUntilItemPresents(gmailMainPage.getUndoButton());
    }
}
