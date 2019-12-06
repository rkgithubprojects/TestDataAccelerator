package com.cigniti.airlines.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cigniti.airlines.reports.Result;


public class BaseClass extends UtilitiesClass {
	//boolean status=false;
	/*
	 * Method to select number of adult passengers in flight search page
	 * @locator : locator value
	 * @paxCount : no of adult passenger count
	 */

	public boolean selectAdultPaxCount(By locator, int paxCount) {
		boolean flag = false;
		try {
			highlightElement(locator);
			WebElement ele = driver.findElement(locator);
			ele.click();

			if (paxCount != 0) {
				for (int i = 1; i < paxCount; i++) {
					driver.findElement(By.xpath("//*[contains(@class,'plus')]")).click();
				}
			}
			flag=true;
		} catch (Exception e) {
			flag = false;
			throw e;
		}
		return flag;
	}

	/*
	 * Method to select number of senior passengers in flight search page
	 * @locator : locator value
	 * @paxCount : no of senior passenger count
	 */

	public boolean selectSeniorPaxCount(By locator, int paxCount){
		boolean flag = false;
		try {
			highlightElement(locator);
			WebElement ele = driver.findElement(locator);
			ele.click();

			if (paxCount != 0) {
				for (int i = 0; i < paxCount; i++) {
					driver.findElement(By.xpath("//*[contains(@class,'plus')]")).click();
				}
			}
			flag=true;
		} catch (Exception e) {
			flag = false;
			throw e;
		}
		return flag;
	}

	/*
	 * Method to verify text
	 * @locator : locator value
	 * @expectedData : expected string value
	 * @statement : operation description/explanation
	 */

	private boolean verifyText(By locator, String expectedData, String statement) {
		boolean flag=false;
		try {
			highlightElement(locator);
			String actual = driver.findElement(locator).getText().toLowerCase().trim();
			String expected = expectedData.toLowerCase().trim();
			flag= actual.contains(expected);
		} 
		catch (Exception e) {
			flag=false;
			throw e;
		}
		return flag;
	}

	/*
	 * Method to click on selected element
	 * @locator : element to be clicked locator value
	 */
	
	public boolean clickOnElement(By locator) throws Exception {
		boolean flag=false;
		try {
			highlightElement(locator);
			driver.findElement(locator).click();
			flag=true;
		} catch (Exception e) {
			flag=false;
			throw e;
		}
		return flag;
	}
	
	private boolean selectFlight(By locator){
		boolean flag=false;
		List<WebElement> flightList=null;
		try
		{
			flightList = driver.findElements(locator);
			WebElement flight = flightList.get(getRandomInt(0,flightList.size() - 1));
			flightNumber = flight.getAttribute("data-ref");
			System.out.println("flightNumber>>>> " + flightNumber);
			flight.click();
			flag=true;
			
		}
		catch(Exception e)
		{
			flag=false;
			throw e;
		}
		return flag;
	}
	
	public boolean clickOnElementByUsingJSE(By locator) throws Exception {
		boolean flag=false;
		try {
			highlightElement(locator);
			WebDriverWait wd = new WebDriverWait(driver,5);
			wd.until(ExpectedConditions.elementToBeClickable(driver.findElement(locator)));
			Thread.sleep(5000);
			((JavascriptExecutor) driver)
		     .executeScript("arguments[0].click();", driver.findElement(locator));
			flag=true;
		} catch (Exception e) {
			flag=false;
			throw e;
		}
		return flag;
	}

	/*
	 * Method to perform operation on selected element
	 * @operation : Type of Operation to be performed
	 * @locatorType : Locator Type to find element to perform operation
	 * @locatorValue : locator value
	 * @category : possible value that is selected for the selected feature type
	 * @textData : test data value from excel sheet
	 * @statement : explanation of step that is currently performed
	 */

