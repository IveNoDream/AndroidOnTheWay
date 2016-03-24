package com.wafejlu.uiproductionfactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LaLaPinCheTogetherType extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lalapinche_together_type);
	}
	public void OnButtonBackClicked(View view)
	{
		this.finish();
	}
	public void OnButtonGoBackWork(View view)
	{
		final String[] strWorkType = new String[]{"拼上班", "拼下班", "上下班往返"};
		AlertDialog.Builder builder = new AlertDialog.Builder(LaLaPinCheTogetherType.this);
		builder.setTitle("请选择");
		builder.setItems(strWorkType, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 自动生成的方法存根
				Toast.makeText(LaLaPinCheTogetherType.this, "你选择了" + strWorkType[which], Toast.LENGTH_SHORT).show();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 自动生成的方法存根
				
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	public void OnButtonGoBackAirportClicked(View view)
	{
		final String[] strWorkType = new String[]{"拼去机场", "离开机场"};
		AlertDialog.Builder builder = new AlertDialog.Builder(LaLaPinCheTogetherType.this);
		builder.setTitle("请选择");
		builder.setItems(strWorkType, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 自动生成的方法存根
				Toast.makeText(LaLaPinCheTogetherType.this, "你选择了" + strWorkType[which], Toast.LENGTH_SHORT).show();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 自动生成的方法存根
				
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
