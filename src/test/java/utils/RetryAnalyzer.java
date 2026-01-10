package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

	private int retryCount=0;
	private static int maxRetry;
	
	@Override
	public boolean retry(ITestResult result) {
		maxRetry =Integer.parseInt(ConfigReader.get("retry"));
		//System.out.println(maxRetry);
		if(retryCount<maxRetry) {
			retryCount++;
			return true;
		}
			return false;
	}

}
