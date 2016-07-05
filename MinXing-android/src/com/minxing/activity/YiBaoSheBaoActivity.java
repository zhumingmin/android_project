package com.minxing.activity;

import com.minxing.restwebservice.YiBaoService;
import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class YiBaoSheBaoActivity extends Activity {
	private Button chakan, zuixinzhengce1;
	private ImageButton fanhui;
	private TextView yibao, text_yibao;
	private WebView mWebView;
	LinearLayout ly_yibao;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_yibaoguanli);
		yibao = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.yibao);
		text_yibao = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.text_yibao);
		ly_yibao = (LinearLayout) findViewById(R.id.ly_yibao);
		chakan = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.ChaKan);
		mWebView = (WebView) findViewById(com.zhumingmin.vmsofminxing.R.id.WebView15);
		String str = "广东省人力资源和社会保障厅、医疗保险处处长夏青现场透露，目前广东已开始启动医保卡一卡通工作，并希望通过2-3年的努力，完成从医保总量的管理到现在进行一卡通的管理，最终实现医保一卡通互认。“这意味着，省内医疗保险参保人，在省内进行跨市就医时，可享受到医疗保险即时报销。”"
				+ "\n"
				+ "\n市医保局对城镇职工医保卡的使用作出明确规定，新政仅扩大了(医保卡个人账户资金)的支付范围，但非扩大医保政策的适用范围。“通俗点说，亲属刷医保卡就医消费，只能建立在卡内有钱可刷的基础上”。"
				+ "\n"
				+ "\n碰到刷亲属医保卡就诊的患者，广东省第二人民医院医保科科长余敏表示，一般情况下，门诊收费人员都会仔细查看，非本人参保人来刷卡时是否携带有参保人身份证、户口簿什么的证明亲属关系的文件。但门诊量较大时，医院往往也就凭就医患者是否能准确输入密码来核定身份。“毕竟，医保卡密码是参保人提供给就医患者，这就已经能证明其与参保人的关系了。”";
		text_yibao.setText(str);
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

				Builder builder = new Builder(YiBaoSheBaoActivity.this);
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
				Builder builder = new Builder(YiBaoSheBaoActivity.this);
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
						.from(YiBaoSheBaoActivity.this);
				final View dialogview = factory.inflate(
						com.zhumingmin.vmsofminxing.R.layout.prom_dialog, null);

				((TextView) dialogview
						.findViewById(com.zhumingmin.vmsofminxing.R.id.TextView_PROM))
						.setText(message);

				((EditText) dialogview
						.findViewById(com.zhumingmin.vmsofminxing.R.id.EditText_PROM))
						.setText(defaultValue);

				Builder builder = new Builder(YiBaoSheBaoActivity.this);
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
				YiBaoSheBaoActivity.this.getWindow().setFeatureInt(
						Window.FEATURE_PROGRESS, newProgress * 100);
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				YiBaoSheBaoActivity.this.setTitle(title);
				super.onReceivedTitle(view, title);
			}
		});
		// mWebView.loadUrl("http://shebao.388g.com/elbx/guangdong/");

		yibao.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("查询医疗保险最新政策，办理医保缴费查询业务！");
			}
		});
		// zuixinzhengce1.setOnClickListener(new Button.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Intent intent = new Intent();
		// intent.setClass(YiBaoGuanLi.this, ZuiXinZhengCe1.class);
		// startActivity(intent);
		// YiBaoGuanLi.this.finish();
		// }
		// });

		ly_yibao.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				YiBaoSheBaoActivity.this.finish();

			}
		});
		chakan.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(YiBaoSheBaoActivity.this, YiBaoService.class);
				startActivity(intent);
				YiBaoSheBaoActivity.this.finish();
			}
		});
	}

	private void DisplayToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {

			mWebView.goBack();
			return true;
		}
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {
			// Intent intent = new Intent();
			// intent.setClass(YiBaoGuanLi.this, CaiDanLan.class);
			// startActivity(intent);
			YiBaoSheBaoActivity.this.finish();

		}
		return super.onKeyDown(keyCode, event);
	}
}
