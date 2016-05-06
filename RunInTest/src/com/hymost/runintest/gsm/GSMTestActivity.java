package com.hymost.runintest.gsm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

import com.hymost.runintest.LogUtils;
import com.hymost.runintest.R;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import java.lang.reflect.Field;
import android.widget.TextView;

public class GSMTestActivity extends Activity {

	private TextView mTextView = null;
	private Timer mTimer = null;
	private boolean status = false;

	private ConnectivityManager mConnectivityManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gsm_activity_report);

		LogUtils.writeLog("\r\n");
		LogUtils.writeLog(this.getString(R.string.log_gsm_test_result_start));

		mTextView = (TextView) findViewById(R.id.gsm_test_result_text);

		// 打开数据开关
		mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		mTimer = new Timer();
		mTimer.schedule(timerTask, 0, 5000);
		new Thread(new Runnable() {

			@Override
			public void run() { // TODO Auto-generated method stub
				while (true) {
					if (!getMobileDataStatus()) {
						setMobileDataStatus(true);
					}
					ping();
				}
			}
		}).start();

	}

	public void ping() {
		try {
			java.net.URL url = new java.net.URL("http://www.microsoft.com");
			java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url
					.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.connect();
			if (conn.getResponseCode() != 200) {
				status = false;
			} else {
				status = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			status = false;
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

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				LogUtils.writeLog(DateFormat.format("yyyy-hh-mm-ss", System.currentTimeMillis()) + GSMTestActivity.this.getString(status ? R.string.log_gsm_test_result_success : R.string.log_gsm_test_result_failed));
				mTextView.append("\n" + DateFormat.format("yyyy-hh-mm-ss", System.currentTimeMillis()) + (status ? "请求成功" : "请求失败"));
				break;

			default:
				break;
			}
		};
	};

	protected void onDestroy() {
		super.onDestroy();
		mTimer.cancel();

		LogUtils.writeLog("\r\n");
		LogUtils.writeLog(this.getString(R.string.log_gsm_test_result_end));
	};

	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	// 获取移动数据开关状态
	private boolean getMobileDataStatus() {
		String methodName = "getMobileDataEnabled";
		Class cmClass = mConnectivityManager.getClass();
		Boolean isOpen = null;

		try {
			Method method = cmClass.getMethod(methodName, null);

			isOpen = (Boolean) method.invoke(mConnectivityManager, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isOpen;
	}

	// 通过反射实现开启或关闭移动数据
	private void setMobileDataStatus(boolean enabled) {
		try {
			Class<?> conMgrClass = Class.forName(mConnectivityManager
					.getClass().getName());
			// 得到ConnectivityManager类的成员变量mService（ConnectivityService类型）
			Field iConMgrField = conMgrClass.getDeclaredField("mService");
			iConMgrField.setAccessible(true);
			// mService成员初始化
			Object iConMgr = iConMgrField.get(mConnectivityManager);
			// 得到mService对应的Class对象
			Class<?> iConMgrClass = Class.forName(iConMgr.getClass().getName());
			/*
			 * 得到mService的setMobileDataEnabled(该方法在android源码的ConnectivityService类中实现
			 * )， 该方法的参数为布尔型，所以第二个参数为Boolean.TYPE
			 */
			Method setMobileDataEnabledMethod = iConMgrClass.getDeclaredMethod(
					"setMobileDataEnabled", Boolean.TYPE);
			setMobileDataEnabledMethod.setAccessible(true);
			/*
			 * 调用ConnectivityManager的setMobileDataEnabled方法（方法是隐藏的），
			 * 实际上该方法的实现是在ConnectivityService(系统服务实现类)中的
			 */
			setMobileDataEnabledMethod.invoke(iConMgr, enabled);
			LogUtils.writeLog(DateFormat.format("yyyy-hh-mm-ss", System.currentTimeMillis()) + this.getString(R.string.log_gsm_test_result_enable_success));
		} catch (ClassNotFoundException e) {
			LogUtils.writeLog(DateFormat.format("yyyy-hh-mm-ss", System.currentTimeMillis()) + this.getString(R.string.log_gsm_test_result_enable_failed) + e.toString());
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			LogUtils.writeLog(DateFormat.format("yyyy-hh-mm-ss", System.currentTimeMillis()) + this.getString(R.string.log_gsm_test_result_enable_failed) + e.toString());
			e.printStackTrace();
		} catch (SecurityException e) {
			LogUtils.writeLog(DateFormat.format("yyyy-hh-mm-ss", System.currentTimeMillis()) + this.getString(R.string.log_gsm_test_result_enable_failed) + e.toString());
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			LogUtils.writeLog(DateFormat.format("yyyy-hh-mm-ss", System.currentTimeMillis()) + this.getString(R.string.log_gsm_test_result_enable_failed) + e.toString());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			LogUtils.writeLog(DateFormat.format("yyyy-hh-mm-ss", System.currentTimeMillis()) + this.getString(R.string.log_gsm_test_result_enable_failed) + e.toString());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			LogUtils.writeLog(DateFormat.format("yyyy-hh-mm-ss", System.currentTimeMillis()) + this.getString(R.string.log_gsm_test_result_enable_failed) + e.toString());
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			LogUtils.writeLog(DateFormat.format("yyyy-hh-mm-ss", System.currentTimeMillis()) + this.getString(R.string.log_gsm_test_result_enable_failed) + e.toString());
			e.printStackTrace();
		}
	}
}
