package com.hymost.runintest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ReportDatabaseHelper extends SQLiteOpenHelper {
	private static final String TAG = "ReportDatabaseHelper";
	final String CREATE_TABLE_SQL = 
			"create table dict(_id integer primary key autoincrement, word, successnumber, allnumber)";

	public ReportDatabaseHelper(Context context, String name, int version) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, TAG + "db Create");
		db.execSQL("DROP TABLE IF EXISTS " + "dict" + ";");
		db.execSQL(CREATE_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG, TAG + "db Upgrade");
	}
}
