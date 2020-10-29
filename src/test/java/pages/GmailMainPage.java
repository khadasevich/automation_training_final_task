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

    @FindBy (xpath = "//div[contains(text(), 'Compose')]")
    private WebElement composeButton;

    @FindBy (xpath = "//textarea[@name='to']")
    private WebElement toInputField;

    @FindBy (xpath = "//input[@name='subjectbox']")
    private WebElement subjectInputField;

    @FindBy (xpath = "//div[@aria-label='Message Body']")
    private WebElement bodyInputField;

    @FindBy (xpath = "//div[@role='dialog']/descendant::div[contains(text(), 'Send')]")
    private WebElement sendButton;

    @FindBy (xpath = "//a[@aria-label='Trash']")
    private WebElement trashButton;

    @FindBy (xpath = "//a[@aria-label='Sent']")
    private WebElement sentBoxButton;

    @FindBy (xpath = "//div[@role='tabpanel']//tr[@role='row'][1]")
    private WebElement firstElementInbox;

    @FindBy (xpath = "//tr[1]/descendant::div[contains(text(), 'To')]")
    private WebElement firstElementSent;

    @FindBy (xpath = "(//td/img[@alt='Trash'])[1]//parent::td//following-sibling::td[1]")
    private WebElement firstElementTrash;

    @FindBy (xpath = "//div[@data-message-id]/div[2]/div[3]")
    private WebElement emailText;

    @FindBy (xpath = "//*[contains(text(),'Message sent')]")
    private WebElement emailSentToast;

    @FindBy (xpath = "//*[contains(text(),'View message')]")
    private WebElement viewMessageButton;

    @FindBy (xpath = "//*[contains(text(), 'Conversation moved to Trash.')]")
    private WebElement messageDeletedToast;

    @FindBy (xpath = "//*[contains(text(),'Undo')]")
    private WebElement undoButton;

    @FindBy (xpath = "//div[@data-tooltip='Delete']")
    private WebElement deleteButton;

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

    @Step("Open profile menu")
    public void openProfileMenu() {
        userIcon.click();
    }

    @Step("Log out")
    public void logout() {
        logOutButton.click();
    }

    @Step("Start Email Sending")
    public void openComposeEmailWindow() {
        composeButton.click();
    }

    @Step("Send Email")
    public void sendEmail(String email, String fakeMessage) {
        toInputField.sendKeys(email);
        subjectInputField.sendKeys("Chuck Test Message");
        bodyInputField.sendKeys(fakeMessage);
        sendButton.click();
    }

    @Step("Get result message")
    public String getEmailMessage() {
        firstElementInbox.click();
        return emailText.getText();
    }

    public WebElement getComposeButton() {
        return composeButton;
    }

    @Step("Go to the sent message")
    public void goToSentMessage() {
        sentBoxButton.click();
        firstElementSent.click();
    }

    @Step("Get message of the sent message")
    public String getTextOfInboxMessage() {
        return emailText.getText();
    }

    @Step("Go to the trash message")
    public void goToTrashMessage() {
        trashButton.click();
        firstElementTrash.click();
    }

    @Step("Delete message")
    public void deleteMessage() {
        deleteButton.click();
    }

    public WebElement getEmailTextElement() {
        return emailText;
    }

    public WebElement getSendButton() {
        return sendButton;
    }

    public WebElement getEmailSentToast() {
        return emailSentToast;
    }

    public WebElement getLogOutButton() {
        return logOutButton;
    }

    public WebElement getUndoButton() {
        return undoButton;
    }

    public WebElement getDeleteButton() {
        return deleteButton;
    }
}
