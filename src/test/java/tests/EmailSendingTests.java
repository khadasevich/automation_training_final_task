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
@Feature("Sending Email Test")
public class EmailSendingTests extends BaseTest {

    @Test(priority = 1, description = "GM-3 Verify the ability to send emails")
    @Description("Test logs in to the email box sends an email and checks that email was sent")
    public void sendEmailTest() throws InterruptedException {
        String usernameOne = "seleniumtests10";
        String usernameTwo = "seleniumtests30";
        String password = "060788avavav";
        String expectedResult = TestTools.fakeMessageGenerator();
        goThroughLogin(usernameOne, password);
        sendEmail(usernameTwo + "@gmail.com", expectedResult);
        logoutRemoveAccount();
        Thread.sleep(5000);
        goThroughLogin(usernameTwo, password);
        String actualResult = gmailMainPage.getEmailMessage();
        Assert.assertEquals(actualResult, expectedResult);
    }
}
