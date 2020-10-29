package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SelectAccountPage extends WebAbstractPage {

    @FindBy (xpath = "//*[@id='view_container']/descendant::li[3]")
    private WebElement removeAccountButton;

    @FindBy (xpath = "//*[@id='view_container']/descendant::li[1]")
    private WebElement accountNameButton;

    @FindBy (xpath = "//*[@id='yDmH0d']//descendant::span[contains(text(), 'Удалить')][4]")
    private WebElement confirmDeleteButton;

    @FindBy (xpath = "//div[@id='initialView']")
    private WebElement signInView;

    public SelectAccountPage(WebDriver driver) {
        super(driver);
    }

    public void removeAccount() {
        removeAccountButton.click();
        accountNameButton.click();
        confirmDeleteButton.click();
    }

    public boolean removeAccountIsDisplayed() {
        return removeAccountButton.isDisplayed();
    }

    public boolean signInViewIsDisplayed() {
        return signInView.isDisplayed();
    }

    public WebElement getRemoveAccountButton() {
        return removeAccountButton;
    }
}
