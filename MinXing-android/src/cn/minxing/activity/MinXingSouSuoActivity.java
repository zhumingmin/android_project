package cn.minxing.activity;

import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.AdapterView.OnItemClickListener;
import cn.minxing.util.ActionItem;
import cn.minxing.util.RecordSQLiteOpenHelper;
import cn.minxing.view.MyListView;
import cn.minxing.view.TitlePopup;

import com.zhumingmin.vmsofminxing.R;

public class MinXingSouSuoActivity extends Activity {
	protected static final int RESULT_SPEECH = 1;
	private LinearLayout ly_fanhui;
	private TextView tv_tip;
	private MyListView listView;
	private TextView tv_clear;
	private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);;
	private SQLiteDatabase db;
	private BaseAdapter adapter;
	EditText newsousuo;
	Button zhijiesousuo, soufanshiliu, soufenjiao, soumugua, souqita;
	ImageButton btnSpeak,sousuoshezhi;
	private TitlePopup titlePopup;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_minxingsousuo);
		ly_fanhui = (LinearLayout) findViewById(R.id.ly_fanhui_sousuo);
		tv_tip = (TextView) findViewById(R.id.tv_tip1);
		listView = (cn.minxing.view.MyListView) findViewById(R.id.listView1);
		btnSpeak = (ImageButton) findViewById(com.zhumingmin.vmsofminxing.R.id.btnSpeak1);
		soufanshiliu = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.soufanshiliu1);
		soufenjiao = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.soufenjiao1);
		soumugua = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.soumugua1);
		souqita = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.souqita1);
		tv_clear = (TextView) findViewById(R.id.tv_clear1);
		sousuoshezhi = (ImageButton) findViewById(R.id.sousuoshezhi);
		sousuoshezhi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				titlePopup.show(v);
			}
		});
		titlePopup = new TitlePopup(this, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		initData();
		zhijiesousuo = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.zhijiesousuo1);
		newsousuo = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.newsousuo1);

		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		soufanshiliu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String data = soufanshiliu.getText().toString();
				Intent intent = new Intent(MinXingSouSuoActivity.this,
						SouFanShiLiuActivity.class);
				intent.putExtra("reci", data);
				startActivity(intent);

			}

		});
		soufenjiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String data = soufenjiao.getText().toString();
				Intent intent = new Intent(MinXingSouSuoActivity.this,
						SouFenJiaoActivity.class);
				intent.putExtra("reci", data);
				startActivity(intent);

			}

		});
		soumugua.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String data = soumugua.getText().toString();
				Intent intent = new Intent(MinXingSouSuoActivity.this,
						SouMuGuaActivity.class);
				intent.putExtra("reci", data);
				startActivity(intent);

			}

		});
		souqita.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String data = souqita.getText().toString();
				Intent intent = new Intent(MinXingSouSuoActivity.this,
						SouQiTaActivity.class);
				intent.putExtra("reci", data);
				startActivity(intent);

			}

		});
		zhijiesousuo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MinXingSouSuoActivity.this,
						MinXingSouSuoJieGuoActivity.class);
				startActivity(intent);

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
							Toast.makeText(MinXingSouSuoActivity.this,
									"clicked!", Toast.LENGTH_SHORT).show();

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
				Toast.makeText(MinXingSouSuoActivity.this, name,
						Toast.LENGTH_SHORT).show();
				// TODO 获取到item上面的文字，根据该关键字跳转到另一个页面查询，由你自己去实现
			}
		});

		// 插入数据，便于测试，否则第一次进入没有数据怎么测试呀？
		Date date = new Date();
		long time = date.getTime();
		insertData("Leo" + time);

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
	private void initData() {

		titlePopup.addAction(new ActionItem(this, "仅中文",
				R.drawable.mm_title_btn_compose_normal));
		titlePopup.addAction(new ActionItem(this, "仅英文",
				R.drawable.mm_title_btn_receiver_normal));
		titlePopup.addAction(new ActionItem(this, "中英混合",
				R.drawable.mm_title_btn_keyboard_normal));
		titlePopup.addAction(new ActionItem(this, "推荐设置",
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

			MinXingSouSuoActivity.this.finish();
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
