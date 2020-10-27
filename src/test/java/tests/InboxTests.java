package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import tools.TestTools;

@Listeners(TestListener.class)
@Epic("Final Task")
@Feature("Inbox tests")
public class InboxTests extends BaseTest {

    @Test(priority = 1, description = "GM-4 Verify that sent email appears in Sent Mail folder")
    @Description("Test logs in to the email box sends an email and checks that email appears in the 'Sent' folder")
    public void sentEmailTest() {
        String usernameOne = "seleniumtests10";
        String password = "060788avavav";
        String expectedResult = TestTools.fakeMessageGenerator();
        goThroughLogin(usernameOne, password);
        gmailMainPage.openComposeEmailWindow();
        driverManager.waitUntilItemWillBeShown(gmailMainPage.getSendButton());
        gmailMainPage.sendEmail("usernametwo@yopmail.com", expectedResult);
        driverManager.waitUntilItemWillBeShown(gmailMainPage.getEmailSentToast());
        gmailMainPage.goToSentMessage();
        driverManager.waitUntilItemWillBeShown(gmailMainPage.getEmailText());
        String actualResult = gmailMainPage.getSentMessage();
        logoutRemoveAccount();
        Assert.assertTrue(actualResult.contains(expectedResult));
    }
}
