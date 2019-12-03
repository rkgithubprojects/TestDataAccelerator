package com.cigniti.airlines.accelerators;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cigniti.airlines.utils.TestData;
import com.cigniti.airlines.utils.UtilitiesClass;

public class ReadTestData extends UtilitiesClass{
	

	/* Store Excel data in the form of keys and value pair
	 * @key : feature name
	 * @value : List<TestData> list of locators data in the form of test data object
	 */
	public Map<String, List<TestData>> readTestData() throws Exception 
	{
		InputStream inputStream = null;
		Workbook readWorBook;
		File inputFile;
		Map<String, List<TestData>> testData=new HashMap<>();
		
		try {
			inputFile = new File(propData.get("ReadFile"));
			inputStream = new FileInputStream(inputFile);
			readWorBook = new XSSFWorkbook(inputStream);
			Sheet dataSheet = readWorBook.getSheet("TestData");
			testData=storePossibleData(dataSheet);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
		return testData;
	}

	/* Get all static Excel data in the form of keys and value pair
	 * @key : feature name
	 * @value : List<TestData> list of locators data in the form of test data object
	 * @dataSheet : Sheet object in which all store data
	 */
	private Map<String, List<TestData>> storePossibleData(Sheet dataSheet) {
		Map<String, List<TestData>> fData = null;
		try {
			
			fData = new HashMap<String, List<TestData>>();
			List<TestData> possibleValues = null;
			int rowsCount = dataSheet.getLastRowNum() - dataSheet.getFirstRowNum();
			String temp = "";

			for (int i = 1; i < rowsCount + 1; i++) {
				
				if (dataSheet.getRow(i).getCell(0) != null && !(dataSheet.getRow(i).getCell(0).getStringCellValue().equals(""))) {
					temp = dataSheet.getRow(i).getCell(0).getStringCellValue().trim();
					possibleValues = new ArrayList<TestData>();

					TestData testData=new TestData();
					
					testData.setCategory(temp);
					testData.setStatement(dataSheet.getRow(i).getCell(2).getStringCellValue());
					testData.setLocatorType(dataSheet.getRow(i).getCell(3).getStringCellValue());
					testData.setLocatorValue(dataSheet.getRow(i).getCell(4).getStringCellValue());
					testData.setOperation(dataSheet.getRow(i).getCell(5).getStringCellValue());
					testData.setTextData(dataSheet.getRow(i).getCell(6).getStringCellValue());
					possibleValues.add(testData);

				} else {
					TestData testData=new TestData();
					testData.setCategory(temp);
					testData.setStatement(dataSheet.getRow(i).getCell(2).getStringCellValue());
					testData.setLocatorType(dataSheet.getRow(i).getCell(3).getStringCellValue());
					testData.setLocatorValue(dataSheet.getRow(i).getCell(4).getStringCellValue());
					testData.setOperation(dataSheet.getRow(i).getCell(5).getStringCellValue());
					
					Cell cell=dataSheet.getRow(i).getCell(6);
					int cellType=cell.getCellType();
					String textData="";
					if(cellType==cell.CELL_TYPE_NUMERIC)
					{
						textData=""+(int)cell.getNumericCellValue();
					}
					else
					{
						textData=cell.getStringCellValue();
					}
					
					testData.setTextData(textData);
				
					possibleValues.add(testData);
				}
				fData.put(temp, possibleValues);
			}

			//displayStaticSteps(fData);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dataSheet = null;
		}

		return fData;
		}

	/*
	 * Method to display the test data read from excel sheet
	 */
	public void displayStaticSteps(Map<String, List<TestData>> fData)
	{
		try
		{
			List<TestData> data=fData.get("PAXDETAILS");
			for (TestData testData : data) {
				System.out.println("Category "+testData.getCategory());
				System.out.println("Locator Type "+testData.getLocatorType());
				System.out.println("Locator Value "+testData.getLocatorValue());
				System.out.println("Text Data "+testData.getTextData());
				System.out.println("Statement "+testData.getStatement());
				System.out.println("Operation "+testData.getOperation());
				System.out.println("\n\n");
			}		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	}

