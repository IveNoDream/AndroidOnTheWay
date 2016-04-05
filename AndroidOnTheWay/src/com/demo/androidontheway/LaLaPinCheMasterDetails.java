package com.demo.androidontheway;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class LaLaPinCheMasterDetails extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ���ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lalapinche_master_details_activity);
	}
	public void OnButtonBackClicked(View view)
	{
		this.finish();
	}
}
