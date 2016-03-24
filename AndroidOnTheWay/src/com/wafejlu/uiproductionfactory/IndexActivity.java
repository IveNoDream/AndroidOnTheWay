package com.wafejlu.uiproductionfactory;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @package：com.wafejlu.uiproductionfactory
 * @author：fengw
 * @email：
 * @data：
 * @description：带实体的数据源
 */
public class IndexActivity extends Activity {

	LinearLayout layoutIndex;
	/** 字母索引表 */
	private String[] strCarName = {"奥迪", "宝马", "保时捷", "奔驰", "标致", "本田", "别克", "宾利", "BYD", 
			"长城", "大众", "法拉利", "菲亚特",  "丰田", "福特","广汽", "海马", "悍马", "吉利", "江淮", "江铃", "捷豹", "凯迪拉克",
			"克莱斯勒", "雷克萨斯", "雷诺", "力帆", "莲花", "林肯", "铃木", "路虎", "马自达", "名爵", "mini", "尼桑"
			, "欧宝", "讴歌", "奇瑞", "起亚", "荣威", "斯柯达", "smart", "沃尔沃", "现代", "雪铁龙", 
			"一汽", "中华", "其他品牌"};
	private String[] str = { "A", "B", "C", "D",  "F", "G", "H", 
			"J", "K", "L", "M", "N", "O",  "Q", "R", "S",  "W", "X", "Y",
			"Z" };//

