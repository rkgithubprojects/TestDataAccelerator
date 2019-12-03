package com.cigniti.airlines.accelerators;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cigniti.airlines.utils.BaseClass;

public class ReadExcel extends BaseClass {

	/* Store Excel data in the form of keys and values keys -
	 * @values :feature name
	 * @List : all possible sub category
	 * @readSheet : excel sheet from which data is to be read
	 */

	public Map<String, List<String>> storePossibleValues(Sheet readSheet) {
		Map<String, List<String>> fData = null;
		try {
			fData = new HashMap<String, List<String>>();
			List<String> possibleValues = null;
			int rowsCount = readSheet.getLastRowNum() - readSheet.getFirstRowNum();
			String temp = "";

			for (int i = 1; i < rowsCount + 1; i++) {

				if (readSheet.getRow(i).getCell(0) != null
						&& !(readSheet.getRow(i).getCell(0).getStringCellValue().equals(""))) {
					temp = readSheet.getRow(i).getCell(0).getStringCellValue().trim();
					possibleValues = new ArrayList<String>();

					possibleValues.add(temp + ":" + readSheet.getRow(i).getCell(1).getStringCellValue().trim());
					fData.put(temp, possibleValues);

				} else {

					possibleValues.add(temp + ":" + readSheet.getRow(i).getCell(1).getStringCellValue().trim());
				}

				fData.put(temp, possibleValues);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			readSheet = null;
		}

		return fData;
	}

	/* Read excel data and store all possible combinations of test data in a Set. 
	 * Each possible combination is stored in a list And all the list
	 * values are store in a set
	 * @sheetName : name of the sheet from which data is to be read
	 */

	public Set<List<String>> readExcelData(String sheetName) throws Exception {
		InputStream inputStream = null;
		Workbook readWorBook;
		File inputFile;
		Set<List<String>> sheetsData = new HashSet<List<String>>();
		try {
			propData = getData();
			ReadTestData rd = new ReadTestData();
			varData = rd.readTestData();

			inputFile = new File(propData.get("ReadFile"));
			inputStream = new FileInputStream(inputFile);
			readWorBook = new XSSFWorkbook(inputStream);

			Sheet readSheet = readWorBook.getSheet(sheetName);

			sheetsData = generateCombinations(readSheet);

			Set<List<String>> businessRules = getBusinessRules(readWorBook, "BusinessRules");

			sheetsData = applyBusinessRules(sheetsData, businessRules);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
		return sheetsData;
	}

	/*
	 * Read Business rules sheet and store it in set Each list is the set of
	 * rule
	 * @readWorkBook : excel work book in which business rules are written
	 * @sheetName : Name of the sheet in which business rules are written
	 */
	private Set<List<String>> getBusinessRules(Workbook readWorBook, String sheetName) {
		Set<List<String>> bRules = new HashSet<>();
		try {
			Sheet bRulesSheet = readWorBook.getSheet(sheetName);
			int rowsCount = bRulesSheet.getLastRowNum() - bRulesSheet.getFirstRowNum();
			for (int i = 0; i < rowsCount; i++) {

				if (bRulesSheet.getRow(0).getCell(0) == null
						&& (bRulesSheet.getRow(0).getCell(0).getStringCellValue().equals(""))) {
					System.out.println(
							"There are no Business Rules Mentioned in the sheet (or)  Please enter the rules in correct format");
					break;
				} else {
					Row row = bRulesSheet.getRow(i);

					int cellSize = row.getLastCellNum() - row.getFirstCellNum();

					List<String> rule = new ArrayList<>();

					for (int j = 0; j < cellSize; j++) {
						
						rule.add(row.getCell(j).getStringCellValue());
					}

					bRules.add(rule);
				}
			}
			System.out.println("Business Rules \n" + bRules);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bRules;
	}

	/*
	 * Generates all combinations of given test data Returns all combinations of
	 * test data in the form of set Each list is one combination of test data
	 * @readSheet : Sheet in test data is written
	 */

	public Set<List<String>> generateCombinations(Sheet readSheet) throws Exception {
		Set<List<String>> combs = new HashSet<List<String>>();
		try {
			Map<String, List<String>> featureData = new HashMap<String, List<String>>();
			String featureNames[] = null;

			featureData = storePossibleValues(readSheet);

			// Get features size from the excel
			featureNames = new String[featureData.size()];
			featureNames = storeKeyValuesIntoArray(featureData, readSheet);

			List<List<String>> lists = new ArrayList<List<String>>();

			// Write all feature names in array
			for (int i = 0; i < featureNames.length; i++) {

				if (featureData.get(featureNames[i]) != null) {
					lists.add(featureData.get(featureNames[i]));
				}
			}

			// Get all combinations of data from the read excel data and store
			// each combination in an set
			combs = getCombinations(lists);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return combs;
	}

	/*
	 * Store all feature values into String array 
	 * @featureData : map with feature name as key and possible values as values
	 * @readSheet : excel sheet object from which data is read
	 */

	private String[] storeKeyValuesIntoArray(Map<String, List<String>> featuresData, Sheet readSheet) {
		int c = 0;
		String[] keys = null;

		try {
			int rowCount = readSheet.getLastRowNum() - readSheet.getFirstRowNum();
			keys = new String[featuresData.size()];
			for (int i = 1; i < rowCount; i++) {
				if (readSheet.getRow(i).getCell(0) != null
						&& !(readSheet.getRow(i).getCell(0).getStringCellValue().equals(""))) {
					keys[c] = readSheet.getRow(i).getCell(0).getStringCellValue();
					c++;
					if (c == keys.length - 1) {
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keys;
	}

	
	/*
	 * Generates all combinations of data from the given data in lists
	 * @lists : input data in the form of list for which the combinations are to
	 * be generated
	 */

	public static <T> Set<List<T>> getCombinations(List<List<T>> lists) {

		Set<List<T>> combinations = new HashSet<List<T>>();
		Set<List<T>> newCombinations;

		int index = 0;
		for (T i : lists.get(0)) {
			List<T> newList = new ArrayList<T>();
			newList.add(i);
			combinations.add(newList);
		}
		index++;
		while (index < lists.size()) {
			List<T> nextList = lists.get(index);
			newCombinations = new HashSet<List<T>>();
			for (List<T> first : combinations) {
				for (T second : nextList) {
					List<T> newList = new ArrayList<T>();
					newList.addAll(first);
					newList.add(second);
					newCombinations.add(newList);
				}
			}
			combinations = newCombinations;
			index++;
		}
		return combinations;
	}

	/*
	 * Removes test case combinations from the generated combinations
	 * data(sheetsData) for which business rules(bRules) are applicable
	 * @sheetsData : @input : Set : combination of test data in the form of list.
	 * @bRules : Set of business Rules ,Each List is a set of rule
	 */

	public Set<List<String>> applyBusinessRules(Set<List<String>> sheetsData, Set<List<String>> bRules) {
		Set<List<String>> Tc_With_Brules = new HashSet<>();
		Set<List<String>> Tc_Without_Brules = new HashSet<>();

		try {
			if (bRules != null) {
				for (List<String> dataList : sheetsData) {

					String temp = "";

					for (int i = 0; i < dataList.size(); i++) {
						temp = temp + "," + dataList.get(i);
					}

					for (List<String> rulesList : bRules) {

						if (isSubset(temp, rulesList)) {
							Tc_With_Brules.add(dataList);
							break;
						} else {
							Tc_Without_Brules.add(dataList);
						}
					}
				}
				sheetsData.removeAll(Tc_With_Brules);
				System.out.println("Filtered Data" + sheetsData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sheetsData;
	}

	public static boolean isSubset(String temp, List<String> rulesSet) {
		int count = 0;
		boolean flag = false;

		for (int i = 0; i < rulesSet.size(); i++) {

			if (temp.toLowerCase().contains(rulesSet.get(i).toLowerCase())) {
				count++;
			}
		}
		if (count >= rulesSet.size()) {
			flag = true;
		}

		return flag;
	}
}
