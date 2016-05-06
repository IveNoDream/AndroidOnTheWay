package com.hymost.runintest.bt;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.hymost.runintest.LogUtils;
import com.hymost.runintest.R;
import com.hymost.runintest.tone.ToneTestActivity;

import android.R.anim;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.BluetoothServerSocket;  
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import java.io.FileOutputStream;
import java.io.EOFException;  
import java.io.IOException;  

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class BTTestActivity extends Activity {
	
	private BluetoothAdapter adapter = null;
	private static String TAG = "BTTestActivity";
	public static BluetoothSocket btSocket;
	private Timer mTimer = null; 
	private boolean mHasBoundDevices = false;
	private TextView btTestReport;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bt_activity_report);
		
		adapter = BluetoothAdapter.getDefaultAdapter();
		
		btTestReport = (TextView) findViewById(R.id.bt_test_result_text);

		LogUtils.writeLog("\r\n");
		LogUtils.writeLog(this.getString(R.string.log_bt_test_result_start));
		
		IntentFilter startSearchFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);  
		openBT();
		setBTDiscoverable(30);
	    
		mTimer = new Timer();
		mTimer.schedule(timerTask, 0, 300000);
	}
	
	public void openBT(){
		if(null==adapter){ 
	          android.util.Log.e(TAG, "Device does not support Bluetooth");
	          return;  
	      }  
	      if (!adapter.isEnabled()) {  
	          adapter.enable();  
		LogUtils.writeLog("\r\n");
		LogUtils.writeLog(this.getString(R.string.log_bt_test_result_enablebt));
	      }
	}
	/**
	 * 
	 * @param timeout  参数没有作用
	 */
	public void setBTDiscoverable(int timeout){
		try {
			Method setDiscoverableTimeout = BluetoothAdapter.class.getMethod("setDiscoverableTimeout", int.class);
			setDiscoverableTimeout.setAccessible(true);
			Method setScanMode =BluetoothAdapter.class.getMethod("setScanMode", int.class,int.class);
			setScanMode.setAccessible(true);
			
			setDiscoverableTimeout.invoke(adapter, timeout);
			setScanMode.invoke(adapter, BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE,timeout);
			LogUtils.writeLog("\r\n");
			LogUtils.writeLog(this.getString(R.string.log_bt_test_result_enablebtdiscoverable));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	TimerTask timerTask = new TimerTask() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message msg = new Message();
			msg.what = 1;
			handler.sendMessage(msg);
		}
	};
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Set<BluetoothDevice> devices = adapter.getBondedDevices();
        			if(devices.size()>0){
        				for(Iterator<BluetoothDevice> it = devices.iterator();it.hasNext();){
        					final BluetoothDevice device = (BluetoothDevice)it.next();
        					//send
        					send(device);	
						android.util.Log.i(TAG, "send File");
						btTestReport.append("\n" + DateFormat.format("yyyy-hh-mm-ss", System.currentTimeMillis()) + "  send File");
						LogUtils.writeLog("\r\n");
						LogUtils.writeLog(DateFormat.format("yyyy-hh-mm-ss", System.currentTimeMillis()) + BTTestActivity.this.getString(R.string.log_bt_test_result_send_success));
					}
        			}else{
					android.util.Log.i(TAG, "No Bound Devices");
					btTestReport.append("\n" + DateFormat.format("yyyy-hh-mm-ss", System.currentTimeMillis()) + "  No Bound Devices");
					LogUtils.writeLog("\r\n");
					LogUtils.writeLog(DateFormat.format("yyyy-hh-mm-ss", System.currentTimeMillis()) + BTTestActivity.this.getString(R.string.log_bt_test_result_no_device));
        			}
				break;

			default:
				break;
			}
		};
	};

	protected void onDestroy() {
		super.onDestroy();
		android.util.Log.i(TAG, "onDestroy()");
		mTimer.cancel();
		LogUtils.writeLog("\r\n");
		LogUtils.writeLog(this.getString(R.string.log_bt_test_result_end));
	};
 
    	private String initPath() {
		File parentPath = Environment.getExternalStorageDirectory();
		String storagePath = parentPath.getAbsolutePath() + "/" + "RunInBT/";
		File file = new File(storagePath);
		if (!file.exists()) {
			file.mkdir();
		}
		String fileName = "RunInBT.txt";	
            	File filetxt = new File(storagePath + fileName);
			if (!filetxt.exists()) {
				try {
					filetxt.createNewFile();
			FileOutputStream stream = new FileOutputStream(filetxt, true);
			String s = "RunInTest: BlueTooth Test File";
			byte[] buf = s.getBytes();
			stream.write(buf);
			stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		return storagePath + fileName;
	}  	

	public void send(BluetoothDevice device){
		Intent intent = new Intent();
	    intent.setAction(Intent.ACTION_SEND);
	    intent.setComponent(new ComponentName(
	        "com.android.bluetooth",
	        "com.android.bluetooth.opp.BluetoothOppLauncherActivity"));
	    intent.setType("image/jpeg");
	    File file = new File(initPath());
	    if (file.exists()) {
			android.util.Log.i(TAG, "File exists");
		} else {
			android.util.Log.i(TAG, "File not exists");
		}
	    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		intent.putExtra ("RunInTestBT",true);
		intent.putExtra ("BTTestDevice",device.getAddress());
	    startActivity(intent);
	}
}
