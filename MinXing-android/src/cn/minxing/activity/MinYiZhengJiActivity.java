package cn.minxing.activity;

import cn.minxing.activity.HeTongGuanLiActivity.TimeThread;
import cn.minxing.util.CustomArrayAdapter;

import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MinYiZhengJiActivity extends Activity {
	private Button zhichi, weiguan;
	private EditText huoqucanxuanzhiwu, huoqugerenshiji;
	private Spinner xuanzecanxuanren;
	private TextView shijian;
	private static final String[] kexuancanxuanren = { "项目一", "项目二", "项目三",
			"项目四", "项目五" };
	ProgressDialog m_pDialog;
	int m_count = 0;
	private CustomArrayAdapter<CharSequence> mAdapter;
	private static final int msgKey1 = 1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_minyizhengji);
		zhichi = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.zhichi);
		weiguan = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.weiguan);
		shijian = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.shijian);
		new TimeThread().start();
		huoqucanxuanzhiwu = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.huoqucanxuanzhiwu);
		huoqugerenshiji = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.huoqugerenshiji);

		zhichi.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("您已支持该参选人！");
			}
		});

		String[] kexuancanxuanren = getResources()
				.getStringArray(R.array.item4);
		this.mAdapter = new CustomArrayAdapter<CharSequence>(this,
				kexuancanxuanren);

		xuanzecanxuanren = (Spinner) findViewById(com.zhumingmin.vmsofminxing.R.id.xuanzecanxuanren);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, kexuancanxuanren);
		xuanzecanxuanren.setAdapter(adapter);
		xuanzecanxuanren
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						arg0.setVisibility(View.VISIBLE);
						huoqucanxuanzhiwu.setText("副书记");
						huoqugerenshiji.setText("水利建设主要负责人");
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}

				});
		weiguan.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("选出自己心目中的好村委！");
				Intent intent = new Intent(MinYiZhengJiActivity.this,
						TouPiaoJieGuoActivity.class);
				startActivity(intent);
				MinYiZhengJiActivity.this.finish();
			}
		});
	}

	private void DisplayToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {
			// Intent intent = new Intent();
			// intent.setClass(MinYiZhengJi.this, SheZhi.class);
			// startActivity(intent);
			MinYiZhengJiActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	public class TimeThread extends Thread {
		@Override
		public void run() {
			do {
				try {
					Thread.sleep(1000);
					Message msg = new Message();
					msg.what = msgKey1;
					mHandler.sendMessage(msg);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (true);
		}
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case msgKey1:
				long sysTime = System.currentTimeMillis();
				CharSequence sysTimeStr = DateFormat.format("yyyy年MM月dd日",
						sysTime);
				shijian.setText(sysTimeStr);
				break;

			default:
				break;
			}
		}
	};
}
