package com.hymost.runintest;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hymost.runintest.GridViewItems;
import com.hymost.runintest.ReportDatabaseHelper;

public class DbUtils {

	private static final String TAG = "DbUtils";
	private Context mContext;
	public SQLiteDatabase mRunInDb = null;
	public static final String ENG_ENGTEST_DB = "/protect_f/engtest.db";
	public static final String ENG_STRING2INT_TABLE = "str2int";
	public static final String ENG_STRING2INT_NAME = "name";
	public static final String ENG_STRING2INT_VALUE = "value";

	public static final String DB_TABLE = "dict";
	public static final String DB_COLUMN_ITEM = "item";
	public static final String DB_COLUMN_SUCCESS_NUMBER = "successNumber";
	public static final String DB_COLUMN_ALL_NUMBER = "allNumber";

	public static final int ENG_ENGTEST_VERSION = 1;
	public SharedPreferences mPreferences;

	private static DbUtils mDbUtils;

	public static synchronized DbUtils getInstance(Context context) {
		if (mDbUtils == null) {
			mDbUtils = new DbUtils(context);
		}
		return mDbUtils;
	}

	private DbUtils(Context context) {
		mContext = context;
		ReportDatabaseHelper dbHelper = new ReportDatabaseHelper(
				mContext, "rundb.db3", 1);
		mRunInDb = dbHelper.getWritableDatabase();
	}

	public ArrayList<GridViewItems> queryData() {
		int i = 0;
		Cursor cursor = mRunInDb.query(DB_TABLE, new String[] { DB_COLUMN_ITEM,
				DB_COLUMN_SUCCESS_NUMBER, DB_COLUMN_ALL_NUMBER }, null, null,
				null, null, null);// 7

		if (cursor == null) {
			Log.e(TAG, "null data for db");
		}
		
		ArrayList<GridViewItems> resultList = new ArrayList<GridViewItems>();
		try {
			while (cursor.moveToNext()) {
				GridViewItems item = new GridViewItems();
				item.mTestId = String.valueOf(++i);
				item.mTestCase = cursor.getString(0);
				item.mTestItemResult = cursor.getInt(1);
				item.mTestAllNumber = cursor.getInt(2);
				Log.i("test_db_query", "mTestId = " + item.mTestId
						+ "mTestCase = " + item.mTestCase
						+ "mTestItemResult = " + item.mTestItemResult
						+ "mTestAllNumber = " + item.mTestAllNumber);

				if (item.mTestCase != null) {
					resultList.add(item);
				}
			}
		} finally {
			cursor.close();
		}

		return resultList;
	}

	public int getTestNumber() {
		int testNumber = -1;
		Cursor cursor = mRunInDb.query(DB_TABLE, new String[] {
				DB_COLUMN_ITEM, DB_COLUMN_SUCCESS_NUMBER, DB_COLUMN_ALL_NUMBER },
				null, null, null, null, null);// 7
		
		if (cursor != null) {
			testNumber = cursor.getCount();
		} 
		
		cursor.close();
		
		return testNumber;
	}

	public int getTestListItemStatus(String name) {
		return 0;
	}

	public void insertData(String name, int successValue, int allNumber) {
		ContentValues cv = new ContentValues();
		int malltime = getNumber(allNumber);

		cv.put(DB_COLUMN_ITEM, name);
		cv.put(DB_COLUMN_SUCCESS_NUMBER, successValue);
		cv.put(DB_COLUMN_ALL_NUMBER, malltime);
		long returnValue = mRunInDb.insert(DB_TABLE, null, cv);

		if (returnValue == -1) {
			// Log.e(TAG, "insert DB error!");
		}
	}

	public int getNumber(int item) {
		int number = 0;
		mPreferences = mContext.getSharedPreferences("testnumber", 0x0001);
		switch (item) {
		case 1:
			number = mPreferences.getInt("RebootNumber", 1);
			break;
		case 2:
			number = mPreferences.getInt("UpdownNumber", 1);
			break;
		case 3:
			number = mPreferences.getInt("CameraNumber", 1);
			break;
		case 4:
			number = mPreferences.getInt("EmmcNumber", 1);
			break;
		case 5:
			number = mPreferences.getInt("DdrNumber", 1);
			break;
		case 6:
			number = mPreferences.getInt("CallsNumber", 1);
			break;
		}
		Log.i(TAG, "number = " + number);
		return number;
	}

	public void updateData(String name, int successvalue, int alltime) {
		int malltime = getNumber(alltime);
		ContentValues cv = new ContentValues();
		cv.put(DB_COLUMN_ITEM, name);
		cv.put(DB_COLUMN_SUCCESS_NUMBER, successvalue);
		cv.put(DB_COLUMN_ALL_NUMBER, malltime);

		mRunInDb.update(DB_TABLE, cv, DB_COLUMN_ITEM + "= \'" + name
				+ "\'", null);
	}

	public void updateDB(String name, int value, int allvalue) {
		if (queryNameExist(name)) {
			Log.i("test_dbutils", "exist");
			updateData(name, value, allvalue);
		} else {
			Log.i("test_dbutils", "not exist");
			insertData(name, value, allvalue);
		}
	}

	public boolean queryNameExist(String name) {
		try {
			Cursor c = mRunInDb.query(DB_TABLE, new String[] {
					DB_COLUMN_ITEM, DB_COLUMN_SUCCESS_NUMBER,
					DB_COLUMN_ALL_NUMBER }, DB_COLUMN_ITEM + "= \'" + name
					+ "\'", null, null, null, null);
			if (c != null) {
				if (c.getCount() > 0) {
					c.close();
					return true;
				}
				c.close();
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public int queryFailCount() {
		int bln = 0;
		if (mRunInDb == null)
			return bln;
		Cursor cur = mRunInDb.query(ENG_STRING2INT_TABLE, new String[] {
				"name", "value" }, "value=?", new String[] { "0" }, null, null,
				null);
		if (cur != null && cur.getCount() >= 1) {
			bln = cur.getCount();
		}
		cur.close();
		return bln;
	}

}
