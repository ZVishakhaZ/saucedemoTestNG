package page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;
import utils.DriverFactory;

public class CartPage extends BasePage {

	public CartPage(WebDriver driver) {
		super();
		PageFactory.initElements(DriverFactory.getDriver(), this);
	}

	@FindBy(className ="cart_item")
	private List<WebElement> cartItems;

	@FindBy(id ="checkout")
	private WebElement checkoutButton;

	// Methods
	public int getCartItemCount() {
		return cartItems.size();
	}

	public void clickCheckout() {
		checkoutButton.click();
	}

}