	public void perform(String operation, String locatorType, String locatorValue, String category, String textData,
			String statement) throws Exception {
		
		boolean status=false;
		
		try {
			switch (operation.toUpperCase()) {
			case "CLICK":
				status=clickOnElement(getLocator(locatorType, locatorValue));
				break;
				
			case "JSECLICK":
				status=clickOnElementByUsingJSE(getLocator(locatorType, locatorValue));
				break;

			case "SENDTEXT":
				status=sendText(getLocator(locatorType, locatorValue), textData);
				break;

			case "NAVIGATE":
				status = navigateToUrl(textData);
				break;

			case "GETTEXT":
				String text=getText(getLocator(locatorType, locatorValue));
				status=(text!="")&&(text!=null);
				break;

			case "VERIFYTEXT":
				status=verifyText(getLocator(locatorType, locatorValue), textData, statement);
				break;

			case "SELECTADULTPAXCOUNT":
				adultsCount = Integer.parseInt(textData);
				status=selectAdultPaxCount(getLocator(locatorType, locatorValue), adultsCount);
				break;

			case "SELECTSENIORPAXCOUNT":
				seniorsCount = Integer.parseInt(textData);
				status=selectSeniorPaxCount(getLocator(locatorType, locatorValue), seniorsCount);
				break;

			case "SELECTOUTFLIGHT":
				status=selectOutFlight();
				break;

			case "SELECTINFLIGHT":
				status=selectInFlight();
				break;

			case "VERIFYELEMENT":
				status=verifyElement(getLocator(locatorType, locatorValue));
				break;

			case "MAKEPAYMENT":
				status=selectByVisibleText(getLocator(locatorType, locatorValue), textData);
				System.out.println(statement.replace("$", category) + " --  Passed");
				fillTravellerInfo();
				fillData("CONTACTDETAILS");
				fillData("PAYMENTDETAILS");
				fillData("RECEIPTDETAILS");
				break;

			case "SELECT":
				status=selectByVisibleText(getLocator(locatorType, locatorValue), textData);
				break;

			case "SELECTBYVALUE":
				status=selectByValue(getLocator(locatorType, locatorValue), textData);
				break;

			case "SELECTMONTH":
				String month =selectMonth(getLocator(locatorType, locatorValue));
				status=(month!=null)&&(month.length()!=0);
				break;

			case "SELECTYEAR":
				String year=selectYear(paxType, getLocator(locatorType, locatorValue));
				status=(year.length()!=0)&&(year!=null);
			break;

			case "SELECTDAY":
				int day=Integer.parseInt(textData);
				day=selectDay(getLocator(locatorType, locatorValue),day);
				status=(day!=0);
				break;
			case "SELECTDATE":
				//int day1=0;
				int day1=Integer.parseInt(textData);
				day1=selectDay(getLocator(locatorType, locatorValue),day1);
				status=(day1!=0);
				break;
			case "SELECTOUTBOUNDSENIORFARE":
				status=selectOBseniorFare(getLocator(locatorType, locatorValue));
			break;

			case "SELECTINBOUNDSENIORFARE":
				status=selectIBseniorFare(getLocator(locatorType, locatorValue));
				break;

			case "SUBMITSENIORFARE":
				status=submitSeniorFare(getLocator(locatorType, locatorValue));
			break;
			
			case "VERIFYVISIBILITY":
				status=visibilityOfElementLocated(getLocator(locatorType, locatorValue));
			break;
			
			case "VERIFYINVISIBILITY":
				status=invisibilityOfElementLocated(getLocator(locatorType, locatorValue));
			break;
			
			case "LOGIN":
				status= fillData("LOGINDETAILS");
			break;
			
			case "ENTERPASSENGERDETAILS":
				status= fillData("PASSENGERDETAILS");
			break;
			
			case "CABINBAGSELECTION":
				status= fillData("BAGDETAILS");
			break;
			
			case "SEATSELECTION":
				status= fillData("SEATSELECTIONDETAILS");
			break;
			
			case "SCROLLTOBOTTOM":
				status= scrollToBottom();
			break;
			
			case "BOOKTICKET":
				status= fillData("PAYMENTINFORMATION");
			break;
			
			case "GETPNR":
				pnrNumber=getText(getLocator(locatorType, locatorValue));
				status=(pnrNumber!="")&&(pnrNumber!=null);
				break;
				
			case "GETJOURNEYDATE":
				journeyDate=getText(getLocator(locatorType, locatorValue));
				status=(journeyDate!="")&&(journeyDate!=null);
				break;
				
			case "GETORIGIN":
				origin=getText(getLocator(locatorType, locatorValue));
				status=(origin!="")&&(origin!=null);
				break;
				
			case "GETDESTINATION":
				destination=getText(getLocator(locatorType, locatorValue));
				status=(destination!="")&&(destination!=null);
				break;
				
			case "SELECTFLIGHT":
				status=selectFlight(getLocator(locatorType, locatorValue));
				break;
				
				
			default:
				System.out.println("The operation Type [ " + operation + " ] is not defined");
			break;
			}
		} catch (TimeoutException tx) {
			
			Actions action = new Actions(driver);
			action.sendKeys(Keys.ESCAPE);
			
		}
		catch(NoSuchElementException nse)
		{
			status = false;
			System.out.println("Element with [locator Type :"+locatorValue+"] and [locator value :"+locatorValue+"] is not found");
			throw nse;
		}
		catch (Exception e) {
			status =false;
			e.printStackTrace();
		}
		finally
		{
			Result result = null;
			if (status) {
				System.out.println(statement.replace("$", category) + " --  Passed");
				result = new Result(operation, stepCount++, statement.replace("$", category), true, "");
			} else {
				System.out.println(statement.replace("$", category) + " --  Failed");
				String screenshotpath = takeScreenShot();
				result = new Result(operation, stepCount++, statement.replace("$", category), false, screenshotpath);
				
				
			}
			
			reports.add(result);
		}

	}

