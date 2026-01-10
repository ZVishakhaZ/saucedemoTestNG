package utils;

import java.util.Map;

import org.testng.annotations.DataProvider;

public class DataProviderUtils {

//	@DataProvider(name = "purchaseData")
//	public static Object[][] purchaseData() {
//		return new Object[][] { { "standard_user", "secret_sauce", "Vishakha", "Zalke", "400705" },
//				{ "standard_user", "secret_sauce", "Rushikesh", "Sherkar", "400705" } };
//	}
	@DataProvider(name = "purchaseData")
	public static Object[][] purchaseData() {

	    String[] testCases = {
	        "testComp_standard",
	        "testComp_performance"
	    };

	    Object[][] data = new Object[testCases.length][5];

	    for (int i = 0; i < testCases.length; i++) {

	        Map<String, String> rowData =
	                ExcelUtils.getTestData(testCases[i]); // 🔥 EXCEL CALL

	        data[i][0] = rowData.get("username");
	        data[i][1] = rowData.get("password");
	        data[i][2] = rowData.get("firstName");
	        data[i][3] = rowData.get("lastName");
	        data[i][4] = rowData.get("postalCode");
	    }
	    return data;
	}

}
