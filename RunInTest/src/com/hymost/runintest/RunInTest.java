package com.hymost.runintest;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.hymost.runintest.bt.BTTestActivity;
import com.hymost.runintest.camera.CameraActivity;
import com.hymost.runintest.gsm.GSMTestActivity;
import com.hymost.runintest.tone.ToneTestActivity;

public class RunInTest extends Activity {
	private static final String LOG_TAG = "Main";
	
	private GridView mGridView;
	ArrayList<String> mAdaptername = new ArrayList<String>();
	public static final int TESTITEM_WIFI = 0;
	public static final int TESTITEM_BT = 1;
	public static final int TESTITEM_CAMERA = 2;	
	public static final int TESTITEM_GPS = 3;
	public static final int TESTITEM_TONE = 4;
	
	public static final int TESTMODE_ALLTEST = 1;
	
	public static int mAllTestItem[];
	
	private static int mCurrentTestMode = 0;
	private static int mCurrentTestItem = 0;
	
	private Button mTestAllBtn= null;
	private Button mTestResultBtn = null;
	private Button mTestCountBtn = null;
	
	ReportDatabaseHelper mDbHelper;
	public DbUtils mDbUtils;
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		mTestAllBtn = (Button) findViewById(R.id.testBtn);
		mTestResultBtn = (Button) findViewById(R.id.resultBtn);
		mTestCountBtn = (Button) findViewById(R.id.countBtn);

		mDbHelper = new ReportDatabaseHelper(this, "RunInDb.db3", 1);
		mDbUtils = DbUtils.getInstance(this);

		mAdaptername.add(getString(R.string.wifi_item));
		mAdaptername.add(getString(R.string.bt_item));
		mAdaptername.add(getString(R.string.camera_item));
		mAdaptername.add(getString(R.string.gps_item));
		mAdaptername.add(getString(R.string.tone_item));

		int testItemCount = mAdaptername.size();
		mAllTestItem = new int[testItemCount];
		for (int i = 0; i < testItemCount; i++) {
			mAllTestItem[i] = i;
		}

		mGridView = (GridView) findViewById(R.id.main_grid);
		mGridView.setOnItemClickListener(new ItemClickListener());

		for (int i = 0; i < mAdaptername.size(); i++) {
			LogUtils.log(LOG_TAG, "mAdaptername(" + i + ") = " + mAdaptername.get(i));
		}

		GridAdapter adapter = new GridAdapter(this, mAdaptername);
		mGridView.setAdapter(adapter);

		mTestAllBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				startAllTest();
			}
		});

		mTestResultBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				testSelectedItemReport();
			}
		});

		mTestCountBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				TestTimeSet();
			}
		});

		//接收广播，开始自动运行
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		LogUtils.log(LOG_TAG, "bundle = " + bundle);
		if (bundle != null) {
			String mString = bundle.getString("something");
			LogUtils.log(LOG_TAG, "mString = " + mString);
			if ((mString != null) && mString.equals("1")) {
				startAllTest();
			}
		}
	}

	class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0,// The AdapterView where
													// the// click happened
				View arg1,// The view within the AdapterView that was clicked
				int arg2,// The position of the view in the adapter
				long arg3// The row id of the item that was clicked
		) {
			LogUtils.log(LOG_TAG, "onItemClick position = " + arg2);
			resetDB();
			testSelectedItem(arg2);
		}
	}
    
	public void writeTestLog(int number) {
		switch (number) {
		case 0:
			LogUtils.writeLog(getString(R.string.log_reboot_test_alone));
			break;
		case 1:
			LogUtils.writeLog(getString(R.string.log_updown_test_alone));
			break;
		case 2:
			LogUtils.writeLog(getString(R.string.log_camera_test_alone));
			break;
		case 3:
			LogUtils.writeLog(getString(R.string.log_emmc_test_alone));
			break;
		case 4:
			LogUtils.writeLog(getString(R.string.log_ddr_test_alone));
			break;
		}

	}
	
	public void resetDB(){
	    mDbUtils.mRunInDb.delete(DbUtils.DB_TABLE, null, null);
	}
	
	public void testSelectedItem(int selectedItem) {
		writeTestLog(selectedItem);
		Intent intent = new Intent();
		LogUtils.log(LOG_TAG, "testSelectedItem, selectedItem:" + selectedItem);

		switch (selectedItem) {
		case TESTITEM_WIFI:
			intent.setClass(this, GSMTestActivity.class);
			startActivity(intent);
			break;
			
		case TESTITEM_BT:
			intent.setClass(this, BTTestActivity.class);
			startActivity(intent);
			break;
			
		case TESTITEM_CAMERA:
			intent.setClass(this, CameraActivity.class);
			startActivityForResult(intent, TESTITEM_CAMERA);
			break;
			
		case TESTITEM_GPS:
			break;
			
		case TESTITEM_TONE:
			intent.setClass(this, ToneTestActivity.class);
			startActivity(intent);
			break;
			
		default:
			break;
		}
	}
	
	public void startAllTest() {
		LogUtils.writeLog(this.getString(R.string.log_begin_test));
		resetDB();
		setCurrtetTestMode(TESTMODE_ALLTEST);
		mCurrentTestItem = 0;
		LogUtils.log(LOG_TAG, "StartAllTest()");
		testSelectedItem(mAllTestItem[mCurrentTestItem]);
	}
	
	public void testSelectedItemReport() {
		Intent intent = new Intent();
		LogUtils.log(LOG_TAG, "testSelectedItemReport()");
		intent.setClass(this, Report.class);
		startActivity(intent); 
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.run_in_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public final class ViewHolder {
        public TextView textID;
    }
    
	private class GridAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private ArrayList<String> mItems;

		public GridAdapter(Context context, ArrayList<String> items) {
			mInflater = LayoutInflater.from(context);
			mItems = items;
		}

		@Override
		public int getCount() {
			return mItems.size();
		}

		@Override
		public Object getItem(int position) {
			return mItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder; 
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item, parent, false);
				holder = new ViewHolder();
				holder.textID = (TextView) convertView
						.findViewById(R.id.factor_button);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			holder.textID.setText(mItems.get(position));
			
			return convertView;
		}
	}
	
	private void TestTimeSet() {
		
	}
	
	public static void setCurrtetTestMode(int curmode){
		mCurrentTestMode = curmode;
	}

	public static int getCurrentTestMode(){
		return mCurrentTestMode;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (getCurrentTestMode() == TESTMODE_ALLTEST) {
			LogUtils.log(LOG_TAG, "mCurrentTestItem = " + mCurrentTestItem + ", requestCode = " + requestCode);
			LogUtils.log(LOG_TAG, "(mAllTestItem.length - 1) = " + (mAllTestItem.length - 1));

			if (mCurrentTestItem == (mAllTestItem.length - 1)) {
				Intent intent = new Intent();
				LogUtils.log(LOG_TAG, "start report");
				intent.setClass(this, Report.class);
				startActivity(intent);
				testSelectedItem(requestCode);
				setCurrtetTestMode(0);
			} else {
				LogUtils.log(LOG_TAG, "requestCode=" + requestCode);
				LogUtils.log(LOG_TAG, "mCurrentTestItem = " + mCurrentTestItem);
				
				if (requestCode == 1) {
					mCurrentTestItem = mCurrentTestItem + 1;
				}
				
				mCurrentTestItem = mCurrentTestItem + 1;
				testSelectedItem(mAllTestItem[mCurrentTestItem]);
			}
		} else {
			
		}

	}
	
	public void singleTest(){
	    Intent intent_report = new Intent();
	    LogUtils.log(LOG_TAG,"start report");
	    intent_report.setClass(this, Report.class);
	    startActivity(intent_report);	
	}
	
}
