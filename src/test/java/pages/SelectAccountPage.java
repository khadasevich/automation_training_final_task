package pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SelectAccountPage extends WebAbstractPage{

    @FindBy (css = "div > ul > li:nth-child(3)")
    @Getter private WebElement removeAccount;

    @FindBy (css = "div > ul > li:nth-child(1)")
    private WebElement accountName;

    @FindBy (xpath = "(//span[contains(., 'seleniumtests10@gmail.com')]//following-sibling::div//div[@role='button'])[1]")
    private WebElement confirmDelete;

    @FindBy (id = "initialView")
    private WebElement initialView;

    public SelectAccountPage(WebDriver driver) {
        super(driver);
    }

    public void removeAccount() {
        removeAccount.click();
        accountName.click();
        confirmDelete.click();
    }

    public boolean checkInitialViewShown() {
        return initialView.isDisplayed();
    }
}
