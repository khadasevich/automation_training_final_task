package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.*;
import tools.FakeMessages;

@Listeners(TestListener.class)
@Epic("Final Task")
@Feature("Sending Email Test")
public class EmailSendingTests extends BaseTest {

    @Test(priority = 1, description = "GM-3 Verify the ability to send emails")
    @Description("Test logs in to the email box sends an email and checks that email was sent")
    @Parameters({"usernameOne", "usernameTwo", "password"})
    public void sendEmailTest(@Optional("seleniumtests10") String usernameOne,
                              @Optional("seleniumtests30") String usernameTwo,
                              @Optional("060788avavav") String password) throws InterruptedException {
        String generateFakeSubject = FakeMessages.generateFakeEmailSubject();
        String generateFakeMessage = FakeMessages.generateFakeEmailBody();
        signIn(usernameOne, password);
        gmailMainPage.composeAndSendEmail(usernameTwo + "@gmail.com", generateFakeSubject, generateFakeMessage);
        signOut();
        Thread.sleep(5000);
        signIn(usernameTwo, password);
        String emailMessage = gmailMainPage.getEmailMessage();
        Assert.assertEquals(emailMessage, generateFakeMessage);
    }

    @AfterMethod
    public void logout() {
        signOut();
    }
}
