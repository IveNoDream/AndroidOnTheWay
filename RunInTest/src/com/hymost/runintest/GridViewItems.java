package com.hymost.runintest;

public class GridViewItems {
	public int mIconID;
	public String mTitle;
	public int mSuccessItem;
	public int mAllItem;

	public String mTestId;
	public String mTestCase;
	public int mTestItemResult;
	public int mTestAllNumber;

	public GridViewItems() {

	}

	public GridViewItems(int id, String title, int item_success, int item_all) {
		this.mIconID = id;
		this.mTitle = title;
		this.mSuccessItem = item_success;
		this.mAllItem = item_all;
	}

	public GridViewItems(String testID, String testCase, int testItem,
			int testAllTime) {
		this.mTestId = testID;
		this.mTestCase = testCase;
		this.mTestItemResult = testItem;
		this.mTestAllNumber = testAllTime;
	}

	public void setTestResult(int testItem) {
		this.mTestItemResult = testItem;
	}

}
