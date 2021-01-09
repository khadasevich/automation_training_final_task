package pages;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Data
public class SelectAccountPage extends WebAbstractPage{

    @FindBy (css = "div > ul > li:nth-child(3)")
    private WebElement removeAccountButton;

    @FindBy (css = "div > ul > li:nth-child(1)")
    private WebElement accountNameButton;

    @FindBy (xpath = "(//span[contains(., 'seleniumtests10@gmail.com')]//following-sibling::div//div[@role='button'])[1]")
    private WebElement confirmDeleteButton;

    @FindBy (id = "initialView")
    private WebElement initialView;

    public SelectAccountPage(WebDriver driver) {
        super(driver);
    }

    public void removeAccount() {
        removeAccountButton.click();
        accountNameButton.click();
        confirmDeleteButton.click();
    }

    public boolean checkInitialViewShown() {
        return initialView.isDisplayed();
    }
}
