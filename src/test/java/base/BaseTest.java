package base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import utils.ConfigReader;
import utils.DriverFactory;

public class BaseTest {

	public String browser;
	public String url;
	
	
	@BeforeMethod(alwaysRun=true)
	public void launchApp() {
		System.out.println("Launch Application");
		ConfigReader.loadProperties();
		browser = ConfigReader.get("browser");
		DriverFactory.initDriver(browser);
		DriverFactory.getDriver().manage().window().maximize();		
		url = ConfigReader.get("appUrl");
		DriverFactory.getDriver().get(url);
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		System.out.println("Closing Application");
		DriverFactory.quitDriver();
	}
	
}
