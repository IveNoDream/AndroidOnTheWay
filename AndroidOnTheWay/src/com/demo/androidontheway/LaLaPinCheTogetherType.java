package com.demo.androidontheway;

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
		// TODO �Զ���ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lalapinche_together_type);
	}
	public void OnButtonBackClicked(View view)
	{
		this.finish();
	}
	public void OnButtonGoBackWork(View view)
	{
		final String[] strWorkType = new String[]{"ƴ�ϰ�", "ƴ�°�", "���°���"};
		AlertDialog.Builder builder = new AlertDialog.Builder(LaLaPinCheTogetherType.this);
		builder.setTitle("��ѡ��");
		builder.setItems(strWorkType, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO �Զ���ɵķ������
				Toast.makeText(LaLaPinCheTogetherType.this, "��ѡ����" + strWorkType[which], Toast.LENGTH_SHORT).show();
			}
		});
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO �Զ���ɵķ������
				
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	public void OnButtonGoBackAirportClicked(View view)
	{
		final String[] strWorkType = new String[]{"ƴȥ��", "�뿪��"};
		AlertDialog.Builder builder = new AlertDialog.Builder(LaLaPinCheTogetherType.this);
		builder.setTitle("��ѡ��");
		builder.setItems(strWorkType, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO �Զ���ɵķ������
				Toast.makeText(LaLaPinCheTogetherType.this, "��ѡ����" + strWorkType[which], Toast.LENGTH_SHORT).show();
			}
		});
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO �Զ���ɵķ������
				
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
