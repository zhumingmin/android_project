package com.minxing.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ZhuCeActivity extends Activity {
	private TextView zhanghao1, mima1, mima2;
	private EditText tianxiezhanghao1, tianxieshoujihaoma1, tianxiexingming1,
			tianxiemima1, tianxiemima2;
	private Button wancheng, quxiao, zhuce;
	SQLiteDatabase db;
	private MyTask mTask;
	private LinearLayout ly_fanhui;
	private String mStrName, mStrResult;

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//db.close();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.webservice_zhuce);
		zhanghao1 = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.ZhangHao1);
		tianxiezhanghao1 = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieZhangHao1);
		tianxieshoujihaoma1 = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieShouJiHaoMa1);

		tianxiexingming1 = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieXingMing1);
		mima1 = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.MiMa1);
		mima2 = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.MiMa2);
		tianxiemima1 = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieMiMa1);
		tianxiemima2 = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.TianXieMiMa2);
		zhuce = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.zhucewancheng);
		wancheng = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.WanCheng);

		ly_fanhui = (LinearLayout) findViewById(com.zhumingmin.vmsofminxing.R.id.ly_fanhui_zhuce);

		tianxiemima2.setOnFocusChangeListener(new OnFocusChangeListener() {

			public void onFocusChange(View v, boolean hasFocus) { // TODO
				// Auto-generated method stub if (hasFocus) { // 获得焦点处理
				if (tianxiemima2.length() != 0
						&& !(tianxiemima2.getText().toString() == tianxiemima1
								.getText().toString()))
					DisplayToast("两次密码输入不同");
			}
		});

		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();

				intent.setClass(ZhuCeActivity.this, DengLuJieMianActivity.class);

				startActivity(intent);
				ZhuCeActivity.this.finish();
			}
		});

		zhuce.setOnClickListener(new Button.OnClickListener() {

			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = tianxiezhanghao1.getText().toString();
				String password = tianxiemima1.getText().toString();

				if (!(name.equals("") || password.equals(""))) {
					if (addUser(name, password)) {
						DialogInterface.OnClickListener ss = new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								// 跳转到登录界面
								mStrName = "account:"
										+ tianxiezhanghao1.getText().toString()
										+ " " + "name:"
										+ tianxiezhanghao1.getText().toString()
										+ " " + "password1:"
										+ tianxiemima1.getText().toString()
										+ " " + "password2:"
										+ tianxiemima2.getText().toString();
								mTask = new MyTask();
								mTask.execute(mStrName);
								Intent in = new Intent();
								in.setClass(ZhuCeActivity.this, DengLuJieMianActivity.class);
								startActivity(in);
								// 销毁当前activity
								ZhuCeActivity.this.onDestroy();

							}
						};
						new AlertDialog.Builder(ZhuCeActivity.this).setTitle("注册成功")
								.setMessage("注册成功").setPositiveButton("确定", ss)
								.show();

					} else {
						new AlertDialog.Builder(ZhuCeActivity.this).setTitle("注册失败")
								.setMessage("注册失败")
								.setPositiveButton("确定", null);
					}
				} else {
					new AlertDialog.Builder(ZhuCeActivity.this).setTitle("帐号密码不能为空")
							.setMessage("帐号密码不能为空")
							.setPositiveButton("确定", null);
				}
				if (tianxiezhanghao1.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "账号不能空！", 0).show();
					return;
				}
				if (tianxiexingming1.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "姓名不能空！", 0).show();
					return;
				}
				if (tianxieshoujihaoma1.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "手机号码不能为空！", 0)
							.show();
					return;
				}

				if (tianxiemima1.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "密码不能为空！", 0)
							.show();
					return;
				}
				if (tianxiemima2.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "密码不能为空！", 0)
							.show();
					return;
				}

			}
		});

	}

	// 添加用户
	public Boolean addUser(String name, String password) {
		String str = "insert into tb_user values(?,?) ";
		DengLuJieMianActivity main = new DengLuJieMianActivity();
		db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()
				+ "/test.dbs", null);
		main.db = db;
		try {
			db.execSQL(str, new String[] { name, password });
			return true;
		} catch (Exception e) {
			main.createDb();
		}
		return false;
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
			// 显示对话框
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

	private void DisplayToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	private class MyTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HttpClient hc = new DefaultHttpClient();

			// String address =
			// "http://192.168.191.1:8080/fileUpload/servlet/JsonServlet";
			String address = "http://192.168.191.1:8080/ServerJsonDemo/servlet/JsonServlet";
			HttpPost hp = new HttpPost(address);

			JSONObject jsonObj = new JSONObject();
			String age = null, id = null;
			try {
				jsonObj.put("name", params[0]);

				hp.setEntity(new StringEntity(jsonObj.toString()));

				HttpResponse response = hc.execute(hp);

				if (response.getStatusLine().getStatusCode() == 200) {

					mStrResult = EntityUtils.toString(response.getEntity());

					JSONObject result = new JSONObject(mStrResult);

					age = result.getString("age");
					id = result.getString("id");
					System.out.println("id:" + id + "age:" + age);
					System.out.println("result" + mStrResult);
				} else {
					System.out.println("上传失败");
				}

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "员工号:" + id + " 年龄:" + age;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			DisplayToast(result);

		}
	}
}
