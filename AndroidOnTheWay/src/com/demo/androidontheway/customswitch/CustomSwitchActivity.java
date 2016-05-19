package com.demo.androidontheway.customswitch;

import com.demo.androidontheway.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CustomSwitchActivity extends Activity {

	private SwitchButton mToastSwitchButton = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_switch_activity);
		
		mToastSwitchButton = (SwitchButton) findViewById(R.id.sb_onclick_toast);
		mToastSwitchButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (arg1) {
					Toast.makeText(CustomSwitchActivity.this, "Check ON", Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(CustomSwitchActivity.this, "Check OFF", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
