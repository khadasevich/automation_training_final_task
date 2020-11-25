package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private final String baseGmailUrl = "https://gmail.com";
    private final String inboxUrl = "https://mail.google.com/mail/u/0/#inbox";
    protected WebDriver driver;

    @FindBy(xpath = "//input[@id='identifierId']")
    private WebElement usernameField;

    @FindBy(xpath = "//div[@id='password']//descendant::input")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id='identifierNext']/div/button")
    private WebElement usernameNextButton;

    @FindBy(xpath = "//*[@id='passwordNext']/div/button")
    private WebElement passwordNextButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @Step("Go to the Gmail base URL")
    public void openLoginPage() {
        driver.get(baseGmailUrl);
    }

    @Step("Submit username {0}")
    public void submitUsername(String username) {
        usernameField.sendKeys(username);
        usernameNextButton.click();
    }

    @Step("Submit password {0}")
    public void submitPassword(String password) {
        passwordField.sendKeys(password);
        passwordNextButton.click();
    }

    @Step("Go to the inbox")
    public void openInbox() {
        driver.get(inboxUrl);
    }

    public WebElement getUsernameField() {
        return usernameField;
    }

    public WebElement getPasswordField() {
        return passwordField;
    }
}
