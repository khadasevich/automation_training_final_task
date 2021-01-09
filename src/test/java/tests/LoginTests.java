package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
@Epic("Final Task")
@Feature("Log in / Log out")
public class LoginTests extends BaseTest {


    @Test(dataProvider = "credentials", priority = 1, description = "GM-1 Login to gmail with valid credentials")
    @Description("Test goes through list of accounts and logs into gmail")
    public void loginTest(String username, String password) {
        signIn(username, password);
        String loggedUsername = gmailMainPage.getAccountInfoText();
        signOut();
        Assert.assertTrue(loggedUsername.contains(username));
    }

    @Test(dataProvider = "credentials", priority = 1, description = "GM-2 Logout from gmail")
    @Description("Test goes through list of accounts and logs into gmail, logs out and check that user logged out")
    public void logoutTest(String username, String password) {
        signIn(username, password);
        signOut();
        loginPage.openInbox();
        Assert.assertTrue(selectAccountPage.checkInitialViewShown(), "User wasn't logged out");
    }
}