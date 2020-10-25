package tests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

@Listeners(TestListener.class)
@Epic("Final Task")
@Feature("Log in / Log out")
public class LoginTests extends BaseTest {


    @Test(dataProvider = "credentials", priority = 1, description = "GM-1 Login to gmail with valid credentials")
    @Description("Test goes through list of accounts and logs into gmail")
    public void loginTest(String username, String password) {
        goThroughLogin(username, password);
        loginPage.goToInbox();
        String actualResult = gmailMainPage.getLoggedUserText();
        gmailMainPage.logout();
        selectAccountPage.removeAccount();
        Assert.assertTrue(actualResult.contains(username));
    }

    public void goThroughLogin(String username, String password) {
        loginPage.openLoginPage();
        loginPage.submitUsername(username);
        loginPage.submitPassword(password);
        driverManager.waitUntilItemWillBeShown(loginPage.getStackOverflowContent());
    }

    @DataProvider(name = "credentials")
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
