package com.cigniti.airlines.accelerators;


import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.cigniti.airlines.utils.BaseClass;
import com.cigniti.airlines.utils.TestData;



public class GenerateTestScript extends BaseClass {

	boolean result=true;
	
	/*
	 * Method to run test script
	 * @excelData : all possible combinations of testData in form of set
	 * @storeData : [@key : feature+possibleValue] and [@value :locator data in form of TestData object]
	 * @staticData : [@key : feature] and value [@value: locators data in the form of TestData object]
	 * @sheetData : sheet name for which test scripts are executed  
	 */
	
	public void runTestScript(Set<List<String>> excelData, Map<String, TestData> storeData,
			Map<String, List<TestData>> staticData, String sheetName) {
		try {

			currentSheetName=sheetName;
			executionStartTime=getCurrentTime();
			
			for (List<String> list : excelData) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/chromedriver.exe");
				ChromeOptions opt = new ChromeOptions();
				opt.addArguments("disable-infobars");
				opt.addArguments("--start-maximized");
				opt.addArguments("--disable-extensions");
				driver = new ChromeDriver(opt);
				driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				initialize(staticData);

				for (String key : list) {

					TestData data = storeData.get(key);
					
					String featureType = data.getFeatureType();
					String locatorType = data.getLocatorType();
					String operation = data.getOperation();
					String statement = data.getStatement();
					String locatorValue = data.getLocatorValue();
					String category = data.getCategory();
					String textData = data.getTextData();

					if (category.equalsIgnoreCase("ONEWAY")) {
						isOneWay = true;
					}
					try
					{
						perform(operation, locatorType, locatorValue, category, textData, statement);
						executeStaticSteps(staticData, data);
					}
					catch(Exception e)
					{
						closeDriver();
						result=false;
						break;
					}
				}
				if(result)
				{
					closeDriver();
				}
				stepCount=1;
				tcCount++;
			
				//break;
				if(tcCount==3)
					{break;}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally{
			executionEndTime=getCurrentTime();
		}
	}

	
	/*
	 * Method to execute verification/static steps
	 * @staticData : [@key : feature] and value [@value: locators data in the form of TestData object]
	 * @testData : Locators data in the form of TestData object
	 */
	public void executeStaticSteps(Map<String, List<TestData>> staticData, TestData testData) {
		try {
			List<TestData> staticSteps = new ArrayList<>();
			staticSteps = getValidStaticSteps(staticData, testData);
			if (staticSteps != null && staticSteps.size() > 0) {
				for (TestData data : staticSteps) {
					perform(data.getOperation(), data.getLocatorType(), data.getLocatorValue(), "", data.getTextData(),
							data.getStatement());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * get valid steps depending upon the test case combination.
	 * @staticData : [@key : feature] and value [@value: locators data in the form of TestData object]
	 * @testData : Locators data in the form of TestData object
	 */
	
	private List<TestData> getValidStaticSteps(Map<String, List<TestData>> staticData, TestData testData) {
		List<TestData> staticSteps = new ArrayList<>();
		try {

			staticSteps = staticData.get(testData.getFeatureType());
			if ((!isOneWay) && testData.getFeatureType().equalsIgnoreCase("CabinClassOut"))
			{
				staticSteps.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return staticSteps;
	}

	
	/*
	 * Method to run init steps
	 */
	private void initialize(Map<String, List<TestData>> staticData) {
		try {
			List<TestData> initData = staticData.get("InitialSteps");
			for (TestData testData : initData) {
				perform(testData.getOperation(), testData.getLocatorType(), testData.getLocatorValue(), "",
						testData.getTextData(), testData.getStatement());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Method to open application URL
	 */
	public void openUrl() {
		try {
			driver.get(propData.get("URL"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Method to close browser
	 */
	
	public void closeDriver() {
		try {
			driver.close();
		} catch (Exception e) {
			throw e;
		}
	}


}
