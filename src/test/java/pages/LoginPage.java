package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends WebAbstractPage {

    private final String baseInboxUrl = "https://gmail.com";


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

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Go to the Gmail base URL")
    public void openLoginPage() {
        driver.get(baseInboxUrl);
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

    public WebElement getUsernameField() {
        return usernameField;
    }
}
