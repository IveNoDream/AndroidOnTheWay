package com.wafejlu.uiproductionfactory;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class LaLaPinCheMasterDetails extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lalapinche_master_details_activity);
	}
	public void OnButtonBackClicked(View view)
	{
		this.finish();
	}
}
