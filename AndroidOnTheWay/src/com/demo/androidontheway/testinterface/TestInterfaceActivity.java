package com.demo.androidontheway.testinterface;

import com.demo.androidontheway.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 使用场景
 * 小明想要问小红借10块钱,小红答应借. 但是小红很关心小明还钱,因为小红想要在小明还钱之后,去买好吃的蛋糕.
 * 这个时候, 小红又不能时时刻刻催着小明还钱. 只能等小明还钱的时候通知小红了;
 * 那么, 小明通知小红的这个过程, 我把它叫做 回调;
 * @author root
 *
 */
public class TestInterfaceActivity extends Activity implements XiaoMingRepayCallback{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_interface_activity);
		Button btnBorrowButton = (Button) findViewById(R.id.btn_borrow);
		final XiaoMing xiaoMing = new XiaoMing(this);
		btnBorrowButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				xiaoMing.JieQian();
			}
		});
	}

	@Override
	public boolean onRepay() {
		// TODO Auto-generated method stub
		Log.i("jieqian", "xiaohong recieve money");
		return true;
	}
}
