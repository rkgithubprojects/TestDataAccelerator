package com.cigniti.airlines.exe;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cigniti.airlines.accelerators.GenerateReports;
import com.cigniti.airlines.accelerators.GenerateTestCase;
import com.cigniti.airlines.accelerators.GenerateTestScript;
import com.cigniti.airlines.accelerators.ReadExcel;
import com.cigniti.airlines.accelerators.ReadStaticSteps;
import com.cigniti.airlines.accelerators.StoreExcelData;
import com.cigniti.airlines.utils.TestData;

public class execute {

	
	public static void main(String[] args) throws Exception {
		try
		{
			String[] sheetNames={"DemoTestCase"};
						
			ReadExcel readExcel=new ReadExcel();
			Set<List<String>> excelData=readExcel.readExcelData(sheetNames[0]);
			
			StoreExcelData storeExcelData=new StoreExcelData();
			Map<String, TestData> storeData=storeExcelData.getSheetData(sheetNames[0]);
			
			ReadStaticSteps rd=new ReadStaticSteps();
			Map<String, List<TestData>> staticData=rd.readStaticData();
			
			GenerateTestCase tc=new GenerateTestCase();
			tc.generateTestCase(excelData, storeData, staticData, sheetNames[0]);
			
			GenerateTestScript generateScript=new GenerateTestScript();
			generateScript.runTestScript(excelData, storeData,staticData,sheetNames[0]);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			GenerateReports.getReports();
		}
	}
}
