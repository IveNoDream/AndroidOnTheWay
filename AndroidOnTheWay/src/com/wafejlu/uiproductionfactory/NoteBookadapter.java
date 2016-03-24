package com.wafejlu.uiproductionfactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/** 实现Filterable接口,编写过滤规则 */
public class NoteBookadapter extends BaseAdapter {
	private Context ctx;
	private ViewHolder holder;
	List<NoteBookItem> list;
	Map<String, Integer> selector;//键值是索引表的字母，值为对应在listview中的位置
	/** 字母表 */
	String index[];
	public NoteBookadapter(Context context, List<NoteBookItem> list,
			String[] index) {
		this.ctx = context;
		this.list = list;	
		this.index = index;
		selector = new HashMap<String, Integer>();
		for (int j = 0; j < index.length; j++) {// 循环字母表，找出list中对应字母的位置
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).index.equals(index[j].toLowerCase()))
					selector.put(index[j], i);
			}

		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try {
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(ctx).inflate(
						R.layout.note_item, null);
				holder.tv1 = (TextView) convertView.findViewById(R.id.tv1);
				holder.iv = (ImageView)convertView.findViewById(R.id.imageView_car_icon);
//				holder.tv2 = (TextView) convertView.findViewById(R.id.tv2);
//				holder.iv = (ImageView) convertView.findViewById(R.id.iv_phone);
//				holder.tv3 = (TextView) convertView.findViewById(R.id.tv3);
//				holder.layout = convertView.findViewById(R.id.layout);
				holder.index = (TextView) convertView
						.findViewById(R.id.tv_index);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// 绑定数据
			NoteBookItem item = list.get(position);
			holder.tv1.setText(item.name);
			holder.iv.setImageResource(item.imageRscID);
//			if (item.number.equals("null"))
//				holder.tv2.setText(item.mobile);
//			else
//				holder.tv2.setText(item.number);
//			holder.tv3.setText(item.call);

			// 显示index
			String currentStr = item.index;
			// 上一项的index
			String previewStr = (position - 1) >= 0 ? list.get(position - 1).index
					: " ";
			/**
			 * 判断是否上一次的存在
			 */
			if (!previewStr.equals(currentStr)) {
				holder.index.setVisibility(View.VISIBLE);
				holder.index.setText(currentStr);//中间提示的文本显示当前滑动的字母
			} else {
				holder.index.setVisibility(View.GONE);
			}
		} catch (OutOfMemoryError e) {
			Runtime.getRuntime().gc();
		} catch (Exception ex) {
			// handler.sendEmptyMessage(CommonMessage.PARSE_ERROR);
			ex.printStackTrace();
		}
		return convertView;
	}

	class ViewHolder {
		ImageView iv;
		TextView tv1;
		TextView index;
	}

	public Map<String, Integer> getSelector() {
		return selector;
	}

	public void setSelector(Map<String, Integer> selector) {
		this.selector = selector;
	}

	public String[] getIndex() {
		return index;
	}

	public void setIndex(String[] index) {
		this.index = index;
	}
}
