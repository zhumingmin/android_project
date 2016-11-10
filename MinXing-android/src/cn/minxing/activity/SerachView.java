package cn.minxing.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;






import cn.minxing.util.News;
import cn.minxing.util.NewsListViewAdapter;
import cn.minxing.util.RecordSQLiteOpenHelper;
import cn.minxing.util.XMLParser;
import cn.minxing.view.MyListView;
import cn.minxing.view.PullToRefreshListView;

import com.zhumingmin.vmsofminxing.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SerachView extends Activity {
	private PullToRefreshListView pullToRefreshListView;
	private News news;
	private ImageButton fanhui1;
	public static List<News> newsDataList = new ArrayList<News>();
	private NewsListViewAdapter newsListViewAdapter;
	protected static final int RESULT_SPEECH = 1;
	Button but = null;
	Button dubiaoqian, xiebiaoqian, but2, soufanshiliu, soufenjiao, soumugua,
			souqita, zhijiesousuo, jiafen, koufen;
	ImageButton btnSpeak;
	EditText newsousuo;
	private WebView mWebView;
	private LinearLayout ly_fanhui;
	private TextView tv_tip;
	private MyListView listView;
	private TextView tv_clear;
	private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);;
	private SQLiteDatabase db;
	private BaseAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_nongyezhushou);
		this.initNewsData();
		this.testLoadNewsData();
		newsListViewAdapter = new NewsListViewAdapter(SerachView.this,
				newsDataList, R.layout.news_list_item);

		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.frame_listview_news);
		tv_tip = (TextView) findViewById(R.id.tv_tip);
		listView = (cn.minxing.view.MyListView) findViewById(R.id.listView);
		tv_clear = (TextView) findViewById(R.id.tv_clear);

		pullToRefreshListView.setAdapter(newsListViewAdapter);
		soufanshiliu = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.soufanshiliu);
		soufenjiao = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.soufenjiao);
		soumugua = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.soumugua);
		souqita = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.souqita);
		zhijiesousuo = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.zhijiesousuo);
		jiafen = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.jiafen);
		koufen = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.koufen);
		btnSpeak = (ImageButton) findViewById(com.zhumingmin.vmsofminxing.R.id.btnSpeak);
		newsousuo = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.newsousuo);
		ly_fanhui = (LinearLayout) findViewById(com.zhumingmin.vmsofminxing.R.id.ly_fanhui_nongyezhushou);
		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SerachView.this.finish();
			}
		});

		//
		tv_clear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				deleteData();
				queryData("");
			}
		});

		// 搜索框的键盘搜索键点击回调
		newsousuo.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键

					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (keyCode == KeyEvent.KEYCODE_ENTER
								&& event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
							// 先隐藏键盘
							((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
									.hideSoftInputFromWindow(getCurrentFocus()
											.getWindowToken(),
											InputMethodManager.HIDE_NOT_ALWAYS);
							// 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
							boolean hasData = hasData(newsousuo.getText()
									.toString().trim());
							if (!hasData) {
								insertData(newsousuo.getText().toString()
										.trim());
								queryData("");
							}
							// TODO 根据输入的内容模糊查询商品，并跳转到另一个界面，由你自己去实现
							Toast.makeText(SerachView.this, "clicked!",
									Toast.LENGTH_SHORT).show();

						}
						return false;
					}
				});

		// 搜索框的文本变化实时监听
		newsousuo.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString().trim().length() == 0) {
					tv_tip.setText("搜索历史");
				} else {
					tv_tip.setText("搜索结果");
				}
				String tempName = newsousuo.getText().toString();
				// 根据tempName去模糊查询数据库中有没有数据
				queryData(tempName);

			}
		});

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView textView = (TextView) view
						.findViewById(android.R.id.text1);
				String name = textView.getText().toString();
				newsousuo.setText(name);
				Toast.makeText(SerachView.this, name, Toast.LENGTH_SHORT)
						.show();
				// TODO 获取到item上面的文字，根据该关键字跳转到另一个页面查询，由你自己去实现
			}
		});

		// 插入数据，便于测试，否则第一次进入没有数据怎么测试呀？
		Date date = new Date();
		long time = date.getTime();
		insertData("Leo" + time);

		// 第一次进入查询所有的历史记录
		queryData("");

		//
		mWebView = (WebView) findViewById(com.zhumingmin.vmsofminxing.R.id.WebView12);
		/*
		 * 点击了这个按钮需要提升这条结果被搜索到的几率
		 */
		jiafen.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("感谢您的反馈！");
			}
		});

		/*
		 * 对结果不满意时需要提供原因选项，之后加上
		 */
		koufen.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("感谢您的建议！");
			}
		});
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

				Builder builder = new Builder(SerachView.this);
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
				Builder builder = new Builder(SerachView.this);
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
						.from(SerachView.this);
				final View dialogview = factory.inflate(
						com.zhumingmin.vmsofminxing.R.layout.prom_dialog, null);

				((TextView) dialogview
						.findViewById(com.zhumingmin.vmsofminxing.R.id.TextView_PROM))
						.setText(message);

				((EditText) dialogview
						.findViewById(com.zhumingmin.vmsofminxing.R.id.EditText_PROM))
						.setText(defaultValue);

				Builder builder = new Builder(SerachView.this);
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
				SerachView.this.getWindow().setFeatureInt(
						Window.FEATURE_PROGRESS, newProgress * 100);
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				SerachView.this.setTitle(title);
				super.onReceivedTitle(view, title);
			}
		});

		btnSpeak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

				try {
					startActivityForResult(intent, RESULT_SPEECH);
					newsousuo.setText("");
				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Ops! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}
			}
		});

		zhijiesousuo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String keyword = null;
				if (newsousuo.getText().toString() != null
						&& newsousuo.getText().length() != 0
						&& !newsousuo.getText().toString().equals("")) {
					keyword = newsousuo.getText().toString();
				}

				// Intent intent = new Intent(SerachView.this,
				// ZhiJieSouSuo.class);
				// intent.putExtra("keyword", keyword);
				// startActivity(intent);
				mWebView.loadUrl("http://www.baidu.com/s?&wd=" + keyword);
			}

		});
		soufanshiliu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent();
				// intent.setClass(SerachView.this, SouFanShiLiu.class);
				// startActivity(intent);
				// SerachView.this.finish();
				mWebView.loadUrl("https://www.baidu.com/s?wd=%E7%95%AA%E7%9F%B3%E6%A6%B4%E7%A7%8D%E6%A4%8D&rsv_spt=1&rsv_iqid=0xc3aac06f0001535b&issp=1&f=8&rsv_bp=0&rsv_idx=2&ie=utf-8&tn=09060019_2_pg&rsv_enter=1&inputT=6724");
			}

		});
		soufenjiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent();
				// intent.setClass(SerachView.this, SouFenJiao.class);
				// startActivity(intent);
				// SerachView.this.finish();
				mWebView.loadUrl("https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=2&dsp=baidubrowser&tn=09060019_2_pg&wd=%E7%B2%89%E8%95%89%E7%A7%8D%E6%A4%8D&rsv_spt=1&oq=%E7%95%AA%E7%9F%B3%E6%A6%B4%E7%A7%8D%E6%A4%8D&rsv_pq=9bc88d3200018c02&rsv_t=98e7Z950nEHwg%2FVlZv9ZSveVQSCar9QxoxXrZuuOGH6wKIPfUE0tD%2BdTC0705uvN6RIsBw&rsv_enter=1&rsv_sug3=2&rsv_sug1=2&rsv_sug2=0&inputT=2292&rsv_sug4=2293");
			}

		});
		soumugua.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent();
				// intent.setClass(SerachView.this, SouMuGua.class);
				// startActivity(intent);
				// SerachView.this.finish();
				mWebView.loadUrl("https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=2&dsp=baidubrowser&tn=09060019_2_pg&wd=%E6%9C%A8%E7%93%9C%E7%A7%8D%E6%A4%8D&rsv_spt=1&oq=%E7%B2%89%E8%95%89%E7%A7%8D%E6%A4%8D&rsv_pq=b4091d2500014754&rsv_t=464619kWUtIBOVkBZyx05QUj6ftf8yw%2BDVQBBNnJU%2BYzZnHIZDVZ1mExVfzNE8xCQVh%2BDw&rsv_enter=1&inputT=1677&rsv_sug3=4&rsv_sug1=4&rsv_sug2=0&rsv_sug4=1677");
			}

		});
		souqita.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent();
				// intent.setClass(SerachView.this, SouQiTa.class);
				// startActivity(intent);
				// SerachView.this.finish();
				mWebView.loadUrl("https://www.baidu.com/index.php?tn=09060019_2_pg&ch=8");
			}

		});
	}

	private void DisplayToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SPEECH: {
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				newsousuo.setText(text.get(0));
			}
			break;
		}

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {

			mWebView.goBack();
			return true;
		}
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {
			// Intent intent = new Intent();
			// intent.setClass(SerachView.this, TuiJian.class);
			// startActivity(intent);
			SerachView.this.finish();

		}
		return super.onKeyDown(keyCode, event);
	}

	// 为ListView列添加单击事件
	class ListViewItemOnClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			DisplayToast("稍后开通！");
			System.out.println("----->OnClick");
			// 这里可以自由发挥，比如播放一首歌曲等等
		}
	}

	private void initNewsData() {
		for (int i = 1; i <= 20; i++) {

			news = new News("为您提供的相关搜索结果" + i, "华农的学生", "2016-1-" + i, 12 + i,
					"新闻内容" + i);

			newsDataList.add(news);
		}
	}

	private void testLoadNewsData() {
		Log.w("当前newsDataList中新闻数量为", String.valueOf(newsDataList.size()));
		for (int i = 1; i <= newsDataList.size(); i++) {
			News news = newsDataList.get(i - 1);
			Log.i("第" + i + "条新闻标题", news.getTitle());
			Log.i("第" + i + "条新闻作者", news.getAuthor());
			Log.i("第" + i + "条新闻发表日期", news.getPubDate());
			Log.i("第" + i + "条新闻评论数", String.valueOf(news.getCommentCount()));
			Log.i("第" + i + "条新闻内容", news.getBody());
		}
	}

	/**
	 * 插入数据
	 */
	private void insertData(String tempName) {
		db = helper.getWritableDatabase();
		db.execSQL("insert into records(name) values('" + tempName + "')");
		db.close();
	}

	/**
	 * 模糊查询数据
	 */
	@SuppressLint("NewApi")
	private void queryData(String tempName) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select id as _id,name from records where name like '%"
						+ tempName + "%' order by id desc ", null);
		// 创建adapter适配器对象
		adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_1, cursor,
				new String[] { "name" }, new int[] { android.R.id.text1 },
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		// 设置适配器
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	/**
	 * 检查数据库中是否已经有该条记录
	 */
	private boolean hasData(String tempName) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select id as _id,name from records where name =?",
				new String[] { tempName });
		// 判断是否有下一个
		return cursor.moveToNext();
	}

	/**
	 * 清空数据
	 */
	private void deleteData() {
		db = helper.getWritableDatabase();
		db.execSQL("delete from records");
		db.close();
	}

}
