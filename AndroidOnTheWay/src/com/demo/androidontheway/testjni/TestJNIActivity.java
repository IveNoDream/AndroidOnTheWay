package com.demo.androidontheway.testjni;

import com.demo.androidontheway.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TestJNIActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_jni_activity);
		TextView tView = (TextView) findViewById(R.id.tv_test_jni);
		
		TestJNI jni = new TestJNI();
		boolean flag = jni.init();
		if (flag) {
			int sum = jni.add(1, 2);
			tView.setText("1+2=" + sum);
		}
	}
	
	static {
		System.loadLibrary("TestJNI");
	}
}
