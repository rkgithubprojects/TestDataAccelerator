package com.cigniti.airlines.reports;

public class Result {

	private int stepCount;
	private String stepDescription;
	private boolean status = false;
	private String operation;
	

	public Result(String operation,int stepCount, String stepDescription, boolean status) {
		this.stepCount = stepCount;
		this.stepDescription = stepDescription;
		this.status = status;
		this.operation=operation;
		
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
	

}
