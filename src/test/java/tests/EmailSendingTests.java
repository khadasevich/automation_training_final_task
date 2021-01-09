package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.concurrent.TimeUnit;

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
        generateFakeSubject = generateFakeEmailSubject();
        generateFakeMessage = generateFakeEmailBody();
        signIn(usernameOne, password);
        gmailMainPage.composeAndSendEmail(usernameTwo + "@gmail.com", generateFakeSubject, generateFakeMessage);
        signOut();
        TimeUnit.SECONDS.sleep(5);
        signIn(usernameTwo, password);
        String emailMessage = gmailMainPage.getEmailMessage();
        Assert.assertEquals(emailMessage, generateFakeMessage);
    }
}
