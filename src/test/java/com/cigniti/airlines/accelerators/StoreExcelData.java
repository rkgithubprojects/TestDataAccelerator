package com.cigniti.airlines.accelerators;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cigniti.airlines.utils.TestData;

public class StoreExcelData {

	/*
	 * Store Excel data in the form of keys and values 
	 * keys - feature name:Possible value  
	 * values - Locator data in the TestData object for particular feature data
	 * @readSheet : sheet object from excel work book where test data is written
	 */

	public Map<String, TestData> storeExcelData(Sheet readSheet) {
		Map<String, TestData> fData = null;
		try {
			fData = new HashMap<String, TestData>();

			int rowsCount = readSheet.getLastRowNum() - readSheet.getFirstRowNum();
			String temp = "";
			String key = "";
			String statement="";
			for (int i = 1; i < rowsCount + 1; i++) {

				TestData testData = new TestData();

				if (readSheet.getRow(i).getCell(0) != null && !(readSheet.getRow(i).getCell(0).getStringCellValue().equals(""))) 
				{
					temp = readSheet.getRow(i).getCell(0).getStringCellValue();
				}
								
				key =temp + ":" + readSheet.getRow(i).getCell(1);
				
				if(readSheet.getRow(i).getCell(2) != null && !(readSheet.getRow(i).getCell(2).getStringCellValue().equals("")))
				{
					statement=readSheet.getRow(i).getCell(2).getStringCellValue();
				}
				
				testData.setFeatureType(temp);
				testData.setCategory(readSheet.getRow(i).getCell(1).getStringCellValue());
				testData.setStatement(statement);
				testData.setLocatorType(readSheet.getRow(i).getCell(3).getStringCellValue());
				testData.setLocatorValue(readSheet.getRow(i).getCell(4).getStringCellValue());
				testData.setOperation(readSheet.getRow(i).getCell(5).getStringCellValue());
				//testData.setTextData(readSheet.getRow(i).getCell(6).getStringCellValue());
				
				Cell cell=readSheet.getRow(i).getCell(6);
				int cellType=cell.getCellType();
				String textData="";
				if (cellType == cell.CELL_TYPE_NUMERIC) {
					textData = "" + (int) cell.getNumericCellValue();
				} else {
					textData = cell.getStringCellValue();
				}
				
				testData.setTextData(textData);
				fData.put(key, testData);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			readSheet = null;
		}

		return fData;
	}

	
	/*
	 * Returns stored data in the form of key and value pair 
	 * keys - feature name:Possible value  
	 * values - Locator data in the TestData object for particular feature data
	 * @sheetName : sheet name from excel work book where test data is written 
	 */
	
	
	public Map<String, TestData> getSheetData(String sheetName) throws Exception {
		InputStream inputStream = null;
		Workbook readWorBook;
		File inputFile;
		Map<String, TestData> sheetData=new HashMap<String, TestData>();

		try {

			inputFile = new File(System.getProperty("user.dir") + "/TestData.xlsx");
			inputStream = new FileInputStream(inputFile);
			readWorBook = new XSSFWorkbook(inputStream);
			Sheet readSheet=readWorBook.getSheet(sheetName);	
			sheetData=storeExcelData(readSheet);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
		return sheetData;
	}

	

}
