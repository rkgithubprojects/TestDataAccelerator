package com.cigniti.airlines.accelerators;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.cigniti.airlines.utils.TestData;
import com.cigniti.airlines.utils.UtilitiesClass;

public class ReadStaticSteps extends UtilitiesClass{

	/*
	 * Read all static steps and verification steps in the form of key value pair
	 * @key : String : feature Name 
	 * @value : Test Data object : locators data inthe for of TestData object  
	 */
	public Map<String, List<TestData>> readStaticData() throws Exception {
		InputStream inputStream = null;
		Workbook readWorBook;
		File inputFile;
		Map<String, List<TestData>> staticData = new HashMap<>();

		try {
			inputFile = new File(propData.get("ReadFile"));
			inputStream = new FileInputStream(inputFile);
			readWorBook = new XSSFWorkbook(inputStream);
			Sheet readSheet = readWorBook.getSheet("StaticSteps");
			staticData = storePossibleValues(readSheet);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
		return staticData;
	}
	
	
	/*
	 * Returns excel sheet data in the form of key and value pair
	 * @key : String : featureName
	 * @value :List : Testdata object ,locators data from verification/static steps sheet
	 * @readSheet :Sheet object from which static data test data is read. 
	 */
	
	public Map<String, List<TestData>> storePossibleValues(Sheet readSheet) {
		Map<String, List<TestData>> fData = null;
		try {
			fData = new HashMap<String, List<TestData>>();
			List<TestData> possibleValues = null;
			int rowsCount = readSheet.getLastRowNum() - readSheet.getFirstRowNum();
			String temp = "";

			for (int i = 1; i < rowsCount + 1; i++) {

				if (readSheet.getRow(i).getCell(0) != null
						&& !(readSheet.getRow(i).getCell(0).getStringCellValue().equals(""))) {
					temp = readSheet.getRow(i).getCell(0).getStringCellValue().trim();
					possibleValues = new ArrayList<TestData>();

					TestData testData = new TestData();

					testData.setCategory(temp);
					testData.setStatement(readSheet.getRow(i).getCell(1).getStringCellValue());
					testData.setLocatorType(readSheet.getRow(i).getCell(2).getStringCellValue());
					testData.setLocatorValue(readSheet.getRow(i).getCell(3).getStringCellValue());
					testData.setOperation(readSheet.getRow(i).getCell(4).getStringCellValue());
					System.out.println(temp + "--- " + readSheet.getRow(i).getCell(4).getStringCellValue());
					testData.setTextData(readSheet.getRow(i).getCell(5).getStringCellValue());
					possibleValues.add(testData);

				} else {
					TestData testData = new TestData();
					testData.setCategory(temp);
					testData.setStatement(readSheet.getRow(i).getCell(1).getStringCellValue());
					testData.setLocatorType(readSheet.getRow(i).getCell(2).getStringCellValue());
					testData.setLocatorValue(readSheet.getRow(i).getCell(3).getStringCellValue());
					testData.setOperation(readSheet.getRow(i).getCell(4).getStringCellValue());
					testData.setTextData(readSheet.getRow(i).getCell(5).getStringCellValue());

					possibleValues.add(testData);
				}
				fData.put(temp, possibleValues);
			}
			// displayStaticSteps(fData);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			readSheet = null;
		}

		return fData;
	}

	/*
	 * Method to dispaly all read static data from excel sheet
	 */
	public void displayStaticSteps(Map<String, List<TestData>> fData)
	{
		try
		{
			for (String key : fData.keySet()) {
				
				for (TestData data : fData.get(key)) {
					
					System.out.println("Category "+data.getCategory());
					System.out.println("Category "+data.getLocatorType());
					System.out.println("Category "+data.getLocatorValue());
					System.out.println("Category "+data.getTextData());
					System.out.println("Category "+data.getStatement());
					System.out.println("Category "+data.getOperation());
					System.out.println("\n\n");
				}
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
