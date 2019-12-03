package com.cigniti.airlines.accelerators;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cigniti.airlines.reports.Result;
import com.cigniti.airlines.utils.BaseClass;

public class GenerateReports extends BaseClass{

	public static void getReports()
	{
		try {
			Writer writer = null;
			totalPax=adultsCount+seniorsCount;
			int count=0;

			File file = new File(System.getProperty("user.dir") + "/ScreenShots/SummaryReports.html");

			writer = new FileWriter(file, false);
			writer.write("<html><body bgcolor='#000000'>");
			writer.write("<center>");
			writer.write("<br><br><br>");

			/************************* Table 1 **************************/

			writer.write(
					"<table width='90%'  border=1><tr bgcolor='violet'><th align='center' width='30%'><img https://cignitiweb-e5e3.kxcdn.com/wp-content/uploads/logo.png' alt='Cigniti Technologies'/></th><th align='center'>RYANAIR REPORTS</th></tr></table>");
			writer.write("<br>");

			/************************* Table 2 **************************/

			writer.write("<table width='90%' border='1' bgcolor='ffcccc'>");

			writer.write("<tr width='100%'>");
			writer.write("<td width='30%' align='center'><b><font size='2'>URL</font></b></td>");
			writer.write("<td width='70%' align='center'><b><i><font size='2' color='red'>" + propData.get("URL")
					+ "</font><i></b></td>");
			writer.write("</tr>");

			writer.write("<tr width='100%'>");
			writer.write("<td width='30%' align='center'><b><font size='2'>BROWSER</font></b></td>");
			writer.write("<td width='70%' align='center'><b><i><font size='2' color='red'>"+ "Chrome" + "</font><i></b></td>");
			writer.write("</tr>");

			writer.write("<tr width='100%'>");
			writer.write("<td width='30%' align='center'><b><font size='2'>EXECUTION START-UP TIME</font></b></td>");
			writer.write("<td width='70%' align='center'><b><i><font size='2' color='red'>" + executionStartTime+ "</font><i></b></td>");
			writer.write("</tr>");
			
			writer.write("<tr width='100%'>");
			writer.write("<td width='30%' align='center'><b><font size='2'>EXECUTION END TIME</font></b></td>");
			writer.write("<td width='70%' align='center'><b><i><font size='2' color='red'>" + executionEndTime
					+ "</font><i></b></td>");
			writer.write("</tr>");
/*			
			writer.write("<tr width='100%'>");
			writer.write("<td width='30%' align='center'><b><font size='2'>TOTAL PASSENGERS</font></b></td>");
			writer.write("<td width='70%' align='center'><b><i><font size='2' color='red'>"
					+ totalPax + "</font><i></b></td>");
			writer.write("</tr>");
			
			writer.write("<tr width='100%'>");
			writer.write("<td width='30%' align='center'><b><font size='2'>ADULT PASSENGERS</font></b></td>");
			writer.write("<td width='70%' align='center'><b><i><font size='2' color='red'>"
					+ adultsCount + "</font><i></b></td>");
			writer.write("</tr>");
			
			writer.write("<tr width='100%'>");
			writer.write("<td width='30%' align='center'><b><font size='2'>SENIOR PASSENGERS</font></b></td>");
			writer.write("<td width='70%' align='center'><b><i><font size='2' color='red'>"
					+ seniorsCount + "</font><i></b></td>");
			writer.write("</tr>");
*/			
			writer.write("</table><br>");

			/************************* Table 3 **************************/

			writer.write("<table width='90%' border='1'>");
			writer.write("<tr bgcolor='00ff40' height='35'>");
			writer.write("<th width='10%'><font size='3' color='0000ff'><b>STEP NO</b></font></th>");
			writer.write("<th width='30%'><font size='3' color='0000ff'>" + "OPERATION" + "</font></th>");
			writer.write("<th width='45%'><font size='3' color='0000ff'>" + "DESCRIPTION" + "</font></th>");
			writer.write("<th width='25%'><font size='3' color='0000ff'>"+"STATUS"+"</font></th>");
			writer.write("</tr>");
			writer.write("</table>");
				
			for (Result result : reports)
			{
				
				if(result.getStepCount()==1)
				{
					writer.write("<br><table width='90%' border='1'>");
					writer.write("<tr bgcolor='#00FFFF' height='30'>");
					String tcName=currentSheetName.toUpperCase()+"_"+count++;
					writer.write("<th>  <font size='4' >" + tcName + "</font></th>");
					writer.write("</tr></table><br>");
				}
				
				writer.write("<table width='90%' >");
				writer.write("<tr bgcolor='ffffe6' height='30'>");
				writer.write("<th width='10%'><font size='3' >" + result.getStepCount()+ "</font></th>");
				writer.write("<th width='30%'><font size='3' color='0000ff' align='left'>" + result.getOperation()+ "</font></th>");
				writer.write("<th width='45%'><font size='3' align='left'>" + result.getStepDescription() + "</font></th>");
				if (result.getStatus())
				{
					writer.write("<th width='25%'><font size='4' color='0000ff' align='center'>"+ "PASSED"+ "</font></th>");
					//writer.write("<th width='25%'><font size='4' color='0000ff'><img src='http://img.teapic.com/thumbs/201207/27/110424tgsuxofpjdlbormt.jpg.middle.jpg' alt='PASSED' style='width:20px;height:20px;'/></font></th>");
				}
				else 
				{
					writer.write("<th width='20%'><font size='4' color='0000ff'><img src='http://previews.123rf.com/images/cobalt/cobalt1304/cobalt130400022/19104926-Negate-wrong-icon-red-button--Stock-Vector-delete.jpg' alt='FAILED' style='width:20px;height:20px;'/></font></th>");
				}
				writer.write("</tr>");
				writer.write("</table>");
			}

			writer.write("</center>");
			writer.write("</body>");
			writer.write("</html>");
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
