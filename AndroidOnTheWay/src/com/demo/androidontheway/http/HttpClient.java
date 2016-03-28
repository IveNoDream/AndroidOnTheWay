package com.demo.androidontheway.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.wafejlu.uiproductionfactory.R;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HttpClient extends Activity implements OnClickListener {

	public static final int SHOW_RESPONSE = 0;
	private Button btnHttpClient;
	private TextView tvHttpClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.http_client_activity);

		btnHttpClient = (Button) findViewById(R.id.btn_http_client_test);
		tvHttpClient = (TextView) findViewById(R.id.tv_http_client);
		btnHttpClient.setOnClickListener(this);
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SHOW_RESPONSE:
				String response = (String) msg.obj;
				// 在这里进行UI操作,将结果显示到界面上
				tvHttpClient.setText(response);
				break;

			default:
				break;
			}
		};
	};

	private void sendRequestWithHttpURLConnection() {
		// 开启线程来发起网络请求
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					URL url = new URL("http://www.baidu.com");
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
					// 下面对获取到的输入流进行读取
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					Message message = new Message();
					message.what = SHOW_RESPONSE;
					// 将服务器返回的结果存放到Message中
					message.obj = response.toString();
					handler.sendMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_http_client_test:
			sendRequestWithHttpURLConnection();
			break;

		default:
			break;
		}
	}
}
