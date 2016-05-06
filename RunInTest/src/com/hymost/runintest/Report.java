package com.hymost.runintest;

import java.util.ArrayList;

import com.hymost.runintest.DbUtils;
import com.hymost.runintest.GridViewItems;
import com.hymost.runintest.LogUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Report extends Activity {
	private static final String TAG = "Report";
	private Context mContext;
	public TextView mTestResultView;
	ArrayList<GridViewItems> resultListItem;
	private DbUtils mDbutils;
	ArrayList<GridViewItems> mArraylist;
	SensorManager mSensorManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report);
		
		mContext = this;
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		resultListItem = new ArrayList<GridViewItems>();
		mDbutils = DbUtils.getInstance(mContext);
		mTestResultView = (TextView) findViewById(R.id.test_result_text);

		ListView listView = (ListView) findViewById(R.id.listview_layout);
		ListAdapter listAdapter = new ListAdapter(mContext,
				mDbutils.queryData());
		listView.setAdapter(listAdapter);
	}

	@Override
	protected void onStart() {
		super.onStop();
		Log.i(TAG, "onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, "onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i(TAG, "onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		finish();
		Log.i(TAG, "onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroy");
	}

	public final class ViewHolder {
		public TextView textID;
		public TextView textCase;
		public TextView textResult;
		public TextView textall;
	}

	private class ListAdapter extends BaseAdapter {
		private Context mContext;
		private LayoutInflater mLayoutInflater;
		private ViewHolder mViewHolder;
		private ArrayList<GridViewItems> mItems;

		public ListAdapter(Context context, ArrayList<GridViewItems> items) {
			mContext = context;
			mLayoutInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mViewHolder = new ViewHolder();
			// mItems = items;
			mItems = ArraySort(items);
			LogUtils.writeLog(mContext.getString(R.string.log_test_report));
		}

		public ArrayList<GridViewItems> ArraySort(ArrayList<GridViewItems> items) {
			int Count = items.size();
			int mm = 1;
			mArraylist = new ArrayList<GridViewItems>();

			for (int i = 0; i < Count; i++) {
				if ((items.get(i).mTestItemResult) == (items.get(i).mTestAllNumber)) {
					items.get(i).mIconID = mm;
					mArraylist.add(items.get(i));
					mm++;
				}
			}

			for (int i = 0; i < Count; i++) {
				if ((items.get(i).mTestItemResult) != (items.get(i).mTestAllNumber)) {
					items.get(i).mIconID = mm;
					mArraylist.add(items.get(i));
					mm++;
				}
			}

			return mArraylist;
		}

		@Override
		public int getCount() {
			return mItems.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = mLayoutInflater.inflate(R.layout.table_row_layout,
					parent, false);
			mViewHolder.textID = (TextView) convertView
					.findViewById(R.id.id_text);
			mViewHolder.textCase = (TextView) convertView
					.findViewById(R.id.test_case);
			mViewHolder.textResult = (TextView) convertView
					.findViewById(R.id.test_result);
			mViewHolder.textall = (TextView) convertView
					.findViewById(R.id.test_all);

			String successnumber = (String
					.valueOf(mItems.get(position).mTestItemResult));
			String allnumber = (String
					.valueOf(mItems.get(position).mTestAllNumber));
			
			if (successnumber.equals(allnumber)) {
				mViewHolder.textID.setText(String.valueOf(position + 1));
				mViewHolder.textCase.setText(mItems.get(position).mTestCase);
				mViewHolder.textResult.setText(successnumber);
				mViewHolder.textall.setText(allnumber);

				mViewHolder.textID.setTextColor(Color.GREEN);
				mViewHolder.textCase.setTextColor(Color.GREEN);
				mViewHolder.textResult.setTextColor(Color.GREEN);
				mViewHolder.textall.setTextColor(Color.GREEN);
				LogUtils.writeLog(mItems.get(position).mTestCase
						+ mContext.getString(R.string.log_test_success) + "---"
						+ successnumber + "/" + allnumber);
			} else if ((mItems.get(position).mTestCase.equals("LSensor Test") && (mSensorManager
					.getDefaultSensor(Sensor.TYPE_LIGHT) == null))
					|| (mItems.get(position).mTestCase.equals("PSensor Test") && (mSensorManager
							.getDefaultSensor(Sensor.TYPE_PROXIMITY) == null))) {
				mViewHolder.textID.setText(String.valueOf(position + 1));
				mViewHolder.textCase.setText(mItems.get(position).mTestCase);
				mViewHolder.textResult.setText(R.string.not_support);
				mViewHolder.textall.setText(allnumber);

				mViewHolder.textID.setTextColor(Color.GREEN);
				mViewHolder.textCase.setTextColor(Color.GREEN);
				mViewHolder.textResult.setTextColor(Color.GREEN);
				mViewHolder.textall.setTextColor(Color.GREEN);
			} else if (Integer.parseInt(allnumber) < 1) {
				convertView.setVisibility(View.GONE);
			} else {
				mViewHolder.textID.setText(String.valueOf(position + 1));
				mViewHolder.textCase.setText(mItems.get(position).mTestCase);
				if ((mItems.get(position).mTestItemResult) == -2) {
					mViewHolder.textResult
							.setText(R.string.please_insert_Headset);
					LogUtils.writeLog(mItems.get(position).mTestCase
							+ mContext
									.getString(R.string.log_test_failed_or_noHeadset)
							+ "---" + successnumber + "/" + allnumber);
				} else if (Integer.parseInt(successnumber) < 0) {
					// mViewHolder.textResult.setText(R.string.not_test);
				} else {
					mViewHolder.textResult.setText(successnumber);
				}
				mViewHolder.textall.setText(allnumber);

				mViewHolder.textID.setTextColor(Color.RED);
				mViewHolder.textCase.setTextColor(Color.RED);
				mViewHolder.textResult.setTextColor(Color.RED);
				mViewHolder.textall.setTextColor(Color.RED);
				mTestResultView.setText(getResources().getString(
						R.string.TestResultTitleStringFail));
				if (((mItems.get(position).mTestItemResult) != -2)) {
					LogUtils.writeLog(mItems.get(position).mTestCase
							+ mContext.getString(R.string.log_test_failed)
							+ "---" + successnumber + "/" + allnumber);
				}
			}

			Log.i(TAG, "id = " + (String.valueOf(position + 1)));
			Log.i(TAG, "mTestCase = " + (mItems.get(position).mTestCase));
			Log.i("test_report", "testItemResult = " + (mItems.get(position).mTestItemResult));
			Log.i("test_report", "testAllCount = " + (mItems.get(position).mTestAllNumber));
			
			if (getCount() == (position + 1)) {
				LogUtils.writeLog("------------- END ----------------");
			}

			return convertView;
		}
	}

}
