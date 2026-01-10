package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;
import utils.DriverFactory;

public class CheckoutCompletePage extends BasePage {

	public CheckoutCompletePage(WebDriver driver) {
		super();
		PageFactory.initElements(DriverFactory.getDriver(), this);
	}
	
	@FindBy(className = "complete-header")
    private WebElement completeHeader;

    @FindBy(className = "complete-text")
    private WebElement completeText;

    public String getCompleteHeader() {
        return completeHeader.getText();
    }

    public String getCompleteText() {
        return completeText.getText();
    }

}
