package pages;

import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import webdriver.DriverManager;
import webdriver.Waiters;

public class GmailMainPage extends WebAbstractPage {

    @FindBy(css = "a[href*= 'https://accounts.google.com/SignOutOptions']")
    @Getter
    private WebElement userIcon;

    @FindBy(xpath = "//*[@id='gb']//descendant::div[contains(@aria-label, 'Account Information')]")
    private WebElement accountInfo;

    @FindBy(xpath = "//a[contains(., 'Sign out')]")
    @Getter
    private WebElement logOutButton;

    @FindBy(xpath = "//div[contains(text(), 'Compose')]")
    private WebElement composeButton;

    @FindBy(xpath = "//textarea[@name='to']")
    private WebElement receiverInputField;

    @FindBy(xpath = "//input[@name='subjectbox']")
    private WebElement subjectInputField;

    @FindBy(xpath = "//div[@aria-label='Message Body']")
    private WebElement bodyInputField;

    @FindBy(xpath = "//div[@role='dialog']/descendant::div[contains(text(), 'Send')]")
    @Getter
    private WebElement sendButton;

    @FindBy(xpath = "//a[@aria-label='Trash']")
    private WebElement trashInbox;

    @FindBy(xpath = "//a[@aria-label='Sent']")
    private WebElement sentInbox;

    @FindBy(xpath = "(//div[@role='tabpanel']//tr[@role='row'])[1]")
    private WebElement firstInboxElement;

    @FindBy(xpath = "//tr[1]/descendant::div[contains(text(), 'To')]")
    private WebElement firstSentElement;

    @FindBy(xpath = "(//td/img[@alt='Trash'])[1]//parent::td//following-sibling::td[1]")
    private WebElement firstTrashElement;

    @FindBy(xpath = "//div[@data-message-id]/div[2]/div[3]")
    @Getter
    private WebElement receivedEmailTextElement;

    @FindBy(xpath = "//*[contains(text(),'Message sent')]")
    @Getter
    private WebElement emailSentToast;

    @FindBy(xpath = "//*[contains(text(),'View message')]")
    private WebElement viewMessageButton;

    @FindBy(xpath = "//*[contains(text(), 'Conversation moved to Trash.')]")
    private WebElement messageDeletedToast;

    @FindBy(xpath = "//*[contains(text(),'Undo')]")
    @Getter
    private WebElement undoButton;

    @FindBy(xpath = "(//div[2][@class='G-Ni G-aE J-J5-Ji'])[3]")
    private WebElement inboxActionBar;

    @FindBy(xpath = "//div[@data-tooltip='Delete']")
    private WebElement deleteButton;

    public GmailMainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Return Info Of the Logged in User")
    public String getAccountInfo() {
        userIcon.click();
        String accountInfo = this.accountInfo.getText();
        userIcon.click();
        return accountInfo;
    }

    @Step("Open profile menu")
    public void openProfileMenu() {
        userIcon.click();
    }

    @Step("Log out")
    public void gmailLogout() {
        logOutButton.click();
    }

    @Step("Start Email Sending")
    public void openComposeEmailWindow() {
        composeButton.click();
    }

    @Step("Send Email")
    public void createEmail(String email, String fakeMessage) {
        receiverInputField.sendKeys(email);
        subjectInputField.sendKeys("Chuck Test Message");
        bodyInputField.sendKeys(fakeMessage);
        sendButton.click();
    }

    @Step("Get result message")
    public String getEmailMessage() {
        firstInboxElement.click();
        return receivedEmailTextElement.getText();
    }

    public WebElement getComposeButton() {
        return composeButton;
    }

    @Step("Go to the sent message")
    public void openFirstSentMessage() {
        sentInbox.click();
        firstSentElement.click();
    }

    @Step("Get message of the sent message")
    public String getReceivedEmailText() {
        return receivedEmailTextElement.getText();
    }

    @Step("Go to the trash message")
    public void openFirstTrashMessage() {
        trashInbox.click();
        firstTrashElement.click();
    }

    @Step("Delete message")
    public void deleteMessage() {
        action.moveToElement(inboxActionBar).build().perform();
        deleteButton.click();
    }

    @Step("Compose and send message")
    public void composeAndSendEmail(String email, String bodyText) {
        openComposeEmailWindow();
        Waiters.waitUntilItemIsClickable(sendButton, driver);
        createEmail(email, bodyText);
        Waiters.waitUntilItemWillBeShown(emailSentToast, driver);
        Waiters.waitUntilItemPresents(undoButton, driver);
    }
}
