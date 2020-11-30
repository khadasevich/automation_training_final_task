package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import tools.FakeMessages;
import webdriver.DriverManager;

@Listeners(TestListener.class)
@Epic("Final Task")
@Feature("Inbox tests")
public class InboxTests extends BasicActionsForTest {

    String usernameOne = "seleniumtests10";
    String password = "060788avavav";

    @Test(priority = 1, description = "GM-4 Verify that sent email appears in Sent Mail folder")
    @Description("Test logs in to the email box sends an email and checks that email appears in the 'Sent' folder")
    public void emailAppearsInSentInboxTest() {
        String expectedResult = FakeMessages.generateFakeMessage();
        goThroughLogin(usernameOne, password);
        sendEmail("usernametwo@yopmail.com", expectedResult);
        gmailMainPage.openFirstSentMessage();
        DriverManager.waitUntilItemWillBeShown(gmailMainPage.getReceivedEmailTextElement());
        String actualResult = gmailMainPage.getReceivedEmailText();
        Assert.assertEquals(actualResult, expectedResult);
        removeAccountAfterLogout();
    }

    @Test(priority = 1, description = "GM-5 Verify that deleted email is listed in Trash")
    @Description("Test logs in to the email box sends an email, deletes it and checks that email appears in the 'Trash' folder")
    public void removedEmailAppearsInTrashTest() {
        String expectedResult = FakeMessages.generateFakeMessage();
        goThroughLogin(usernameOne, password);
        sendEmail("usernametwo@yopmail.com", expectedResult);
        gmailMainPage.openFirstSentMessage();
        gmailMainPage.deleteMessage();
        gmailMainPage.openFirstTrashMessage();
        DriverManager.waitUntilItemWillBeShown(gmailMainPage.getReceivedEmailTextElement());
        String actualResult = gmailMainPage.getReceivedEmailText();
        Assert.assertEquals(actualResult, expectedResult);
        removeAccountAfterLogout();
    }
}
