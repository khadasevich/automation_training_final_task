package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import webdriver.Waiters;

@Listeners(TestListener.class)
@Epic("Final Task")
@Feature("Inbox tests")
public class InboxTests extends BaseTest {

    String usernameOne = "seleniumtests10";
    String password = "060788avavav";

    @BeforeMethod
    public void prepareAndSignIn() {
        generateFakeMessage = generateFakeEmailBody();
        generateFakeSubject = generateFakeEmailSubject();
        signIn(usernameOne, password);
    }

    @Test(priority = 1, description = "GM-4 Verify that sent email appears in Sent Mail folder")
    @Description("Test logs in to the email box sends an email and checks that email appears in the 'Sent' folder")
    public void emailAppearsInSentInboxTest() {
        gmailMainPage.composeAndSendEmail("usernametwo@yopmail.com", generateFakeSubject, generateFakeMessage);
        gmailMainPage.openFirstSentMessage(generateFakeSubject);
        Waiters.waitUntilItemWillBeShown(gmailMainPage.getReceivedEmailTextArea(), driver);
        String actualResult = gmailMainPage.getReceivedEmailText();
        Assert.assertEquals(actualResult, generateFakeMessage);
    }

    @Test(priority = 1, description = "GM-5 Verify that deleted email is listed in Trash")
    @Description("Test logs in to the email box sends an email, deletes it and checks that email appears in the 'Trash' folder")
    public void removedEmailAppearsInTrashTest() {
        gmailMainPage.composeAndSendEmail("usernametwo@yopmail.com", generateFakeSubject, generateFakeMessage);
        gmailMainPage.openFirstSentMessage(generateFakeSubject);
        gmailMainPage.deleteMessage();
        gmailMainPage.openFirstTrashMessage();
        Waiters.waitUntilItemWillBeShown(gmailMainPage.getReceivedEmailTextArea(), driver);
        String receivedEmailText = gmailMainPage.getReceivedEmailText();
        Assert.assertEquals(receivedEmailText, generateFakeMessage);
    }
}
