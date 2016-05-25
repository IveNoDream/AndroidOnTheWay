package com.demo.androidontheway.testservice;

import com.demo.androidontheway.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class TestServiceActivity extends Activity {
	private MyService msgService;
	private ProgressBar mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_service_activity);

		Intent intent = new Intent(
				"com.demo.androidontheway.testservice.MSG_ACTION");
		bindService(intent, conn, Context.BIND_AUTO_CREATE);

		mProgressBar = (ProgressBar) findViewById(R.id.pro_service);
		Button mButton = (Button) findViewById(R.id.btn_start_service);
		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// start download at service
				msgService.startDownLoad();
			}
		});
	}

	ServiceConnection conn = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// return object of service
			msgService = ((MyService.MsgBinder) service).getService();
			// recieve callback progress
			msgService.SetOnProgressListner(new OnProgressListner() {

				@Override
				public void onProgress(int progress) {
					// TODO Auto-generated method stub
					mProgressBar.setProgress(progress);
				}
			});
		}
	};

	@Override
	protected void onDestroy() {
		unbindService(conn);
		super.onDestroy();
	}
}
