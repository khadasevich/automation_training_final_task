package tests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import jdk.nashorn.internal.parser.JSONParser;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.GmailMainPage;
import pages.LoginPage;
import pages.SelectAccountPage;
import webdriver.DriverManager;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

@Epic("Final Task")
@Feature("Log in / Log out")
public class LoginTests {

    DriverManager driver;
    LoginPage loginPage;
    GmailMainPage gmailMainPage;
    SelectAccountPage selectAccountPage;

    @BeforeClass
    public void setUp() {
        driver = new DriverManager("local", "chrome");
        loginPage = new LoginPage(driver.getWebDriver());
        gmailMainPage = new GmailMainPage(driver.getWebDriver());
        selectAccountPage = new SelectAccountPage(driver.getWebDriver());
        driver.maximizeWindow();
        driver.setTimeout();
    }

    @Test(dataProvider = "data", priority = 0, description = "GM-1 Login to gmail with valid credentials")
    @Description("Test goes through list of accounts and logs into gmail")
    public void loginTest(String username, String password) {
        loginPage.openLoginPage();
        loginPage.submitUsername(username);
        loginPage.submitPassword(password);
        driver.waitUntilItemWillBeShown(loginPage.getStackOverflowContent());
        loginPage.goToInbox();
        String actualResult = gmailMainPage.getLoggedUserText();
        gmailMainPage.logout();
        selectAccountPage.removeAccount();
        Assert.assertTrue(actualResult.contains(username));
    }

    @AfterClass
    public void tearDown() {
        driver.quitDriver();
    }


    @DataProvider( name = "data" )
    public static Object[][] getJSON(ITestContext context) throws FileNotFoundException {
        String filename = "C:\\Testing\\AutomationTrainingFinalTask\\src\\test\\resources\\test_data";
        JsonArray array = new JsonParser().parse(new FileReader(filename))
                .getAsJsonArray();
        Gson gson = new Gson();
        List<Map> list = gson.fromJson(array, List.class);
        Object[][] objects = list.stream()
                .map(testData ->
                        testData.values().stream().toArray()).toArray(Object[][]::new);
        return objects;
    }
}
