package cn.minxing.rsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import cn.minxing.util.RS_ActionItem;
import cn.minxing.util.RS_News;
import cn.minxing.util.RS_RecordSQLiteOpenHelper;
import cn.minxing.view.RS_MyListView;
import cn.minxing.view.RS_TitlePopup;

import com.zhumingmin.vmsofminxing.R;

/*
 * 这里需要完成的操作有：
 * 1.语音转文字
 * 2.搜索热词的动态更新
 * 3.输入搜索关键词后的关联列表
 * 4.点击搜索热词后的详情跳转
 * 5.点击更多后的个性化设置操作
 */
public class SerachActivity extends Activity {
	private static final String SERVICE_URL = "http://192.168.191.1:8080/RestWebServiceDemo/rest/keyword";
	private static final String SERVICE_URL2 = "http://192.168.191.1:8080/RestWebServiceDemo/rest/hotwords";
	private static final String TAG = "SerachActivity";
	protected static final int RESULT_SPEECH = 1;
	private LinearLayout ly_fanhui;
	private TextView tv_tip;
	private RS_MyListView listView;
	private TextView tv_clear;
	private RS_RecordSQLiteOpenHelper helper = new RS_RecordSQLiteOpenHelper(
			this);;
	private SQLiteDatabase db;
	private BaseAdapter adapter;
	EditText newsousuo;
	Button zhijiesousuo, soufanshiliu, soufenjiao, soumugua, souqita,
			sousuoshezhi;
	ImageButton btnSpeak;
	private RS_TitlePopup titlePopup;
	boolean isReqing = false;

