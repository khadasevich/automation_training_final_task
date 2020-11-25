package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SelectAccountPage {

    protected WebDriver driver;

    @FindBy (xpath = "//*[@id='view_container']/descendant::li[3]")
    private WebElement removeAccount;

    @FindBy (xpath = "//*[@id='view_container']/descendant::li[1]")
    private WebElement accountName;

    @FindBy (xpath = "//*[@id='yDmH0d']//descendant::span[contains(text(), 'Удалить')][4]")
    private WebElement confirmDelete;

    @FindBy (xpath = "//div[@id='initialView']")
    private WebElement initialView;

    public SelectAccountPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public void removeAccount() {
        removeAccount.click();
        accountName.click();
        confirmDelete.click();
    }

    public boolean checkInitialViewShown() {
        return initialView.isDisplayed();
    }

    public WebElement getRemoveAccount() {
        return removeAccount;
    }
}
