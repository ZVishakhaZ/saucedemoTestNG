package test;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import page.CartPage;
import page.CheckoutCompletePage;
import page.CheckoutInformationPage;
import page.CheckoutOverviewPage;
import page.InventoryPage;
import page.LoginPage;
import utils.DataProviderUtils;
import utils.DriverFactory;
import utils.Log;
import utils.ScreenshotUtils;

public class PurchaseFlowTest extends BaseTest {

	// Page Objects // Changes added
	LoginPage loginPage;
	InventoryPage inventoryPage;
	CartPage cartPage;
	CheckoutInformationPage checkoutInformationPage;
	CheckoutOverviewPage checkoutOverviewPage;
	CheckoutCompletePage checkoutCompletePage;

	@Test (dataProvider ="purchaseData",dataProviderClass = DataProviderUtils.class
		)
	public void testCompletePurchaseFlow(String username,String password, String firstName, String lastName,
			String postalCode) throws InterruptedException {

		WebDriver driver = DriverFactory.getDriver();

		// Initialize PageFactory pages
		loginPage = new LoginPage(driver);
		inventoryPage = new InventoryPage(driver);
		cartPage = new CartPage(driver);
		checkoutInformationPage = new CheckoutInformationPage(driver);
		checkoutOverviewPage = new CheckoutOverviewPage(driver);
		checkoutCompletePage = new CheckoutCompletePage(driver);

		// Login
		Log.info("Launching Application");
		//loginPage.login("standard_user", "secret_suce");
		loginPage.login(username, password);
		System.out.println("USER=" + username + " | PASS=" + password);
		
		Log.info("Entered Details");
		Thread.sleep(5000);
		//loginPage.clickLogin();
		Log.info(driver.getCurrentUrl());
		

		// Inventory – add product
		inventoryPage.addProductToCart(0);
		Log.info("Added product to cart");
		ScreenshotUtils.capture(driver, "inventory_page");
		inventoryPage.goToCart();	
		Log.info(driver.getCurrentUrl());
		Log.info("On Cart Page");

		// Cart – verify item & checkout
		Assert.assertEquals(cartPage.getCartItemCount(), 1, "Cart item count is incorrect");
		Log.info("Correct Products are added in the cart");
		cartPage.clickCheckout();
		Log.info(driver.getCurrentUrl());

		// Checkout – Your Information
		//checkoutInformationPage.fillInformation("Vishakha", "Zalke", "400705");
		checkoutInformationPage.fillInformation(firstName, lastName, postalCode);
		Log.info("Information is Added");

		// Checkout – Overview
		Assert.assertEquals(checkoutOverviewPage.getNumberOfItems(), 1, "Overview item count mismatch");
		Log.info(driver.getCurrentUrl());
		Log.info("On Overview Page");
		checkoutOverviewPage.clickFinish();
		Log.info("Finished adding Details");

		// Checkout – Complete
		Assert.assertEquals(checkoutCompletePage.getCompleteHeader(), "Thank you for your order!",
				"Order was not completed successfully");
		Log.info("Testcase successful");
	}
}
