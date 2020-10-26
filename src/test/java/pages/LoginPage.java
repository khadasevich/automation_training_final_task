package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends WebAbstractPage {

    private final String baseStackUrl = "https://stackoverflow.com/users/login";
    private final String baseInboxUrl = "https://mail.google.com/mail/u/0/#inbox";

    @FindBy(xpath = "//*[@id='openid-buttons']/button[contains (., 'Google')]")
    private WebElement loginWithGoogle;

    @FindBy(xpath = "//input[@id='identifierId']")
    private WebElement usernameField;

    @FindBy(xpath = "//div[@id='password']//descendant::input")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id='identifierNext']/div/button")
    private WebElement usernameNextButton;

    @FindBy(xpath = "//*[@id='passwordNext']/div/button")
    private WebElement passwordNextButton;

    @FindBy(xpath = "//div[@id='content']")
    private WebElement stackOverflowContent;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Go to the Stackoverflow and start log in via Google")
    public void openLoginPage() {
        driver.get(baseStackUrl);
        loginWithGoogle.click();
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

    @Step("Go to the Gmail Inbox")
    public void goToInbox() {
        driver.get(baseInboxUrl);
    }

    public WebElement getStackOverflowContent() {
        return stackOverflowContent;
    }

    public WebElement getUsernameField() {
        return usernameField;
    }
}
