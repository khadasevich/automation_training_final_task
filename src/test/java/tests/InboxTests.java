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

    String usernameOne = "seleniumtests10";
    String password = "060788avavav";

    @Test(priority = 1, description = "GM-4 Verify that sent email appears in Sent Mail folder")
    @Description("Test logs in to the email box sends an email and checks that email appears in the 'Sent' folder")
    public void sentEmailTest() throws InterruptedException {
        String expectedResult = TestTools.fakeMessageGenerator();
        goThroughLogin(usernameOne, password);
        sendEmail("usernametwo@yopmail.com", expectedResult);
        gmailMainPage.goToSentMessage();
        driverManager.waitUntilItemWillBeShown(gmailMainPage.getEmailTextElement());
        String actualResult = gmailMainPage.getTextOfInboxMessage();
        Thread.sleep(1000);
        logoutRemoveAccount();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(priority = 1, description = "GM-5 Verify that deleted email is listed in Trash")
    @Description("Test logs in to the email box sends an email, deletes it and checks that email appears in the 'Trash' folder")
    public void trashEmailTest() throws InterruptedException {
        String expectedResult = TestTools.fakeMessageGenerator();
        goThroughLogin(usernameOne, password);
        sendEmail("usernametwo@yopmail.com", expectedResult);
        gmailMainPage.goToSentMessage();
        driverManager.waitUntilIsClickable(gmailMainPage.getDeleteButton());
        gmailMainPage.deleteMessage();
        gmailMainPage.goToTrashMessage();
        driverManager.waitUntilItemWillBeShown(gmailMainPage.getEmailTextElement());
        String actualResult = gmailMainPage.getTextOfInboxMessage();
        Thread.sleep(1000);
        logoutRemoveAccount();
        Assert.assertEquals(actualResult, expectedResult);
    }
}
