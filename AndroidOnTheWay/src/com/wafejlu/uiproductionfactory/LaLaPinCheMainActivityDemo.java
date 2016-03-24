package com.wafejlu.uiproductionfactory;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class LaLaPinCheMainActivityDemo extends Activity {

	private ListView listViewInfo = null;
	private NearbyAdapter nearbyAdapter = null;
	private LinearLayout linearLayoutchoose = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lalapinche_main_activity);
		
		InitRadioButton();
		InitListView();		
	}
	private void InitRadioButton()
	{
		linearLayoutchoose = (LinearLayout)findViewById(R.id.linearlayout_choose);
		linearLayoutchoose.setVisibility(View.INVISIBLE);
	}
	private void InitListView()
	{
		listViewInfo = (ListView)findViewById(R.id.listview_nearby);
		if (nearbyAdapter == null) {
			nearbyAdapter = new NearbyAdapter(this);
		}
		listViewInfo.setAdapter(nearbyAdapter);
		listViewInfo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				Intent intent = null;
				intent = new Intent(LaLaPinCheMainActivityDemo.this, LaLaPinCheMasterDetails.class);
				startActivity(intent);
			}
		});
	}
	private class NearbyAdapter extends BaseAdapter//创建类，实现视图与数据绑定
	{
		private LayoutInflater mInflater;
		public NearbyAdapter(Context context)
		{
			this.mInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			// TODO 自动生成的方法存根
			return getData().size();
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
			Log.i("Test", "getView");
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.listview_item, null);
				holder = new ViewHolder();
				holder.tvMaster = (TextView)convertView.findViewById(R.id.tv_master);
				holder.tvMasterInfo = (TextView)convertView.findViewById(R.id.tv_master_info);
				holder.imageviewMasterInfo = (ImageView)convertView.findViewById(R.id.imageview_master_info);
				holder.tvLeave = (TextView)convertView.findViewById(R.id.tv_leave);
				holder.tvArrive = (TextView)convertView.findViewById(R.id.tv_arrive);
				holder.tvTime = (TextView)convertView.findViewById(R.id.tv_time);
				holder.buttonDetails = (Button)convertView.findViewById(R.id.button_details);
				holder.buttonCollect = (Button)convertView.findViewById(R.id.button_collect);				
//				holder.imageView = (ImageView)convertView.findViewById(R.id.content_icon);
//				holder.textView = (TextView)convertView.findViewById(R.id.content);
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder)convertView.getTag();
			}
			//设置TextView显示内容
			holder.tvMaster.setText("林先生");
			holder.tvMasterInfo.setText("上下班");
			holder.imageviewMasterInfo.setImageResource(R.drawable.ic_launcher);
			holder.tvLeave.setText("出发:叶城路768号2km");
			holder.tvArrive.setText("到达:虹桥机场");
			holder.tvTime.setText("时间:06:40-18:00");
//			holder.imageView.setImageResource(R.drawable.ic_launcher);
//			holder.textView.setText(getData().get(position).get("ItemText").toString());
			return convertView;
		}
		
	}
	private ArrayList<HashMap<String, Object>> getData()
	{
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,Object>>();
		for(int i = 0; i < 10; i++)
		{
			HashMap<String, Object>map = new HashMap<String, Object>();
			map.put("ItemText", "车主信息");
			listItem.add(map);
		}
		return listItem;
	}
	public final class MasterOrPassengerInfo
	{
		public String isMasterOrPassenger;
		public String typeForNeed;//拼车类型（上下班、乘客、机场）
		public double distance;//全程
		public String leavePlace;//出发地
		public String arrivePlace;//到达地点
		public String timeSustained;//时间
		public double expend;//花费
		public String attach;//附言
		public String carBrand;//车类型（私家车、品牌）
	}
	public final class ViewHolder
	{
//		public ImageView imageView;
//		public TextView textView;
		public TextView tvMaster;
		public TextView tvMasterInfo;
		public ImageView imageviewMasterInfo;
		public TextView tvLeave;
		public TextView tvArrive;
		public TextView tvTime;
		public Button buttonDetails;
		public Button buttonCollect;
	}
	public void OnButtonFindCarClicked(View view)
	{
		Intent intent = null;
		intent = new Intent(LaLaPinCheMainActivityDemo.this, LaLaPinCheTogetherType.class);
		startActivity(intent);
	}
	public void OnButtonChooseClicked(View view)
	{
		linearLayoutchoose.setVisibility(View.VISIBLE);
	}
	public void OnButtonChooseOkClicked(View view)
	{
		linearLayoutchoose.setVisibility(View.INVISIBLE);
	}
	public void OnButtonFindPersonClicked(View view)
	{
		Intent intent = new Intent(LaLaPinCheMainActivityDemo.this, IndexActivity.class);
		startActivity(intent);
	}
}
