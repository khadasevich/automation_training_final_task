package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GmailMainPage extends WebAbstractPage {

    @FindBy (css = "a[href*= 'https://accounts.google.com/SignOutOptions']")
    private WebElement userIcon;

    @FindBy (xpath = "//*[@id='gb']//descendant::div[contains(@aria-label, 'Account Information')]")
    private WebElement accountInfo;

    @FindBy (xpath = "//a[@id='gb_71']")
    private WebElement logOutButton;

    @FindBy (xpath = "//*[@id=':6c']/div/div")
    private WebElement composeButton;

    @FindBy (xpath = "//textarea[@name='to']")
    private WebElement toInputField;

    @FindBy (xpath = "//input[@name='subjectbox']")
    private WebElement subjectInputField;

    @FindBy (xpath = "//div[@aria-label='Message Body']")
    private WebElement bodyInputField;

    @FindBy (xpath = "//*[@id=':a5']")
    private WebElement sendButton;

    @FindBy (xpath = "//a[@aria-label='Trash']")
    private WebElement trashButton;

    @FindBy (xpath = "//a[@aria-label='Sent']")
    private WebElement sentButton;

    @FindBy (xpath = "//tr[@role='row'][1]")
    private WebElement firstElementInbox;

    @FindBy (xpath = "//div[@data-message-id]")
    private WebElement emailText;

    public GmailMainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Return Info Of the Logged in User")
    public String getLoggedUserText() {
        userIcon.click();
        String accInfo = accountInfo.getText();
        userIcon.click();
        return accInfo;
    }

    @Step("Log out from account")
    public void logout() {
        userIcon.click();
        logOutButton.click();
    }

    @Step("Send Email")
    public void sendEmail(String email, String fakeMessage) {
        composeButton.click();
        toInputField.sendKeys(email);
        subjectInputField.sendKeys("Chuck Test Message");
        bodyInputField.sendKeys(fakeMessage);
    }

    @Step("Get result message")
    public String getEmailMessage() {
        return emailText.getText();
    }

    public WebElement getComposeButton() {
        return composeButton;
    }
}
