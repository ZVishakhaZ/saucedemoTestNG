package utils;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import base.BaseTest;

public class TestNGListener extends BaseTest implements ITestListener {

    ExtentReports extent;
    ExtentTest test;
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {

        String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);

        reporter.config().setReportName("Automation Test Report");
        reporter.config().setDocumentTitle("SauceDemo Report");

        extent = new ExtentReports();
        extent.attachReporter(reporter);

        extent.setSystemInfo("QA Team", "Vishakha & Rushikesh");
        extent.setSystemInfo("Environment", ConfigReader.get("env"));
    }

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {

        Object[] params = result.getParameters();
        String username = params[0].toString();

        ExcelUtils.updateResult(username,"PASS","Test passed successfully");

        extentTest.get().log(Status.PASS, "Test passed successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        Object[] params = result.getParameters();
        String username = params[0].toString();

        ExcelUtils.updateResult(username,"FAIL",result.getThrowable().getMessage()
        );

        WebDriver driver = DriverFactory.getDriver();
        extentTest.get().fail(result.getThrowable());

        String path = ScreenshotUtils.capture(driver, result.getName());
        extentTest.get().addScreenCaptureFromPath(path, "Failed Test Screenshot");
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        Object[] params = result.getParameters();
        String username = params[0].toString();

        ExcelUtils.updateResult(username,"SKIPPED","Test skipped"
        );

        extentTest.get().log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
