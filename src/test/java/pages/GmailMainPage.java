package pages;

import io.qameta.allure.Step;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import webdriver.Waiters;

import java.util.Formatter;

@Data
public class GmailMainPage extends WebAbstractPage {

    private WebElement firstSentElementButton;

    @FindBy(css = "a[href*= 'https://accounts.google.com/SignOutOptions']")
    private WebElement userNicknameButton;

    @FindBy(xpath = "//*[@id='gb']//descendant::div[contains(@aria-label, 'Account Information')]")
    private WebElement accountInfoPopup;

    @FindBy(xpath = "//a[contains(., 'Sign out')]")
    private WebElement logOutButton;

    @FindBy(xpath = "//div[contains(text(), 'Compose')]")
    private WebElement composeEmailButton;

    @FindBy(xpath = "//textarea[@name='to']")
    private WebElement emailReceiverInputField;

    @FindBy(xpath = "//input[@name='subjectbox']")
    private WebElement emailSubjectInputField;

    @FindBy(xpath = "//div[@aria-label='Message Body']")
    private WebElement emailBodyInputField;

    @FindBy(xpath = "//div[@role='dialog']/descendant::div[contains(text(), 'Send')]")
    private WebElement sendEmailButton;

    @FindBy(xpath = "//a[@aria-label='Trash']")
    private WebElement trashFolderButton;

    @FindBy(xpath = "//a[@aria-label='Sent']")
    private WebElement sentFolderButton;

    @FindBy(xpath = "(//div[@role='tabpanel']//tr[@role='row'])[1]")
    private WebElement firstInboxElementButton;

    @FindBy(xpath = "(//td/img[@alt='Trash'])[1]//parent::td//following-sibling::td[1]")
    private WebElement firstTrashElementButton;

    @FindBy(xpath = "//div[@data-message-id]/div[2]/div[3]")
    private WebElement receivedEmailTextArea;

    @FindBy(xpath = "//*[contains(text(),'Message sent')]")
    private WebElement emailSentToast;

    @FindBy(xpath = "//*[contains(text(),'View message')]")
    private WebElement viewMessageButton;

    @FindBy(xpath = "//*[contains(text(), 'Conversation moved to Trash.')]")
    private WebElement messageDeletedToast;

    @FindBy(xpath = "//*[contains(text(),'Undo')]")
    private WebElement undoButton;

    @FindBy(xpath = "(//div[2][@class='G-Ni G-aE J-J5-Ji'])[3]")
    private WebElement inboxActionBar;

    @FindBy(xpath = "//div[@data-tooltip='Delete']")
    private WebElement deleteButton;

    public GmailMainPage(WebDriver driver) {
        super(driver);
    }

    public WebElement generateFirstSentWebElement(String name) {
        Formatter form = new Formatter();
        String subjectXpath = form.format("(//span[contains(text(), '%s')])[2]", name).toString();
        return firstSentElementButton = driver.findElement(By.xpath(subjectXpath));
    }

    @Step("Return Info Of the Logged in User")
    public String getAccountInfoText() {
        userNicknameButton.click();
        String accountInfo = this.accountInfoPopup.getText();
        userNicknameButton.click();
        return accountInfo;
    }

    @Step("Open profile menu")
    public void openProfileMenu() {
        userNicknameButton.click();
    }

    @Step("Log out")
    public void gmailLogout() {
        logOutButton.click();
    }

    @Step("Start Email Sending")
    public void openComposeEmailWindow() {
        composeEmailButton.click();
    }

    @Step("Send Email")
    public void createEmail(String email, String fakeSubject, String fakeMessage) {
        emailReceiverInputField.sendKeys(email);
        emailSubjectInputField.sendKeys(fakeSubject);
        emailBodyInputField.sendKeys(fakeMessage);
        sendEmailButton.click();
    }

    @Step("Get result message")
    public String getEmailMessage() {
        firstInboxElementButton.click();
        return receivedEmailTextArea.getText();
    }

    @Step("Go to the sent message")
    public void openFirstSentMessage(String fakeSubject) {
        sentFolderButton.click();
        firstSentElementButton = generateFirstSentWebElement(fakeSubject);
        firstSentElementButton.click();
    }

    @Step("Get message of the sent message")
    public String getReceivedEmailText() {
        return receivedEmailTextArea.getText();
    }

    @Step("Go to the trash message")
    public void openFirstTrashMessage() {
        trashFolderButton.click();
        firstTrashElementButton.click();
    }

    @Step("Delete message")
    public void deleteMessage() {
        action.moveToElement(inboxActionBar).build().perform();
        deleteButton.click();
    }

    @Step("Compose and send message")
    public void composeAndSendEmail(String email, String subject, String bodyText) {
        openComposeEmailWindow();
        Waiters.waitUntilItemIsClickable(sendEmailButton, driver);
        createEmail(email, subject, bodyText);
        Waiters.waitUntilItemWillBeShown(emailSentToast, driver);
        Waiters.waitUntilItemPresents(undoButton, driver);
    }
}
