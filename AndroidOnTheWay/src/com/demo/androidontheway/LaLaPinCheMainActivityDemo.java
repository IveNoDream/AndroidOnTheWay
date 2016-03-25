package com.demo.androidontheway;

import java.util.ArrayList;
import java.util.HashMap;

import com.wafejlu.uiproductionfactory.R;

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
		// TODO �Զ���ɵķ������
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
				// TODO �Զ���ɵķ������
				Intent intent = null;
				intent = new Intent(LaLaPinCheMainActivityDemo.this, LaLaPinCheMasterDetails.class);
				startActivity(intent);
			}
		});
	}
	private class NearbyAdapter extends BaseAdapter//�����࣬ʵ����ͼ����ݰ�
	{
		private LayoutInflater mInflater;
		public NearbyAdapter(Context context)
		{
			this.mInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			// TODO �Զ���ɵķ������
			return getData().size();
		}

		@Override
		public Object getItem(int position) {
			// TODO �Զ���ɵķ������
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO �Զ���ɵķ������
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO �Զ���ɵķ������
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
			//����TextView��ʾ����
			holder.tvMaster.setText("������");
			holder.tvMasterInfo.setText("���°�");
			holder.imageviewMasterInfo.setImageResource(R.drawable.ic_launcher);
			holder.tvLeave.setText("����:Ҷ��·768��2km");
			holder.tvArrive.setText("����:���Ż�");
			holder.tvTime.setText("ʱ��:06:40-18:00");
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
			map.put("ItemText", "������Ϣ");
			listItem.add(map);
		}
		return listItem;
	}
	public final class MasterOrPassengerInfo
	{
		public String isMasterOrPassenger;
		public String typeForNeed;//ƴ�����ͣ����°ࡢ�˿͡���
		public double distance;//ȫ��
		public String leavePlace;//������
		public String arrivePlace;//����ص�
		public String timeSustained;//ʱ��
		public double expend;//����
		public String attach;//����
		public String carBrand;//�����ͣ�˽�ҳ���Ʒ�ƣ�
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
