package com.minxing.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint("SetJavaScriptEnabled")
public class TianQiChaXunActivity extends Activity {
	// private Button mButton;
	// private EditText mEditText;
	private WebView mWebView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_tianqichaxun);

		mWebView = (WebView) findViewById(com.zhumingmin.vmsofminxing.R.id.WebView02);

		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		webSettings.setAllowFileAccess(true);

		webSettings.setBuiltInZoomControls(true);

		mWebView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}
		});

		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					final JsResult result) {

				Builder builder = new Builder(TianQiChaXunActivity.this);
				builder.setTitle("提示对话框");
				builder.setMessage(message);
				builder.setPositiveButton(android.R.string.ok,
						new AlertDialog.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								result.confirm();
							}
						});
				builder.setCancelable(false);
				builder.create();
				builder.show();
				return true;
			};

			@Override
			public boolean onJsConfirm(WebView view, String url,
					String message, final JsResult result) {
				Builder builder = new Builder(TianQiChaXunActivity.this);
				builder.setTitle("带选择的对话框");
				builder.setMessage(message);
				builder.setPositiveButton(android.R.string.ok,
						new AlertDialog.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								result.confirm();
							}
						});
				builder.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								result.cancel();
							}
						});
				builder.setCancelable(false);
				builder.create();
				builder.show();
				return true;
			};

			@Override
			public boolean onJsPrompt(WebView view, String url, String message,
					String defaultValue, final JsPromptResult result) {

				final LayoutInflater factory = LayoutInflater
						.from(TianQiChaXunActivity.this);
				final View dialogview = factory.inflate(
						com.zhumingmin.vmsofminxing.R.layout.prom_dialog, null);

				((TextView) dialogview
						.findViewById(com.zhumingmin.vmsofminxing.R.id.TextView_PROM))
						.setText(message);

				((EditText) dialogview
						.findViewById(com.zhumingmin.vmsofminxing.R.id.EditText_PROM))
						.setText(defaultValue);

				Builder builder = new Builder(TianQiChaXunActivity.this);
				builder.setTitle("带输入的对话框");
				builder.setView(dialogview);
				builder.setPositiveButton(android.R.string.ok,
						new AlertDialog.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								String value = ((EditText) dialogview
										.findViewById(com.zhumingmin.vmsofminxing.R.id.EditText_PROM))
										.getText().toString();
								result.confirm(value);
							}
						});
				builder.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								result.cancel();
							}
						});
				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					public void onCancel(DialogInterface dialog) {
						result.cancel();
					}
				});
				builder.show();
				return true;
			};

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				TianQiChaXunActivity.this.getWindow().setFeatureInt(
						Window.FEATURE_PROGRESS, newProgress * 100);
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				TianQiChaXunActivity.this.setTitle(title);
				super.onReceivedTitle(view, title);
			}
		});

		// mButton.setOnClickListener(new OnClickListener() {
		// public void onClick(View v) {
		// try {

		// String url = mEditText.getText().toString();

		// if (URLUtil.isNetworkUrl(url)) {

		// mWebView.loadUrl(url);
		// } else {
		// mEditText.setText("输入网址错误，请重新输入");
		// }
		// } catch (Exception e) {
		// Log.e(DEBUG_TAG, e.toString());
		// }
		// }
		// });
		// mWebView.loadUrl("http://www.weather.com.cn/weather/101280101.shtml");
		mWebView.loadUrl("http://www.nmc.cn/publish/forecast/AGD/guangzhou.html");

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {

			mWebView.goBack();
			return true;
		}
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {
//			Intent intent = new Intent();
//			intent.setClass(TianQiChaXun.this, SheZhi.class);
//			startActivity(intent);
			TianQiChaXunActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
