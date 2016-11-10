package cn.minxing.activity;

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

public class JiHuaShengYuActivity extends Activity {
	private Button zuixinzhengce;
	private ImageButton fanhui;
	private TextView jisheng, text;
	private WebView mWebView;
	LinearLayout ly_jihua;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_jihuashengyu);
		jisheng = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.jisheng);
		fanhui = (ImageButton) findViewById(com.zhumingmin.vmsofminxing.R.id.FanHui);
		zuixinzhengce = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.ZuiXinZhengCe);
		text = (TextView) this.findViewById(R.id.text);
		ly_jihua = (LinearLayout) findViewById(R.id.ly_jihua);

		mWebView = (WebView) findViewById(com.zhumingmin.vmsofminxing.R.id.WebView14);
		String str = "2015年12月27日第十二届全国人民代表大会常务委员会第十八次会议决定对《中华人民共和国人口与计划生育法》作出了修改，计划生育新政策2016年1月1日起施行。计划生育新政策2016年相关规定如下："
				+ "\n第十八条 国家提倡一对夫妻生育两个子女。"
				+ "\n"
				+ "\n符合法律、法规规定条件的，可以要求安排再生育子女。具体办法由省、自治区、直辖市人民代表大会或者其常务委员会规定。"
				+ "\n"
				+ "\n少数民族也要实行计划生育，具体办法由省、自治区、直辖市人民代表大会或者其常务委员会规定。"
				+ "\n"
				+ "\n夫妻双方户籍所在地的省、自治区、直辖市之间关于再生育子女的规定不一致的，按照有利于当事人的原则适用。"
				+ "\n"
				+ "\n第二十条 育龄夫妻自主选择计划生育避孕节育措施，预防和减少非意愿妊娠。"
				+ "\n"
				+ "\n第二十五条 符合法律、法规规定生育子女的夫妻，可以获得延长生育假的奖励或者其他福利待遇。"
				+ "\n"
				+ "\n第二十七条 在国家提倡一对夫妻生育一个子女期间，自愿终身只生育一个子女的夫妻，国家发给《独生子女父母光荣证》。"
				+ "\n"
				+ "\n获得《独生子女父母光荣证》的夫妻，按照国家和省、自治区、直辖市有关规定享受独生子女父母奖励。"
				+ "\n"
				+ "\n法律、法规或者规章规定给予获得《独生子女父母光荣证》的夫妻奖励的措施中由其所在单位落实的，有关单位应当执行。"
				+ "\n"
				+ "\n获得《独生子女父母光荣证》的夫妻，独生子女发生意外伤残、死亡的，按照规定获得扶助。"
				+ "\n"
				+ "\n在国家提倡一对夫妻生育一个子女期间，按照规定应当享受计划生育家庭老年人奖励扶助的，继续享受相关奖励扶助。";
		text.setText(str);
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

				Builder builder = new Builder(JiHuaShengYuActivity.this);
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
				Builder builder = new Builder(JiHuaShengYuActivity.this);
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
						.from(JiHuaShengYuActivity.this);
				final View dialogview = factory.inflate(
						com.zhumingmin.vmsofminxing.R.layout.prom_dialog, null);

				((TextView) dialogview
						.findViewById(com.zhumingmin.vmsofminxing.R.id.TextView_PROM))
						.setText(message);

				((EditText) dialogview
						.findViewById(com.zhumingmin.vmsofminxing.R.id.EditText_PROM))
						.setText(defaultValue);

				Builder builder = new Builder(JiHuaShengYuActivity.this);
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
				JiHuaShengYuActivity.this.getWindow().setFeatureInt(
						Window.FEATURE_PROGRESS, newProgress * 100);
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				JiHuaShengYuActivity.this.setTitle(title);
				super.onReceivedTitle(view, title);
			}
		});

		// mWebView.loadUrl("http://baike.pcbaby.com.cn/qzbd/1270013.html");

		jisheng.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("查询计划生育最近政策！");
			}
		});
		ly_jihua.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent();
				// intent.setClass(JiHuaShengYuActivity.this, YeWuBanLi.class);
				// startActivity(intent);
				JiHuaShengYuActivity.this.finish();

			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {

			mWebView.goBack();
			return true;
		}
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {
			// Intent intent = new Intent();
			// intent.setClass(JiHuaShengYu.this, CaiDanLan.class);
			// startActivity(intent);
			JiHuaShengYuActivity.this.finish();

		}
		return super.onKeyDown(keyCode, event);
	}

	private void DisplayToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}
}
