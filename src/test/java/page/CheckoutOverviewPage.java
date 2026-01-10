package page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;
import utils.DriverFactory;

public class CheckoutOverviewPage extends BasePage {

	public CheckoutOverviewPage(WebDriver driver) {
		super();
		PageFactory.initElements(DriverFactory.getDriver(), this);
	}
	
	@FindBy(className = "cart_item")
    private List<WebElement> items;

    @FindBy(id = "finish")
    private WebElement finishButton;

    public int getNumberOfItems() {
        return items.size();
    }
    
    public void clickFinish() {
        finishButton.click();
    }


}
