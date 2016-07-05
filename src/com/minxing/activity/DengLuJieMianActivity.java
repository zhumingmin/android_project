package com.minxing.activity;

import com.minxing.restwebservice.RegisterService;
import com.zhumingmin.vmsofminxing.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Typeface;

@SuppressLint("CutPasteId")
public class DengLuJieMianActivity extends Activity {
	private TextView zhaohuimima, mianfeizhuce, huanyindenglu, zhanghao, mima;
	private EditText tianxiezhanghao, tianxiemima;
	private CheckBox jizhumima;
	private Button denglu;
	int m_count = 0;
	ProgressDialog m_pDialog;
	public SQLiteDatabase db;
	protected static final int GUI_STOP_NOTIFIER = 0x108;
	protected static final int GUI_THREADING_NOTIFIER = 0x109;
	public int intCounter = 0;

	CheckBox savePasswordCB;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setProgressBarVisibility(true);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.webservice_denglu);
		tianxiezhanghao = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieZhangHao);
		tianxiemima = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieMiMa);
		savePasswordCB = (CheckBox) findViewById(R.id.savePasswordCB);

		db = SQLiteDatabase.openOrCreateDatabase(DengLuJieMianActivity.this
				.getFilesDir().toString() + "/test.dbs", null);
		// 接收设置中的注销登录的操作
		Intent intent = getIntent();
		int from = intent.getIntExtra("from", 0);
		switch (from) {
		case 1:
			tianxiezhanghao.setText("");
			tianxiemima.setText("");
			savePasswordCB.setSaveEnabled(false);
			break;
		}
		mianfeizhuce = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.MianFeiZhuCe);
		huanyindenglu = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.huanyindenglu);
		zhanghao = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.ZhangHao);
		mima = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.MiMa);
		jizhumima = (CheckBox) findViewById(com.zhumingmin.vmsofminxing.R.id.savePasswordCB);
		zhaohuimima = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.ZhaoHuiMiMa);
		denglu = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.DengLu);

		tianxiezhanghao
				.setFilters(new InputFilter[] { new InputFilter.LengthFilter(18) });
		tianxiemima
				.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });
		SharedPreferences remdname = getPreferences(Activity.MODE_PRIVATE);
		String name_str = remdname.getString("name", "");
		String pass_str = remdname.getString("pass", "");
		tianxiezhanghao.setText(name_str);
		tianxiemima.setText(pass_str);

		tianxiezhanghao.setHint("您的身份证号码");
		tianxiemima.setHint("六位包括字母和数字");

		savePasswordCB
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							SharedPreferences remdname = getPreferences(Activity.MODE_PRIVATE);
							SharedPreferences.Editor edit = remdname.edit();
							edit.putString("name", tianxiezhanghao.getText()
									.toString());
							edit.putString("pass", tianxiemima.getText()
									.toString());
							edit.commit();
						}
						if (!isChecked) {

							SharedPreferences remdname = getPreferences(Activity.MODE_PRIVATE);
							SharedPreferences.Editor edit = remdname.edit();
							edit.putString("name", "");
							edit.putString("pass", "");
							edit.commit();
						}
					}

				});

		zhaohuimima.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(DengLuJieMianActivity.this, WangJiMiMaActivity.class);
				startActivity(intent);
				DengLuJieMianActivity.this.finish();
			}
		});

		denglu.setOnClickListener(new Button.OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				m_pDialog = new ProgressDialog(DengLuJieMianActivity.this);

				m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

				m_pDialog.setTitle("提示");

				m_pDialog.setMessage("正在登陆，请稍后……");

				m_pDialog
						.setIcon(com.zhumingmin.vmsofminxing.R.drawable.tubiao1);

				m_pDialog.setIndeterminate(false);

				m_pDialog.setCancelable(true);

				m_pDialog.setButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int i) {

								dialog.cancel();
							}
						});

				m_pDialog.show();
				if (savePasswordCB.isChecked())// 检测用户名密码
				{
					SharedPreferences remdname = getPreferences(Activity.MODE_PRIVATE);
					SharedPreferences.Editor edit = remdname.edit();
					edit.putString("name", tianxiezhanghao.getText().toString());
					edit.putString("pass", tianxiemima.getText().toString());
					edit.commit();
				}
				Intent intent = new Intent();
				// intent.setClass(DengLuJieMian.this, Home.class);
				// intent.setClass(DengLuJieMian.this, CaiDanLan.class);
				intent.setClass(DengLuJieMianActivity.this, YeWuBanLiActivity.class);
				startActivity(intent);
				DengLuJieMianActivity.this.finish();

			}
		});

		mianfeizhuce.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(DengLuJieMianActivity.this, ZhuCeActivity.class);
				// intent.setClass(DengLuJieMian.this, RegisterService.class);
				startActivity(intent);
				DengLuJieMianActivity.this.finish();
			}
		});
		denglu.setOnClickListener(new LoginListener());

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		db.close();
	}

	class LoginListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String name = tianxiezhanghao.getText().toString();
			String password = tianxiemima.getText().toString();
			if (name.equals("") || password.equals("")) {
				// 弹出消息框
				new AlertDialog.Builder(DengLuJieMianActivity.this).setTitle("错误")
						.setMessage("帐号或密码不能空").setPositiveButton("确定", null)
						.show();
			} else {
				isUserinfo(name, password);
			}
		}

		// 判断输入的用户是否正确
		public Boolean isUserinfo(String name, String pwd) {
			try {
				String str = "select * from tb_user where name=? and password=?";
				Cursor cursor = db.rawQuery(str, new String[] { name, pwd });
				if (cursor.getCount() <= 0) {
					new AlertDialog.Builder(DengLuJieMianActivity.this).setTitle("错误")
							.setMessage("帐号或密码错误！")
							.setPositiveButton("确定", null).show();
					return false;
				} else {
					new AlertDialog.Builder(DengLuJieMianActivity.this).setTitle("正确")
							.setMessage("成功登录")
							.setPositiveButton("正在登陆……", null).show();

					Intent intent = new Intent();

					// intent.setClass(DengLuJieMian.this, Home.class);
					// intent.setClass(DengLuJieMian.this, CaiDanLan.class);
					intent.setClass(DengLuJieMianActivity.this, YeWuBanLiActivity.class);

					startActivity(intent);

					DengLuJieMianActivity.this.finish();

					return true;
				}

			} catch (SQLiteException e) {
				createDb();
			}
			return false;
		}

	}

	// 创建数据库和用户表
	public void createDb() {
		db.execSQL("create table tb_user( name varchar(30) primary key,password varchar(30))");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater()
				.inflate(com.zhumingmin.vmsofminxing.R.menu.menu, menu);
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 创建退出对话框
			AlertDialog isExit = new AlertDialog.Builder(this).create();
			// 设置对话框标题
			isExit.setTitle("系统提示");
			// 设置对话框消息
			isExit.setMessage("确定要退出吗");
			// 添加选择按钮并注册监听
			isExit.setButton("确定", listener);
			isExit.setButton2("取消", listener);

			// 显示对话框]
			isExit.show();

		}

		return false;

	}

	/** 监听对话框里面的button点击事件 */
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
