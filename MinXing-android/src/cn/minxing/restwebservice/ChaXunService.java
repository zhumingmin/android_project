package cn.minxing.restwebservice;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.minxing.util.CustomArrayAdapter;

import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ChaXunService extends Activity {
	LinearLayout ly_cx;
	private Spinner sp_chaxunxiangmu;
	private CustomArrayAdapter<CharSequence> mAdapter;
	private TextView tv_tijiao, tv_chuli, tv_wancheng;
	private TextView tv_tijiao_shijian, tv_chuli_shijian, tv_wancheng_shijian;
	String shijian;
	private Button btn_chaxun;
	private EditText et_zhanghao;
	static String account;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.webservice_chaxun);

		ly_cx = (LinearLayout) findViewById(R.id.ly_chaxun);
		tv_tijiao = (TextView) findViewById(R.id.tv_tijiao);
		tv_chuli = (TextView) findViewById(R.id.tv_chuli);
		tv_wancheng = (TextView) findViewById(R.id.tv_wancheng);
		tv_tijiao_shijian = (TextView) findViewById(R.id.tv_tijiao_shijian);
		tv_chuli_shijian = (TextView) findViewById(R.id.tv_chuli_shijian);
		tv_wancheng_shijian = (TextView) findViewById(R.id.tv_wancheng_shijian);
		btn_chaxun = (Button) findViewById(R.id.btn_chaxun);
		et_zhanghao = (EditText) findViewById(R.id.et_zhanghao);
		LoginService ls = new LoginService();
		account = ls.account();
		et_zhanghao.setText(account);
		btn_chaxun.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("网上查询，暂未开通");

			}
		});

		ly_cx.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();

			}
		});

		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日 HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		shijian = formatter.format(curDate);
		String[] kexuanxiangmu = getResources().getStringArray(
				R.array.chaxun_item);
		this.mAdapter = new CustomArrayAdapter<CharSequence>(this,
				kexuanxiangmu);

		sp_chaxunxiangmu = (Spinner) findViewById(com.zhumingmin.vmsofminxing.R.id.sp_chaxunxiangmu);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, kexuanxiangmu);
		sp_chaxunxiangmu.setAdapter(adapter);
		sp_chaxunxiangmu
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						arg0.setVisibility(View.VISIBLE);
						if (arg2 == 0) {
							tv_tijiao.setTextColor(getResources().getColor(
									R.color.white));
							tv_tijiao.setText(" " + "提交ing" + " ");
							tv_tijiao_shijian.setText(shijian);
							tv_tijiao_shijian.setTextColor(getResources()
									.getColor(R.color.ll_chaxun));
							tv_tijiao.setBackgroundColor(getResources()
									.getColor(R.color.red));
						}
						if (arg2 == 1) {
							tv_chuli.setTextColor(getResources().getColor(
									R.color.white));
							tv_chuli.setText(" " + "处理ing" + " ");
							tv_chuli_shijian.setText(shijian);
							tv_chuli_shijian.setTextColor(getResources()
									.getColor(R.color.ll_chaxun));
							tv_chuli.setBackgroundColor(getResources()
									.getColor(R.color.red));
						}
						if (arg2 == 2) {
							tv_wancheng.setTextColor(getResources().getColor(
									R.color.white));
							tv_wancheng.setText(" " + "已完成" + " ");
							tv_wancheng_shijian.setText(shijian);
							tv_wancheng_shijian.setTextColor(getResources()
									.getColor(R.color.ll_chaxun));
							tv_wancheng.setBackgroundColor(getResources()
									.getColor(R.color.red));
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

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

			finish();

		}
		return super.onKeyDown(keyCode, event);
	}
}
