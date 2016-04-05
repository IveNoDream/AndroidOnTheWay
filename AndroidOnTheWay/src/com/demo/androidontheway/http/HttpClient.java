package com.demo.androidontheway.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.demo.androidontheway.R;

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
	private Button btnHttpURLConnection;
	private Button btnHttpClient;
	private TextView tvHttpClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.http_client_activity);

		btnHttpURLConnection = (Button) findViewById(R.id.btn_http_url_connection_test);
		btnHttpClient = (Button) findViewById(R.id.btn_http_client_test);
		tvHttpClient = (TextView) findViewById(R.id.tv_http_client);
		btnHttpURLConnection.setOnClickListener(this);
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

	private void sendRequestWithHttpClient() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					DefaultHttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet("http://www.baidu.com");
					HttpResponse httpResponse = httpClient.execute(httpGet);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						// 请求和响应都成功了
						HttpEntity entity = httpResponse.getEntity();
						String response = EntityUtils.toString(entity, "utf-8");
						Message message = new Message();
						message.what = SHOW_RESPONSE;
						// 将服务器返回的结果存放到Message中
						message.obj = response.toString();
						handler.sendMessage(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

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
		case R.id.btn_http_url_connection_test:
			sendRequestWithHttpURLConnection();
			break;
		case R.id.btn_http_client_test:
			sendRequestWithHttpClient();
			break;

		default:
			break;
		}
	}
}
