package com.wafejlu.uiproductionfactory;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class ActivityPopupWindowTest extends Activity {
	private Button mButton;
	private PopupWindow mPopupWindow;
	
	private static String[] popMenu = {"设置", "蓝牙设置", "退出"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_popupwindow_test);
		
		
		View popupView = getLayoutInflater().inflate(R.layout.popupwindow, null);		
		mPopupWindow= new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources()));
		
		
		mButton = (Button)findViewById(R.id.btn_popUpWindow_test);
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				mPopupWindow.showAsDropDown(v);
			}
		});
	}
	public void OnTextViewClicked(View view)
	{
		switch (view.getId()) {
		case R.id.tv_bluetooth_setting:
			Toast.makeText(this, "you click tv_bluetooth_setting", Toast.LENGTH_SHORT).show();
			break;

		case R.id.tv_theme_setting:
			Toast.makeText(this, "you click tv_theme_setting", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		mPopupWindow.dismiss();
	}
	private List<String> getData()
	{
		List<String> data = new ArrayList<String>();
		for (int i = 0; i < popMenu.length; i++) {
			data.add(popMenu[i]);
		}
		return data;
	}
	private class AdapterPopUpWindow extends BaseAdapter{

		private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
		public AdapterPopUpWindow(Context context) {
			// TODO 自动生成的构造函数存根
			this.mInflater = LayoutInflater.from(context);
		}
		public int getCount() {
			// TODO 自动生成的方法存根
			return popMenu.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO 自动生成的方法存根
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO 自动生成的方法存根
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO 自动生成的方法存根
			return null;
		}
		
	}
}
