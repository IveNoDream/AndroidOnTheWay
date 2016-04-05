package com.demo.androidontheway.http;

import com.demo.androidontheway.R;

import android.app.Activity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewTest extends Activity {

	private WebView mWebView;
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_activity);
		mWebView = (WebView) findViewById(R.id.wv_test);
		
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}
			
		});
		mWebView.loadUrl("http://www.baidu.com");
	};
}
