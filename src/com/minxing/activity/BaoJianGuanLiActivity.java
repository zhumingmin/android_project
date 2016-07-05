package com.minxing.activity;

import com.minxing.util.CustomArrayAdapter;
import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BaoJianGuanLiActivity extends Activity {
	private Button tijiao2, chaxunjindu;
	private ImageButton fanhui;
	private TextView baojianxiangmu, dizhi, lianxifangshi, yuyuebaojianshijian,
			baojian;
	private Spinner xiangmuxuanze, xuanzebaojianshijian;
	private static final String[] kexuanxiangmu = { "项目一", "项目二", "项目三", "项目四",
			"项目五" };
	private static final String[] kexuanshijian = { "周一上午9:00~10:00",
			"周一下午3:00~4:00", "周二上午9:00~10:00", "周二下午3:00~4:00",
			"周三上午9:00~10:00", "周三下午3:00~4:00", "周四上午9:00~10:00",
			"周四下午3:00~4:00", "周五上午9:00~10:00", "周五下午3:00~4:00" };
	private EditText tianxiedizhi, tianxielianxifangshi;
	ProgressDialog m_pDialog;
	int m_count = 0;
	private CustomArrayAdapter<CharSequence> mAdapter;
	LinearLayout ly_baojian;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.webservice_baojianguanli);
		ly_baojian = (LinearLayout) findViewById(R.id.ly_baojian);

		fanhui = (ImageButton) findViewById(com.zhumingmin.vmsofminxing.R.id.FanHui);
		baojian = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.baojian);
		chaxunjindu = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.ChaXunJinDu);
		baojianxiangmu = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.BaoJianXiangMu);
		dizhi = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.DiZhi);
		lianxifangshi = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.LianXiFangShi);
		tianxiedizhi = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.et_baojiandizhi);
		tianxielianxifangshi = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.et_lianxifangshi);
		tijiao2 = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.TiJiao2);
		yuyuebaojianshijian = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.YuYueShiJian);
		baojian.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("办理报建业务！");
			}
		});
		// AssetManager mgr = getAssets();// 得到AssetManager
		// Typeface tf = Typeface.createFromAsset(mgr, "fonts/FZLTCXHJW.TTF");//
		// 根据路径得到Typeface
		// fanhui.setTypeface(tf);
		// chaxunjindu.setTypeface(tf);
		// baojianxiangmu.setTypeface(tf);
		// dizhi.setTypeface(tf);
		// lianxifangshi.setTypeface(tf);
		// tianxiedizhi.setTypeface(tf);
		// tianxielianxifangshi.setTypeface(tf);
		// tijiao2.setTypeface(tf);
		// yuyuebaojianshijian.setTypeface(tf);

		tianxiedizhi.setHint("如民兴村3号");
		tianxielianxifangshi.setHint("如1234567890");

		String[] kexuanxiangmu = getResources().getStringArray(R.array.item1);
		this.mAdapter = new CustomArrayAdapter<CharSequence>(this,
				kexuanxiangmu);

		String[] kexuanshijian = getResources().getStringArray(R.array.item2);
		this.mAdapter = new CustomArrayAdapter<CharSequence>(this,
				kexuanshijian);

		xiangmuxuanze = (Spinner) findViewById(com.zhumingmin.vmsofminxing.R.id.sp_baojianxiangmu);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, kexuanxiangmu);
		xiangmuxuanze.setAdapter(adapter);
		xiangmuxuanze
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						arg0.setVisibility(View.VISIBLE);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}

				});
		xuanzebaojianshijian = (Spinner) findViewById(com.zhumingmin.vmsofminxing.R.id.sp_yuyueshijian);
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, kexuanshijian);
		xuanzebaojianshijian.setAdapter(adapter1);
		xuanzebaojianshijian
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						arg0.setVisibility(View.VISIBLE);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}

				});
		ly_baojian.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(BaoJianGuanLiActivity.this, YeWuBanLiActivity.class);
				startActivity(intent);
				BaoJianGuanLiActivity.this.finish();
			}
		});
		tijiao2.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				m_count = 0;

				m_pDialog = new ProgressDialog(BaoJianGuanLiActivity.this);

				m_pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

				m_pDialog.setTitle("提示");

				m_pDialog.setMessage("正在提交，请稍后……");

				m_pDialog
						.setIcon(com.zhumingmin.vmsofminxing.R.drawable.tubiao1);

				m_pDialog.setProgress(100);

				m_pDialog.setIndeterminate(false);

				m_pDialog.setCancelable(true);

				m_pDialog.show();

				new Thread() {
					public void run() {
						try {
							while (m_count <= 100) {

								m_pDialog.setProgress(m_count++);
								Thread.sleep(100);
							}
							m_pDialog.cancel();
						} catch (InterruptedException e) {
							m_pDialog.cancel();
						}
					}
				}.start();

			}
		});
		chaxunjindu.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("网上查询，暂未开通");
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
			// intent.setClass(BaoJianGuanLi.this, CaiDanLan.class);
			// startActivity(intent);
			BaoJianGuanLiActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
