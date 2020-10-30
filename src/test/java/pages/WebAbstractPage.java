package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

public abstract class WebAbstractPage {

    protected WebDriver driver;
    Actions action;

    public WebAbstractPage(WebDriver driver) {
        this.driver = driver;
        this.action = new Actions(driver);
        PageFactory.initElements(this.driver, this);
    }
}