	private String getText(By locator){
		String text="";
		try
		{
			highlightElement(locator);
			text=driver.findElement(locator).getText();
		}
		catch(Exception e)
		{
			throw e;
		}
		return text;
	}

	private boolean navigateToUrl(String url) {
		boolean flag=false;
		try {
			driver.get(url);
			flag=true;
		} catch (Exception e) {
			flag=false;
			throw e;
		}
		return flag;
	}

	/*
	 * Click on submit button in select senior fare page
	 * @locator : locator value
	 */
	private boolean submitSeniorFare(By locator) {
		boolean flag=false;
		try {
			if (seniorsCount != 0) {
				highlightElement(locator);
				driver.findElement(locator).click();
			}
			flag=true;
		} catch (Exception e) {
			flag=false;
			throw e;
		}
		return flag;
	}

	
	/*
	 * Fill all adult and senior passenger details in purchase page travelers
	 * info section
	 */
	
	private void fillTravellerInfo() throws Exception {
		boolean flag=false;
		try {
			int paxCount = adultsCount + seniorsCount;
			for (int i = 0; i < paxCount; i++) {
				if (i < adultsCount) {
					paxType = "ADULT";
					fillData("PAXDETAILS" + i);
				} else if (i >= adultsCount && i < paxCount) {
					paxType = "SENIOR";
					fillData("PAXDETAILS" + i);
				}
			}
			flag=true;
		} catch (Exception e) {
			flag=false;
			throw e;
		}
	}


	
	private boolean visibilityOfElementLocated(By locator) {
		boolean flag=false;
		try {
			WebDriverWait wd = new WebDriverWait(driver,5);
			wd.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(locator)));
			flag = true;
		} catch (Exception e) {
			flag=false;
			throw e;
			}
		return flag;
	}
	
	private boolean invisibilityOfElementLocated(By locator) {
		boolean flag=false;
		try {
			WebDriverWait wd = new WebDriverWait(driver,5);
			wd.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(locator)));
			flag = true;
		} catch (Exception e) {
			flag=false;
			throw e;
			}
		return flag;
	}
	
	private boolean scrollToBottom() {
		boolean flag=false;
		try {
			((JavascriptExecutor) driver)
		     .executeScript("window.scrollTo(0, document.body.scrollHeight)");
			flag = true;
		} catch (Exception e) {
			flag=false;
			throw e;
			}
		return flag;
	}
	
	
	/*
	 * function to verify if element is present
	 * @locator : locator value
	 */
	
	private boolean verifyElement(By locator) {
		boolean flag=false;
		try {
			highlightElement(locator);
			flag=driver.findElement(locator).isDisplayed();
		} catch (Exception e) {
			flag=false;
			throw e;
		}
		return flag;
	}
	
	private boolean selectByValue(By locator, String textData)  {
		boolean flag=false;
		try {
			highlightElement(locator);
			Select sel = new Select(driver.findElement(locator));
			sel.selectByValue(textData);
			flag=true;
		} catch (Exception e) {
			flag=false;
			throw e;
		}
		return flag;
	}

	private boolean sendText(By locator, String textData) {
		boolean flag=false;
		try {
			highlightElement(locator);
			driver.findElement(locator).clear();
			driver.findElement(locator).sendKeys(textData);
			flag=true;
		} catch (Exception e) {
			flag=false;
			throw e;
		}
		return flag;
	}

	private boolean selectByVisibleText(By locator, String textData) {
		boolean flag = false;
		try {
			highlightElement(locator);
			Select select = new Select(driver.findElement(locator));
			select.selectByVisibleText(textData);
			flag = true;
		} catch (Exception e) {
			flag = false;
			throw e;
		}
		return flag;
	}

	private String selectMonth(By locator) {
		String selectedMonth="";
		try {
			highlightElement(locator);
			Random rm = new Random();
			int startMonth = 1;
			int endMonth = 12;
			int month = rm.nextInt(endMonth - startMonth) + startMonth;
			Select monthDD = new Select(driver.findElement(locator));
			monthDD.selectByIndex(month);
			selectedMonth=monthDD.getFirstSelectedOption().getText();
			
		} catch (Exception e) {
			throw e;
		}
		return selectedMonth;
	}

	private int selectDay(By locator,int day) {
		//int day=0;
		try {
			highlightElement(locator);
			Random rd = new Random();
			int startDay = 1;
			int endDay = 29;
			 day = rd.nextInt(endDay - startDay) + startDay;
			/*Select dayDD = new Select(driver.findElement(locator));
			dayDD.selectByIndex(day);*/
		} catch (Exception e) {
			throw e;
		}
		return day;
	}

	private String selectYear(String paxType, By locator) {
		String yearInString="";
		try {
			highlightElement(locator);
			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);

			switch (paxType.toUpperCase()) {
			case "SENIOR":
				year = year - 60;
				break;
			default:
				year = year - 18;
				break;
			}

			yearInString = String.valueOf(year);
			Select yearDd = new Select(driver.findElement(locator));
			yearDd.selectByVisibleText(yearInString);
		} catch (Exception e) {
			throw e;
		}
		return yearInString;
	}

	public List<String> getTestCaseSteps(TestData testData) throws Exception {
		List<String> steps = new ArrayList<>();
		String operation = testData.getOperation().toUpperCase();
		try {

			switch (operation.toUpperCase()) {

			case "MAKEPAYMENT":
				for (int i = 0; i < totalPax; i++) {
					steps.addAll(getSteps("PAXDETAILS" + i));
				}
				steps.addAll(getSteps("CONTACTDETAILS"));
				steps.addAll(getSteps("PAYMENTDETAILS"));
				steps.addAll(getSteps("RECEIPTDETAILS"));
				break;
			
			case "SELECTADULTPAXCOUNT":
				break;
			
			case "SELECTSENIORPAXCOUNT":
				break;

			default:
				
				break;
			}

		} catch (TimeoutException tx) {

		} catch (Exception e) {
			throw e;
		}
		return steps;
	}

	public List<String> getSteps(String featureType) {
		List<String> steps = new ArrayList<>();
		try {
			List<TestData> paxData = varData.get(featureType);
			for (TestData testData : paxData) {
				steps.add(testData.getStatement().replace("$", testData.getTextData()));
			}
		} catch (Exception e) {
			throw e;
		}
		return steps;
	}

	public boolean fillData(String category) throws Exception {
		boolean flag=false;
		try {
			List<TestData> paxData = varData.get(category);
			for (TestData testData : paxData) {
				perform(testData.getOperation(), testData.getLocatorType(), testData.getLocatorValue(),
						testData.getTextData(), testData.getTextData(), testData.getStatement());
			}
			flag=true;
		} catch (Exception e) {
			flag=false;
			throw e;
		}
		return flag;
	}

	private boolean selectOutFlight() {
		boolean flag=false;
		try {
			System.out.println("Select Out bound flight logic need to be implemented");
			highlightElement(By.xpath("//input[@name='outboundTrip']"));
			driver.findElement(By.xpath("//input[@name='outboundTrip']")).click();
			flag=true;
		} catch (Exception e) {
			flag=false;		
			throw e;
		}
		return flag;
	}

	private boolean selectInFlight() {
		boolean flag=false;
		try {
			highlightElement(By.xpath("//input[@name='inboundTrip']"));
			System.out.println("Select In bound flight logic need to be implemented");
			driver.findElement(By.xpath("//input[@name='inboundTrip']")).click();
			flag=true;
		} catch (Exception e) {
			flag=false;
			throw e;
		}
		return flag;
	}

	private boolean selectOBseniorFare(By locator) {
		boolean flag=false;
		try {
			if (seniorsCount > 0) {
				highlightElement(locator);
				driver.findElement(locator).click();
				flag=true;
			}
		} catch (Exception e) {
			flag=false;
			throw e;
		}
		return flag;
	}

	private boolean selectIBseniorFare(By locator) {
		boolean flag=false;
		try {
			if (seniorsCount > 0 && !isOneWay) {
				highlightElement(locator);
				driver.findElement(locator).click();
			}
			flag=true;
		} catch (Exception e) {
			flag=false;
			throw e;
		}
		return flag;
	}

}