	// /**
	// * 调用onCreate(), 目的是刷新数据,
	// * 从另一activity界面返回到该activity界面时, 此方法自动调用
	// */
	// @Override
	// protected void onResume() {
	// super.onResume();
	// onCreate(null);
	// }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_minxingsousuo_rs);
		Intent intent = getIntent();
		if (intent != null) {
			if (!isReqing) {
				String sampleURL = SERVICE_URL2 + "/1";
				WebServiceTask wst = new WebServiceTask(
						WebServiceTask.GET_TASK, SerachActivity.this,
						"正在加载，请稍候...");
				wst.execute(new String[] { sampleURL });
				isReqing = true;
			} else {
				return;
			}

		}
		newsousuo = (EditText) findViewById(R.id.newsousuo1);
		ly_fanhui = (LinearLayout) findViewById(R.id.ly_fanhui_sousuo);
		tv_tip = (TextView) findViewById(R.id.tv_tip1);
		listView = (cn.minxing.view.RS_MyListView) findViewById(R.id.listView1);
		btnSpeak = (ImageButton) findViewById(R.id.btnSpeak1);
		soufanshiliu = (Button) findViewById(R.id.soufanshiliu1);
		soufenjiao = (Button) findViewById(R.id.soufenjiao1);
		soumugua = (Button) findViewById(R.id.soumugua1);
		souqita = (Button) findViewById(R.id.souqita1);
		tv_clear = (TextView) findViewById(R.id.tv_clear1);
		sousuoshezhi = (Button) findViewById(R.id.sousuoshezhi);
		sousuoshezhi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				titlePopup.show(v);
			}
		});
		titlePopup = new RS_TitlePopup(this, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		initData();
		zhijiesousuo = (Button) findViewById(R.id.zhijiesousuo1);
		newsousuo = (EditText) findViewById(R.id.newsousuo1);

		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		// 从后台获取搜索热词

		soufanshiliu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String keyword = soufanshiliu.getText().toString();

				WebServiceTask wst = new WebServiceTask(
						WebServiceTask.POST_TASK, SerachActivity.this,
						"正在跳转，请稍后……");

				wst.addNameValuePair("keyword", keyword);

				// the passed String is the URL we will POST to
				wst.execute(new String[] { SERVICE_URL });
				String classname = "SerachActivity";
				Intent intent = new Intent(SerachActivity.this,
						SerachListActivity.class);
				intent.putExtra("classname", classname);
				startActivity(intent);
				finish();
			}

		});
		soufenjiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String keyword = soufenjiao.getText().toString();

				WebServiceTask wst = new WebServiceTask(
						WebServiceTask.POST_TASK, SerachActivity.this,
						"正在跳转，请稍后……");

				wst.addNameValuePair("keyword", keyword);

				// the passed String is the URL we will POST to
				wst.execute(new String[] { SERVICE_URL });
				String classname = "SerachActivity";
				Intent intent = new Intent(SerachActivity.this,
						SerachListActivity.class);
				intent.putExtra("classname", classname);
				startActivity(intent);
				finish();
			}

		});
		soumugua.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String keyword = soumugua.getText().toString();

				WebServiceTask wst = new WebServiceTask(
						WebServiceTask.POST_TASK, SerachActivity.this,
						"正在跳转，请稍后……");

				wst.addNameValuePair("keyword", keyword);

				// the passed String is the URL we will POST to
				wst.execute(new String[] { SERVICE_URL });
				String classname = "SerachActivity";
				Intent intent = new Intent(SerachActivity.this,
						SerachListActivity.class);
				startActivity(intent);
				intent.putExtra("classname", classname);
				finish();
			}

		});
		souqita.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String keyword = souqita.getText().toString();

				WebServiceTask wst = new WebServiceTask(
						WebServiceTask.POST_TASK, SerachActivity.this,
						"正在跳转，请稍后……");

				wst.addNameValuePair("keyword", keyword);

				// the passed String is the URL we will POST to
				wst.execute(new String[] { SERVICE_URL });
				String classname = "SerachActivity";
				Intent intent = new Intent(SerachActivity.this,
						SerachListActivity.class);
				intent.putExtra("classname", classname);
				startActivity(intent);
				finish();
			}

		});
		zhijiesousuo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String keyword = newsousuo.getText().toString();

				if (keyword.equals("")) {
					Toast.makeText(getApplicationContext(), "关键词不能空！", 0)
							.show();
				}

				WebServiceTask wst = new WebServiceTask(
						WebServiceTask.POST_TASK, SerachActivity.this,
						"正在搜索，请稍后…");

				wst.addNameValuePair("keyword", keyword);

				// the passed String is the URL we will POST to
				wst.execute(new String[] { SERVICE_URL });
				String classname = "SerachActivity";
				Intent intent = new Intent(SerachActivity.this,
						SerachListActivity.class);
				intent.putExtra("classname", classname);
				startActivity(intent);
				finish();
			}

		});

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

							String keyword = newsousuo.getText().toString();

							if (keyword.equals("")) {
								Toast.makeText(getApplicationContext(),
										"关键词不能空！", 0).show();
							}

							WebServiceTask wst = new WebServiceTask(
									WebServiceTask.POST_TASK,
									SerachActivity.this, "正在搜索，请稍后……");

							wst.addNameValuePair("keyword", keyword);

							// the passed String is the URL we will POST to
							wst.execute(new String[] { SERVICE_URL });
							Intent intent = new Intent(SerachActivity.this,
									SerachListActivity.class);
							startActivity(intent);
							finish();
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
				Toast.makeText(SerachActivity.this, name, Toast.LENGTH_SHORT)
						.show();
				// TODO 获取到item上面的文字，根据该关键字跳转到另一个页面查询，由你自己去实现
			}
		});

		// 插入数据，便于测试，否则第一次进入没有数据怎么测试呀？
		// Date date = new Date();
		// long time = date.getTime();
		// insertData("Leo" + time);

		// 第一次进入查询所有的历史记录
		queryData("");

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
	}

	public void handleResponse(String response) {

		// EditText tianxiekeyword = (EditText) findViewById(R.id.newsousuo1);
		soufanshiliu = (Button) findViewById(R.id.soufanshiliu1);
		soufenjiao = (Button) findViewById(R.id.soufenjiao1);
		soumugua = (Button) findViewById(R.id.soumugua1);
		souqita = (Button) findViewById(R.id.souqita1);
		// tianxiekeyword.setText("");
		// soufanshiliu.setText("");
		// soufenjiao.setText("");
		// soumugua.setText("");
		// souqita.setText("");

		try {

			JSONObject jso = new JSONObject(response);

			// String keyword = jso.getString("keyword");
			// tianxiekeyword.setText(keyword);
			String hotrWords = jso.getString("keyWord");

			String pattern = "([-*/^()\\]\\[])";
			final String rWords = hotrWords.replaceAll(pattern, "");

			new Thread() {
				public void run() {
					// 这儿是耗时操作，完成之后更新UI；
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// 更新UI
							String[] strArray = null;
							strArray = convertStrToArray(rWords);
							if (strArray.length >= 4) {
								soufanshiliu.setText(strArray[0].replace("\"",
										""));

								soufenjiao.setText(strArray[1]
										.replace("\"", ""));
								soumugua.setText(strArray[2].replace("\"", ""));
								souqita.setText(strArray[3].replace("\"", ""));
							} else if (strArray.length == 3) {
								soufanshiliu.setText(strArray[0].replace("\"",
										""));

								soufenjiao.setText(strArray[1]
										.replace("\"", ""));
								soumugua.setText(strArray[2].replace("\"", ""));
								souqita.setText("无更多搜索热词");
							} else if (strArray.length == 2) {
								soufanshiliu.setText(strArray[0].replace("\"",
										""));

								soufenjiao.setText(strArray[1]
										.replace("\"", ""));
								soumugua.setText("无更多搜索热词");
								souqita.setText("无更多搜索热词");
							} else if (strArray.length == 1) {
								soufanshiliu.setText(strArray[0].replace("\"",
										""));

								soufenjiao.setText("无更多搜索热词");
								soumugua.setText("无更多搜索热词");
								souqita.setText("无更多搜索热词");
							} else if (strArray.length == 0) {
								soufanshiliu.setText("无更多搜索热词");

								soufenjiao.setText("无更多搜索热词");
								soumugua.setText("无更多搜索热词");
								souqita.setText("无更多搜索热词");
							}
						}

					});
				}
			}.start();

		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}

	}

	public static String[] convertStrToArray(String str) {
		String[] strArray = null;
		strArray = str.split(","); // 拆分字符为"," ,然后把结果交给数组strArray
		return strArray;
	}

	// 主要操作部分
	private class WebServiceTask extends AsyncTask<String, Integer, String> {

		public static final int POST_TASK = 1;
		public static final int GET_TASK = 2;

		private static final String TAG = "WebServiceTask";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 5000;

		private int taskType = GET_TASK;
		private Context mContext = null;
		private String processMessage = "Processing...";

		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		private ProgressDialog pDlg = null;

		public WebServiceTask(int taskType, Context mContext,
				String processMessage) {

			this.taskType = taskType;
			this.mContext = mContext;
			this.processMessage = processMessage;
		}

		public void addNameValuePair(String name, String value) {

			params.add(new BasicNameValuePair(name, value));
			// HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		}

		private void showProgressDialog() {

			pDlg = new ProgressDialog(mContext);
			pDlg.setMessage(processMessage);
			pDlg.setProgressDrawable(mContext.getWallpaper());
			pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDlg.setCancelable(false);
			pDlg.show();

		}

		@Override
		protected void onPreExecute() {

			showProgressDialog();

		}

		protected String doInBackground(String... urls) {

			String url = urls[0];
			String result = "";

			HttpResponse response = doResponse(url);

			if (response == null) {
				return result;
			} else {

				try {

					result = inputStreamToString(response.getEntity()
							.getContent());

				} catch (IllegalStateException e) {
					Log.e(TAG, e.getLocalizedMessage(), e);

				} catch (IOException e) {
					Log.e(TAG, e.getLocalizedMessage(), e);
				}

			}

			return result;
		}

		@Override
		protected void onPostExecute(String response) {

			handleResponse(response);
			// if (response != null) {
			// Toast.makeText(getApplicationContext(), "成功！", 0).show();
			//
			// } else {
			// Toast.makeText(getApplicationContext(), "失败！", 0).show();
			// }
			pDlg.dismiss();

		}

		// Establish connection and socket (data retrieval) timeouts
		private HttpParams getHttpParams() {

			HttpParams htpp = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
			HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

			return htpp;
		}

		private HttpResponse doResponse(String url) {

			// Use our connection and data timeouts as parameters for our
			// DefaultHttpClient
			HttpClient httpclient = new DefaultHttpClient(getHttpParams());

			HttpResponse response = null;

			try {
				switch (taskType) {

				case POST_TASK:
					HttpPost httppost = new HttpPost(url);
					// Add parameters
					httppost.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					response = httpclient.execute(httppost);
					break;
				case GET_TASK:
					HttpGet httpget = new HttpGet(url);
					response = httpclient.execute(httpget);
					break;
				}
			} catch (Exception e) {

				Log.e(TAG, e.getLocalizedMessage(), e);

			}

			return response;
		}

		private String inputStreamToString(InputStream is) {

			String line = "";
			StringBuilder total = new StringBuilder();

			// Wrap a BufferedReader around the InputStream
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			try {
				// Read response until the end
				while ((line = rd.readLine()) != null) {
					total.append(line);
				}
			} catch (IOException e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
			}

			// Return full string
			return total.toString();
		}

	}

	private void initData() {

		titlePopup.addAction(new RS_ActionItem(this, "仅中文",
				R.drawable.mm_title_btn_compose_normal));
		titlePopup.addAction(new RS_ActionItem(this, "仅英文",
				R.drawable.mm_title_btn_receiver_normal));
		titlePopup.addAction(new RS_ActionItem(this, "中英混合",
				R.drawable.mm_title_btn_keyboard_normal));
		titlePopup.addAction(new RS_ActionItem(this, "推荐设置",
				R.drawable.mm_title_btn_qrcode_normal));
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

		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			finish();
		}
		return super.onKeyDown(keyCode, event);
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
