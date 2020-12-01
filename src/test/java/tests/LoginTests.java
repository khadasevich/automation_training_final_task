package tests;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
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
public class LoginTests extends BasicActionsForTest {


    @Test(dataProvider = "credentials", priority = 1, description = "GM-1 Login to gmail with valid credentials")
    @Description("Test goes through list of accounts and logs into gmail")
    public void loginTest(String username, String password) {
        goThroughLogin(username, password);
        String loggedUsername = gmailMainPage.getAccountInfo();
        removeAccountAfterLogout();
        Assert.assertTrue(loggedUsername.contains(username));
    }

    @Test(dataProvider = "credentials", priority = 1, description = "GM-2 Logout from gmail")
    @Description("Test goes through list of accounts and logs into gmail, logs out and check that user logged out")
    public void logoutTest(String username, String password) {
        goThroughLogin(username, password);
        removeAccountAfterLogout();
        loginPage.openInbox();
        Assert.assertTrue(selectAccountPage.checkInitialViewShown(), "User wasn't logged out");
    }
}