package com.minxing.activity;

import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ShiShiXinWenActivity extends Activity {
	private Button fanhui, redianxinwen, cunneixinwen;
	private ImageButton fanhui1;
	private TextView xinwen;
	private WebView mWebView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_shishixinwen);
		xinwen = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.xinwen);
		fanhui1 = (ImageButton) findViewById(com.zhumingmin.vmsofminxing.R.id.FanHui);
		redianxinwen = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.ReDianXinWen);
		cunneixinwen = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.CunNeiXinWen);
		// AssetManager mgr = getAssets();// 得到AssetManager
		// Typeface tf = Typeface.createFromAsset(mgr, "fonts/FZLTCXHJW.TTF");//
		// 根据路径得到Typeface
		// fanhui.setTypeface(tf);
		// redianxinwen.setTypeface(tf);
		// cunneixinwen.setTypeface(tf);
		mWebView = (WebView) findViewById(com.zhumingmin.vmsofminxing.R.id.xinwenWebView);

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

				Builder builder = new Builder(ShiShiXinWenActivity.this);
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
				Builder builder = new Builder(ShiShiXinWenActivity.this);
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
						.from(ShiShiXinWenActivity.this);
				final View dialogview = factory.inflate(
						com.zhumingmin.vmsofminxing.R.layout.prom_dialog, null);

				((TextView) dialogview
						.findViewById(com.zhumingmin.vmsofminxing.R.id.TextView_PROM))
						.setText(message);

				((EditText) dialogview
						.findViewById(com.zhumingmin.vmsofminxing.R.id.EditText_PROM))
						.setText(defaultValue);

				Builder builder = new Builder(ShiShiXinWenActivity.this);
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
				ShiShiXinWenActivity.this.getWindow().setFeatureInt(
						Window.FEATURE_PROGRESS, newProgress * 100);
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				ShiShiXinWenActivity.this.setTitle(title);
				super.onReceivedTitle(view, title);
			}
		});
		mWebView.loadUrl("http://guonei.news.baidu.com/");

		xinwen.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("查看最新新闻以及村内新闻！");
			}
		});
		fanhui1.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ShiShiXinWenActivity.this, YeWuBanLiActivity.class);
				startActivity(intent);
				ShiShiXinWenActivity.this.finish();
			}
		});
		// redianxinwen.setOnClickListener(new Button.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Intent intent = new Intent();
		// intent.setClass(ShiShiXinWen.this, LianJieXinWen.class);
		// startActivity(intent);
		// ShiShiXinWen.this.finish();
		// }
		// });
		redianxinwen.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				final String[] items = getResources().getStringArray(
						R.array.item3);
				new AlertDialog.Builder(ShiShiXinWenActivity.this)
						.setTitle("请点击跳转相关页面！")
						.setItems(items, new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								new AlertDialog.Builder(ShiShiXinWenActivity.this)
										.setTitle("你选择了:" + items[which])
										.setMessage("准备跳转，请确认")
										.setPositiveButton(
												"确认跳转",
												new android.content.DialogInterface.OnClickListener() {
													@Override
													public void onClick(
															DialogInterface arg0,
															int arg1) {
														// TODO Auto-generated
														// method stub
														arg0.dismiss();
														Intent in2 = new Intent();
														in2.setAction(Intent.ACTION_VIEW);
														if (items.toString() == "网易新闻") {
															in2.setData(Uri
																	.parse("http://news.163.com"));
															startActivity(in2);
														} else if (items
																.toString() == "新浪新闻") {
															in2.setData(Uri
																	.parse("http://news.sina.com.hk/"));
															startActivity(in2);
														} else if (items
																.toString() == "腾讯新闻") {
															in2.setData(Uri
																	.parse("http://news.qq.com/"));
															startActivity(in2);
														} else if (items
																.toString() == "搜狐新闻") {
															in2.setData(Uri
																	.parse("http://news.sohu.com/"));
															startActivity(in2);
														} else {
															in2.setData(Uri
																	.parse("http://news.ifeng.com/"));
															startActivity(in2);
														}

													}
												})
										.setNegativeButton(
												"下次阅读",
												new DialogInterface.OnClickListener() {

													public void onClick(
															DialogInterface dialog,
															int which) {
														// 这里点击取消之后可以进行的操作
													}
												}).show();
							}
						}).show();
			}
		});
		cunneixinwen.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("准备接入pc端村内新闻，暂未开通");
			}
		});
	}

	private void DisplayToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {

			mWebView.goBack();
			return true;
		}
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {
//			Intent intent = new Intent();
//			intent.setClass(ShiShiXinWen.this, CaiDanLan.class);
//			startActivity(intent);
			ShiShiXinWenActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
				finish();
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
				break;
			default:
				break;
			}
		}
	};
}
