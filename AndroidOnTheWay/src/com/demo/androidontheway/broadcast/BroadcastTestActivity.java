package com.demo.androidontheway.broadcast;

import com.demo.androidontheway.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class BroadcastTestActivity extends Activity implements OnClickListener {

	private IntentFilter intentFilter;
	private IntentFilter intentFilterDyn;
	private MyReceiver myReceiver = null;
	private NetworkChangeReceiver networkChangeReceiver = null;

	private Button sendBroadcastbButton;
	private Button dynamicBoradcastButton;
	private Button receveDynamicButton;

	private String SEND_BORADCAST_ACTION = "com.demo.androidontheway.SEND_BROADCAST_ACTION";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.broadcast_activity_main);
		sendBroadcastbButton = (Button) findViewById(R.id.btn_dynamic_register_broadcast);
		dynamicBoradcastButton = (Button) findViewById(R.id.btn_send_broadcast);
		receveDynamicButton = (Button) findViewById(R.id.btn_receiver_dynamic_register_broadcast);
		
		sendBroadcastbButton.setOnClickListener(this);
		dynamicBoradcastButton.setOnClickListener(this);
		receveDynamicButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_dynamic_register_broadcast:
			intentFilter = new IntentFilter();
			intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
			networkChangeReceiver = new NetworkChangeReceiver();
			registerReceiver(networkChangeReceiver, intentFilter);
			break;
		case R.id.btn_receiver_dynamic_register_broadcast:
			intentFilterDyn = new IntentFilter();
			intentFilterDyn.addAction(SEND_BORADCAST_ACTION);
			myReceiver = new MyReceiver();
			registerReceiver(myReceiver, intentFilterDyn);
			break;
		case R.id.btn_send_broadcast:
			Intent intent = new Intent(SEND_BORADCAST_ACTION);
			sendBroadcast(intent);
			break;
		default:
			break;
		}
	}

	public class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Toast.makeText(context, "receive dynamic boradcast recevier", Toast.LENGTH_SHORT).show();
		}
	}
	
	public class BootCompleteReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(context, "Boot Complete", Toast.LENGTH_LONG)
				.show();//static register broadcast,start up when boot complete
		}
	}

	class NetworkChangeReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(context, "network changes", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (null != networkChangeReceiver) {
			unregisterReceiver(networkChangeReceiver);
		}
		if (null != myReceiver) {
			unregisterReceiver(myReceiver);
		}
	}

}
