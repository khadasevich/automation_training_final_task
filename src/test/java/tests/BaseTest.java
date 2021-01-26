package tests;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.GmailMainPage;
import pages.LoginPage;
import pages.SelectAccountPage;
import webdriver.DriverManager;
import webdriver.Waiters;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

public class BaseTest {

    LoginPage loginPage;
    GmailMainPage gmailMainPage;
    SelectAccountPage selectAccountPage;
    String filename = "src\\test\\resources\\test_data";
    Gson gson = new Gson();
    String generateFakeMessage;
    String generateFakeSubject;
    public WebDriver driver;
    public DriverManager driverManager;

    @BeforeClass
    @Parameters({"browser", "environment"})
    protected void setUp(@Optional("chrome") String browser, @Optional("local") String environment) {
        driverManager = new DriverManager(environment, browser);
        driver = DriverManager.getWebDriver();
        loginPage = new LoginPage(driver);
        gmailMainPage = new GmailMainPage(driver);
        selectAccountPage = new SelectAccountPage(driver);
        driverManager.maximizeWindow();
        Waiters.setTimeout(driver);
    }

    @AfterClass
    public void tearDown() {
        driverManager.quitDriver();
    }

    @DataProvider(name = "credentials")
    public Object[][] getJSON(ITestContext context) throws FileNotFoundException {
        List<Map> users = gson.fromJson(new JsonReader(new FileReader(filename)), List.class);
        Object[][] objects = users.stream()
                .map(testData ->
                        testData.values().toArray()).toArray(Object[][]::new);
        return objects;
    }

    public static String generateFakeEmailBody() {
        Faker faker = new Faker();
        return faker.chuckNorris().fact();
    }

    public static String generateFakeEmailSubject() {
        Faker faker = new Faker();
        return faker.dragonBall().character();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        if (methodName != "loginTest" & methodName != "logoutTest") {
            signOut();
        }
    }

    public void signIn(String username, String password) {
        loginPage.openLoginPage();
        Waiters.waitUntilItemWillBeShown(loginPage.getUsernameInputField(), driver);
        loginPage.submitUsername(username);
        Waiters.waitUntilItemWillBeShown(loginPage.getPasswordInputField(), driver);
        loginPage.submitPassword(password);
        Waiters.waitUntilItemIsClickable(gmailMainPage.getComposeEmailButton(), driver);
    }

    public void signOut() {
        Waiters.waitUntilItemIsClickable(gmailMainPage.getUserNicknameButton(), driver);
        gmailMainPage.openProfileMenu();
        Waiters.waitUntilItemIsClickable(gmailMainPage.getLogOutButton(), driver);
        Waiters.waitWhileAlertPresent(driver);
        gmailMainPage.gmailLogout();
        Waiters.waitUntilItemIsClickable(selectAccountPage.getRemoveAccountButton(), driver);
        selectAccountPage.removeAccount();
        Waiters.waitUntilItemWillBeShown(loginPage.getUsernameInputField(), driver);
    }
}
