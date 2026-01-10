package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;
import utils.DriverFactory;

public class CheckoutInformationPage extends BasePage{

	public CheckoutInformationPage(WebDriver driver) {
		super();
		PageFactory.initElements(DriverFactory.getDriver(), this);
	}
	
	 	@FindBy(id = "first-name")
	    WebElement firstNameInput;

	    @FindBy(id = "last-name")
	    WebElement lastNameInput;

	    @FindBy(id = "postal-code")
	    WebElement postalCodeInput;

	    @FindBy(id = "continue")
	    WebElement continueButton;
	    
	    public void enterFirstName(String firstName) {
	        firstNameInput.sendKeys(firstName);
	    }

	    public void enterLastName(String lastName) {
	        lastNameInput.sendKeys(lastName);
	    }

	    public void enterPostalCode(String postalCode) {
	        postalCodeInput.sendKeys(postalCode);
	    }

	    public void clickContinue() {
	        continueButton.click();
	    }
	    
	    public void fillInformation(String firstName, String lastName, String postalCode) {
	        enterFirstName(firstName);
	        enterLastName(lastName);
	        enterPostalCode(postalCode);
	        clickContinue();
	    }

}
