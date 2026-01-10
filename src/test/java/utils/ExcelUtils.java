package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    private static final String FILE_PATH = "src/test/resources/TestData.xlsx";
    private static final String SHEET_NAME = "Sheet2";

    //read data
    public static Map<String, String> getTestData(String testCaseName) {

        Map<String, String> data = new HashMap<>();
        DataFormatter formatter = new DataFormatter();

        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(SHEET_NAME);
            Row headerRow = sheet.getRow(0);

            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;

                //read = match by testname
                String excelTestCase = formatter.formatCellValue(row.getCell(0));

                if (excelTestCase.equalsIgnoreCase(testCaseName)) {

                    for (int c = 1; c < headerRow.getLastCellNum(); c++) {
                        String key = formatter.formatCellValue(headerRow.getCell(c));
                        String value = formatter.formatCellValue(row.getCell(c));
                        data.put(key, value);
                    }
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    //write test result
    public static void updateResult(String username, String status, String remarks) {

        DataFormatter formatter = new DataFormatter();

        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(SHEET_NAME);

            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;

                //write by username
                String excelUsername = formatter.formatCellValue(row.getCell(1));

                if (excelUsername.equalsIgnoreCase(username)) {

                    setCellValue(row, 6, "Executed");
                    setCellValue(row, 7, status);
                    setCellValue(row, 8, remarks);
                    break;
                }
            }

            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //helper code
    private static void setCellValue(Row row, int index, String value) {
        Cell cell = row.getCell(index);
        if (cell == null) {
            cell = row.createCell(index);
        }
        cell.setCellValue(value);
    }
}
