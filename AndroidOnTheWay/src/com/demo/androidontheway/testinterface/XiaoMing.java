package com.demo.androidontheway.testinterface;

import android.util.Log;

public class XiaoMing {
	XiaoMingRepayCallback repayXiaoHong;
	public XiaoMing(XiaoMingRepayCallback callback){
		this.repayXiaoHong = callback;
	}
	
	public void JieQian(){
		Log.i("jieqian","borrow money from xiaohong");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (repayXiaoHong.onRepay()) {
			Log.i("jieqian", "xiaoming repay xiaohong");
		}else {
			Log.i("jieqian", "repay error");
		}
	}
}
