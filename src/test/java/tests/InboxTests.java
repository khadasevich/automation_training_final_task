package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import tools.FakeMessages;
import webdriver.Waiters;

@Listeners(TestListener.class)
@Epic("Final Task")
@Feature("Inbox tests")
public class InboxTests extends BasicActionsForTest {

    String usernameOne = "seleniumtests10";
    String password = "060788avavav";

    @Test(priority = 1, description = "GM-4 Verify that sent email appears in Sent Mail folder")
    @Description("Test logs in to the email box sends an email and checks that email appears in the 'Sent' folder")
    public void emailAppearsInSentInboxTest() {
        String generateFakeMessage = FakeMessages.generateFakeMessage();
        goThroughLogin(usernameOne, password);
        gmailMainPage.composeAndSendEmail("usernametwo@yopmail.com", generateFakeMessage);
        gmailMainPage.openFirstSentMessage();
        Waiters.waitUntilItemWillBeShown(gmailMainPage.getReceivedEmailTextElement(), driver);
        String actualResult = gmailMainPage.getReceivedEmailText();
        Assert.assertEquals(actualResult, generateFakeMessage);
    }

    @Test(priority = 1, description = "GM-5 Verify that deleted email is listed in Trash")
    @Description("Test logs in to the email box sends an email, deletes it and checks that email appears in the 'Trash' folder")
    public void removedEmailAppearsInTrashTest() {
        String generateFakeMessage = FakeMessages.generateFakeMessage();
        goThroughLogin(usernameOne, password);
        gmailMainPage.composeAndSendEmail("usernametwo@yopmail.com", generateFakeMessage);
        gmailMainPage.openFirstSentMessage();
        gmailMainPage.deleteMessage();
        gmailMainPage.openFirstTrashMessage();
        Waiters.waitUntilItemWillBeShown(gmailMainPage.getReceivedEmailTextElement(), driver);
        String receivedEmailText = gmailMainPage.getReceivedEmailText();
        Assert.assertEquals(receivedEmailText, generateFakeMessage);
    }

    @AfterMethod
    public void logout() {
        removeAccountAfterLogout();
    }
}