	int imageIconID[] = {R.drawable.ic_audi,R.drawable.ic_bmw, R.drawable.ic_porsche, 
			R.drawable.ic_mercedesbenz, R.drawable.ic_peugeot, R.drawable.ic_honda, 
			R.drawable.ic_buick, R.drawable.ic_bentley, R.drawable.ic_byd, 
			R.drawable.ic_greatwallmotor, R.drawable.ic_volkswagen, R.drawable.ic_ferrari, 
			R.drawable.ic_fiat, R.drawable.ic_toyota, R.drawable.ic_ford, 
			R.drawable.ic_guangqi, R.drawable.ic_haima, R.drawable.ic_hummer, R.drawable.ic_geely, 
			R.drawable.ic_jac, R.drawable.ic_jmc, R.drawable.ic_jaguar, R.drawable.ic_cadillac, 
			R.drawable.ic_chrysler,R.drawable.ic_lexus, R.drawable.ic_reynolds, R.drawable.ic_lifan, 
			R.drawable.ic_lotus, R.drawable.ic_lincoln, R.drawable.ic_suzuki, R.drawable.ic_landrover,
			R.drawable.ic_mazda, R.drawable.ic_mg, R.drawable.ic_mini, R.drawable.ic_nissan, 
			R.drawable.ic_opel, R.drawable.ic_acura, R.drawable.ic_chery,
			R.drawable.ic_kia, R.drawable.ic_roewe, R.drawable.ic_skoda,
			R.drawable.ic_smart, R.drawable.ic_volvo, R.drawable.ic_hyundai, 
			R.drawable.ic_citroen, R.drawable.ic_faw, R.drawable.ic_zhonghua, R.drawable.ic_car};
	int height;// 字体高度
	List<NoteBookItem> listData;
	private ListView listView;
	NoteBookadapter adapter;
	private TextView tv_show;// 中间显示标题的文本
	private RadioButton radioButtondefault = null;//设置默认选择radiobutton
	private ImageView imageChoosed = null;
	private TextView textChoosed = null;
	private RadioGroup radioGroupCarTypeTaxi = null;//出租车选项
	private RelativeLayout relativeLayoutCarTypeIChoose = null;//私家车已选择
	private FrameLayout frameLayoutListViewIndex = null;//私家车类型ListView

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_index_activity_demo);
		layoutIndex = (LinearLayout) this.findViewById(R.id.layout);
		layoutIndex.setBackgroundColor(Color.parseColor("#00ffffff"));
		
		radioButtondefault = (RadioButton)findViewById(R.id.radioButton_default);
		radioButtondefault.setChecked(true);

		getData();
		listView = (ListView) findViewById(R.id.listView1);
		adapter = new NoteBookadapter(this, listData, this.str);
		listView.setAdapter(adapter);

		tv_show = (TextView) findViewById(R.id.tv);
		tv_show.setVisibility(View.INVISIBLE);
		//实例化选择车的类型
		imageChoosed = (ImageView)findViewById(R.id.imageView_car_type_personal_icon);
		textChoosed = (TextView)findViewById(R.id.tv_car_type_name_personal);
		//ListView点击事件
		listView.setOnItemClickListener(carTypeClickedListener);
		//实例化要隐藏哪一个界面
		radioGroupCarTypeTaxi = (RadioGroup)findViewById(R.id.radio_group_taix_type);
		relativeLayoutCarTypeIChoose = (RelativeLayout)findViewById(R.id.relativelayout_car_type_personal);
		frameLayoutListViewIndex = (FrameLayout)findViewById(R.id.framelayout_listview_index);
		radioGroupCarTypeTaxi.setVisibility(View.INVISIBLE);//初始化隐藏出租车界面
	}
	private OnItemClickListener carTypeClickedListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO 自动生成的方法存根
			imageChoosed.setImageResource(imageIconID[position]);
			textChoosed.setText(strCarName[position]);
		}
	};
	public void getData() {
		listData = new ArrayList<NoteBookItem>();

		for (int i = 0; i < strCarName.length; i++) {
			NoteBookItem item = new NoteBookItem();
			item.name = strCarName[i];
			item.imageRscID = imageIconID[i];
			item.index = String.valueOf(Pinyin4j.getHanyuPinyin(item.name).charAt(0));
			listData.add(item);
		}
/*		NoteBookItem n1 = new NoteBookItem();
		n1.call = "经理";
		n1.name = "allen";
		n1.mobile = "18217594856";
		n1.index = String.valueOf(Pinyin4j.getHanyuPinyin(n1.name).charAt(0));		
		listData.add(n1);
		
		NoteBookItem n2 = new NoteBookItem();
		n2.call = "工程师";
		n2.name = "android";
		n2.mobile = "13658974521";
		n2.index = String.valueOf(Pinyin4j.getHanyuPinyin(n2.name).charAt(0));
		listData.add(n2);
		
		NoteBookItem n3 = new NoteBookItem();
		n3.call = "经理";
		n3.name = "周俊";
		n3.mobile = "13658974521";
		n3.index = String.valueOf(Pinyin4j.getHanyuPinyin(n3.name).charAt(0));
		listData.add(n3);
		
		NoteBookItem n4 = new NoteBookItem();
		n4.call = "教师";
		n4.name = "王强";
		n4.number = "021-25635784";
		n4.index = String.valueOf(Pinyin4j.getHanyuPinyin(n4.name).charAt(0));
		listData.add(n4);
		
		NoteBookItem n5 = new NoteBookItem();
		n5.call = "客服";
		n5.name = "刘敏";
		n5.number = "010-25635784";
		n5.index = String.valueOf(Pinyin4j.getHanyuPinyin(n5.name).charAt(0));
		listData.add(n5);
		
		NoteBookItem n6 = new NoteBookItem();
		n6.call = "客服";
		n6.name = "bruth";
		n6.number = "010-25635784";
		n6.index = String.valueOf(Pinyin4j.getHanyuPinyin(n6.name).charAt(0));
		listData.add(n6);
		
		NoteBookItem n7 = new NoteBookItem();
		n7.call = "经理";
		n7.name = "陈文明";
		n7.number = "010-25635784";
		n7.index = String.valueOf(Pinyin4j.getHanyuPinyin(n7.name).charAt(0));
		listData.add(n7);
		
		NoteBookItem n8 = new NoteBookItem();
		n8.call = "客服";
		n8.name = "mary";
		n8.number = "010-25635784";
		n8.index = String.valueOf(Pinyin4j.getHanyuPinyin(n8.name).charAt(0));
		listData.add(n8);
		
		NoteBookItem n9 = new NoteBookItem();
		n9.call = "客服";
		n9.name = "李勇";
		n9.number = "010-25635784";
		n9.index = String.valueOf(Pinyin4j.getHanyuPinyin(n9.name).charAt(0));
		listData.add(n9);
		
		NoteBookItem n10 = new NoteBookItem();
		n10.call = "客服";
		n10.name = "娜娜";
		n10.number = "010-25635784";
		n10.index = String.valueOf(Pinyin4j.getHanyuPinyin(n10.name).charAt(0));
		listData.add(n10);
		
		NoteBookItem n11 = new NoteBookItem();
		n11.call = "客服";
		n11.name = "筱筱";
		n11.number = "010-25635784";
		n11.index = String.valueOf(Pinyin4j.getHanyuPinyin(n11.name).charAt(0));
		listData.add(n11);*/
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// 在oncreate里面执行下面的代码没反应，因为oncreate里面得到的getHeight=0
		System.out
				.println("layoutIndex.getHeight()=" + layoutIndex.getHeight());
		height = layoutIndex.getHeight() / str.length;
		getIndexView();
	}
	/*
	 * 私家车按钮点击响应
	 */
	public void OnRadioButtonPersonalCarClicked(View view)
	{
		radioGroupCarTypeTaxi.setVisibility(View.INVISIBLE);
		relativeLayoutCarTypeIChoose.setVisibility(View.VISIBLE);
		frameLayoutListViewIndex.setVisibility(View.VISIBLE);
	}
	/*
	 * 出租车按钮点击响应
	 */
	public void OnRadioButtonTaxiClicked(View view)
	{
		relativeLayoutCarTypeIChoose.setVisibility(View.INVISIBLE);
		frameLayoutListViewIndex.setVisibility(View.INVISIBLE);
		radioGroupCarTypeTaxi.setVisibility(View.VISIBLE);
	}
	/*
	 * 返回按钮点击事件
	 */
	public void OnButtonCarTypeBackClicked(View view)
	{
		this.finish();
	}
	/** 绘制索引列表 */
	public void getIndexView() {
		LinearLayout.LayoutParams params = new LayoutParams(
				LayoutParams.WRAP_CONTENT, height);
		// params.setMargins(10, 5, 10, 0);
		for (int i = 0; i < str.length; i++) {
			final TextView tv = new TextView(this);
			tv.setLayoutParams(params);
			tv.setText(str[i]);
			// tv.setTextColor(Color.parseColor("#606060"));
			// tv.setTextSize(16);
			tv.setPadding(10, 0, 10, 0);
			layoutIndex.addView(tv);
			layoutIndex.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event)

				{
					float y = event.getY();
					int index = (int) (y / height);
					if (index > -1 && index < str.length) {// 防止越界
						String key = str[index];
						if (adapter.getSelector().containsKey(key)) {
							int pos = adapter.getSelector().get(key);
							if (listView.getHeaderViewsCount() > 0) {// 防止ListView有标题栏，本例中没有。
								listView.setSelectionFromTop(
										pos + listView.getHeaderViewsCount(), 0);
							} else {
								listView.setSelectionFromTop(pos, 0);// 滑动到第一项
							}
							tv_show.setVisibility(View.VISIBLE);
							tv_show.setText(str[index]);
						}
					}
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						layoutIndex.setBackgroundColor(Color
								.parseColor("#606060"));
						break;

					case MotionEvent.ACTION_MOVE:

						break;
					case MotionEvent.ACTION_UP:
						layoutIndex.setBackgroundColor(Color
								.parseColor("#00ffffff"));
						tv_show.setVisibility(View.INVISIBLE);
						break;
					}
					return true;
				}
			});
		}
	}

}
