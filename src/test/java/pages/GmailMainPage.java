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

    public GmailMainPage(WebDriver driver) {
        super(driver);
    }

    @Step
    public String getLoggedUserText() {
        userIcon.click();
        String accInfo = accountInfo.getText();
        userIcon.click();
        return accInfo;
    }

    @Step
    public void logout() {
        userIcon.click();
        logOutButton.click();
    }
}
