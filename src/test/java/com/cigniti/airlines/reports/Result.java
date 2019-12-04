package com.cigniti.airlines.reports;

public class Result {

	private int stepCount;
	private String stepDescription;
	private boolean status = false;
	private String operation;
	private String screenshotpath;
	

	public Result(String operation,int stepCount, String stepDescription, boolean status, String screenshotpath) {
		this.stepCount = stepCount;
		this.stepDescription = stepDescription;
		this.status = status;
		this.operation=operation;
		this.screenshotpath = screenshotpath;
		
	}

	public int getStepCount() {
		return stepCount;
	}

	public String getStepDescription() {
		return stepDescription;
	}

	public String getOperation() {
		return operation;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	public String getScreenShotPath() {
		return screenshotpath;
	}
	

}
