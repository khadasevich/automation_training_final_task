package pages;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Formatter;

@Data
public class SelectAccountPage extends WebAbstractPage{

    @FindBy (xpath = "//div[contains(text(), 'Remove')]")
    private WebElement removeAccountButton;

    @FindBy (xpath = "//div[contains(text(), '@gmail.com')]")
    private WebElement accountNameButton;

   @FindBy (xpath = "(//span[contains(text(), 'Yes, remove')])[2]")
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
