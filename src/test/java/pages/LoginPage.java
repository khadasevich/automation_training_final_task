package pages;

import io.qameta.allure.Step;
import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Data
public class LoginPage extends WebAbstractPage {

    private final String baseGmailUrl = "https://gmail.com";
    private final String inboxUrl = "https://mail.google.com/mail/u/0/#inbox";

    @FindBy(xpath = "//input[@id='identifierId']")
    private WebElement usernameInputField;

    @FindBy(xpath = "//div[@id='password']//descendant::input")
    private WebElement passwordInputField;

    @FindBy(xpath = "//*[@id='identifierNext']/div/button")
    private WebElement usernameNextButton;

    @FindBy(xpath = "//*[@id='passwordNext']/div/button")
    private WebElement passwordNextButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Go to the Gmail base URL")
    public void openLoginPage() {
        driver.get(baseGmailUrl);
    }

    @Step("Submit username {0}")
    public void submitUsername(String username) {
        usernameInputField.sendKeys(username);
        usernameNextButton.click();
    }

    @Step("Submit password {0}")
    public void submitPassword(String password) {
        passwordInputField.sendKeys(password);
        passwordNextButton.click();
    }

    @Step("Go to the inbox")
    public void openInbox() {
        driver.get(inboxUrl);
    }
}
