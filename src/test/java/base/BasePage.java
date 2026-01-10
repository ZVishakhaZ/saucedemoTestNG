package base;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.ConfigReader;
import utils.DriverFactory;

public class BasePage {

	// Actions is used for advanced mouse and keyboard interactions,
	// and WebDriverWait is used for explicit waits to handle dynamic web elements.
	// Declaring them as protected allows reuse in child test classes.

	protected Actions action;
	protected WebDriverWait wait;
	int time;

	// wait methods

	public BasePage() {
		WebDriver driver = DriverFactory.getDriver();
		time = Integer.parseInt(ConfigReader.get("timeout"));
		action = new Actions(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}

	public WebElement waitforVisible(WebElement elm) {
		try {
			return wait.until(ExpectedConditions.visibilityOf(elm));
		} catch (Exception e) {
			return null;
		}
	}

	public WebElement waitforClickable(WebElement elm) {
		return wait.until(ExpectedConditions.elementToBeClickable(elm));
	}

	public boolean waitforInvisbility(WebElement elm) {
		return wait.until(ExpectedConditions.invisibilityOf(elm));
	}

	public void waitForText(WebElement elm, String text) {
		wait.until((ExpectedConditions.textToBePresentInElement(elm, text)));
	}

	// Action Methods

	public void click(WebElement elm) {
		if (isPresent(elm)) {
			waitforClickable(elm).click();
		}
	}

	public boolean type(WebElement elm, String text) {
		try {
			waitforVisible(elm).sendKeys(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getText(WebElement elm) {
		try {
			return waitforVisible(elm).getText();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isDisplayed(WebElement elm) {
		try {
			return waitforVisible(elm).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isSelected(WebElement elm) {
		try {
			return waitforVisible(elm).isSelected();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isEnabled(WebElement elm) {
		try {
			return waitforVisible(elm).isEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	// Page Navigation

	public void open(String url) {
		DriverFactory.getDriver().get(url);
	}

	public String getPageTitle() {
		return DriverFactory.getDriver().getTitle();
	}

	// Dropdown

	public Boolean selectByVisibleText(WebElement elm, String text) {
		try {
			Select s = new Select(waitforVisible(elm));
			s.selectByVisibleText(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean selectByValue(WebElement elm, String value) {
		try {
			Select s = new Select(waitforVisible(elm));
			s.selectByValue(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean selectByIndex(WebElement elm, int index) {
		try {
			Select s = new Select(waitforVisible(elm));
			s.selectByIndex(index);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean selectFromAutoSuggest(WebElement inputbox, List<WebElement> suggestions, String textToType,
			String valueToSelect) {

		// type in inputBox

		wait.until(ExpectedConditions.visibilityOf(inputbox));
		wait.until(ExpectedConditions.elementToBeClickable(inputbox));
		jsClick(inputbox);
		type(inputbox, textToType);

		// Wait until suggestions are visible
		wait.until(ExpectedConditions.visibilityOfAllElements(suggestions));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (WebElement option : suggestions) {
			try {
				if (option.getText().contains(valueToSelect)) {
					wait.until(ExpectedConditions.elementToBeClickable(option));
					jsClick(option);
					return true;
				}
			} catch (StaleElementReferenceException e) {
				return false;
				// PageFactory list refresh handled implicitly on next interaction
			}
		}

		return false;
	}

	// Scroll

	public void scrollTo(WebElement elm) {
		((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].scrollIntoView(true);", elm);
	}

	public void scrollBy(int x, int y) {
		((JavascriptExecutor) DriverFactory.getDriver()).executeScript("window.scrollBy(arguments[0], arguments[1]);",
				x, y);
	}

	// js Actions
	public void jsClick(WebElement elm) {
		waitforVisible(elm);
		((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].click();", elm);
	}

	public void jsSetValue(WebElement elm, String value) {
		waitforVisible(elm);
		((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].value='" + value + "'", elm);
	}

	public void jsSetValueCharByChar(WebElement elm, String value) {
		waitforVisible(elm);

		JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getDriver();

		// Clear existing value
		js.executeScript("arguments[0].value='';", elm);

		for (char ch : value.toCharArray()) {
			js.executeScript("arguments[0].value = arguments[0].value + arguments[1];"
					+ "arguments[0].dispatchEvent(new Event('input'));"
					+ "arguments[0].dispatchEvent(new Event('change'));", elm, String.valueOf(ch));
		}
	}

	public String jsGetText(WebElement elm) {
		waitforVisible(elm);
		return (String) ((JavascriptExecutor) DriverFactory.getDriver())
				.executeScript("return arguments[0].textContent;", elm);
	}

	// Action Class Utilities

	public void hover(WebElement elm) {
		action.moveToElement(waitforVisible(elm)).perform();
	}

	public void rightClick(WebElement elm) {
		action.contextClick(waitforVisible(elm)).perform();
	}

	public void doubleClick(WebElement elm) {
		action.doubleClick(waitforVisible(elm)).perform();
	}

	public void dragAndDrop(WebElement source, WebElement target) {
		action.dragAndDrop(waitforVisible(source), waitforVisible(target)).perform();
	}

	public void dragAndDropBy(WebElement elm, int x, int y) {
		action.dragAndDropBy(waitforVisible(elm), x, y).perform();
	}

	public void typeUsingActions(WebElement elm, String text) {
		action.moveToElement(waitforVisible(elm)).click().sendKeys(text).perform();
	}

	// Alerts

	public void acceptAlert() {
		wait.until(ExpectedConditions.alertIsPresent()).accept();
	}

	public void dismissAlert() {
		wait.until(ExpectedConditions.alertIsPresent()).dismiss();
	}

	public String getAlertText() {
		return wait.until(ExpectedConditions.alertIsPresent()).getText();
	}

	public void sendKeysToAlert(String text) {
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.sendKeys(text);
		alert.accept();
	}

	// Frame and Window Handling

	public void switchToFrame(WebElement frameElement) {
		waitforVisible(frameElement);
		DriverFactory.getDriver().switchTo().frame(frameElement);
	}

	public void switchToFrameByIndex(int index) {
		DriverFactory.getDriver().switchTo().frame(index);
	}

	public void switchToDefault() {
		DriverFactory.getDriver().switchTo().defaultContent();
	}

	public void switchToWindow(String handle) {
		DriverFactory.getDriver().switchTo().window(handle);
	}

	// wait for Page Load

	public void waitForPageLoad() {
		new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(15))
				.until(webDriver -> ((JavascriptExecutor) DriverFactory.getDriver())
						.executeScript("return document.readyState").equals("complete"));
	}

	// Retry Click

	public void safeClick(WebElement elm) {
		int attempts = 0;
		while (attempts < 3) {
			try {
				click(elm);
				return;
			} catch (Exception e) {
				attempts++;
			}
		}
	}

	// Existing elements check

	public boolean isPresent(WebElement elm) {
		try {
			waitforVisible(elm);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// wait for text change

	public void waitForTextChange(WebElement elm, String expectedText) {
		wait.until(driver -> elm.getText().contains(expectedText));
	}

}
