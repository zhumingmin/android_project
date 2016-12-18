package cn.minxing.activity;

import com.zhumingmin.vmsofminxing.R;

import cn.minxing.util.EncodingHandler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SZ_GRXXActivity extends Activity {
	private TextView grxx_xingming_tv, grxx_lianxifangshi_tv,
			grxx_shenfenzhenghao_tv;
	private EditText grxx_xingming_et, grxx_lianxifangshi_et,
			grxx_shenfenzhenghao_et;
	private Button grxx_chuansong, shengcheng;
	private ImageView qrcodeImageView = null;
	private LinearLayout ly_fanhui;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_gerenxinxi);
		grxx_xingming_tv = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.grxx_xingming_tv);
		grxx_lianxifangshi_tv = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.grxx_lianxifangshi_tv);
		grxx_shenfenzhenghao_tv = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.grxx_shenfenzhenghao_tv);
		grxx_xingming_et = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.grxx_xingming_et);
		grxx_lianxifangshi_et = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.grxx_lianxifangshi_et);
		grxx_shenfenzhenghao_et = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.grxx_shenfenzhenghao_et);
		grxx_chuansong = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.grxx_chuansong);
		shengcheng = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.shengcheng);
		qrcodeImageView = (ImageView) findViewById(com.zhumingmin.vmsofminxing.R.id.erweima);
		ly_fanhui = (LinearLayout) findViewById(R.id.ly_fanhui_gerenxinxi);
		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		// 这里需要修改
		Intent intent = getIntent();
		String data1 = intent.getStringExtra("data1");
		String data2 = intent.getStringExtra("data2");
		String data3 = intent.getStringExtra("data3");

		grxx_xingming_et.setText(data1);
		grxx_lianxifangshi_et.setText(data2);
		grxx_shenfenzhenghao_et.setText(data3);

		grxx_chuansong.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (hasNfc(SZ_GRXXActivity.this)) {
					Intent intent = new Intent();
					intent.setClass(SZ_GRXXActivity.this,
							AndroidBeamMainActivity.class);
					intent.putExtra("GGXX", grxx_xingming_tv.getText()
							.toString().trim()
							+ grxx_xingming_et.getText().toString().trim()
							+ "\n"
							+ grxx_lianxifangshi_tv.getText().toString().trim()
							+ grxx_lianxifangshi_et.getText().toString().trim()
							+ "\n"
							+ grxx_shenfenzhenghao_tv.getText().toString()
									.trim()
							+ grxx_shenfenzhenghao_et.getText().toString()
									.trim());

					startActivity(intent);

				} else {
					DisplayToast("您的设备暂不支持该功能！");
				}
			}
		});
		shengcheng.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String content = grxx_xingming_tv.getText().toString().trim()
						+ grxx_xingming_et.getText().toString().trim() + "\n"
						+ grxx_lianxifangshi_tv.getText().toString().trim()
						+ grxx_lianxifangshi_et.getText().toString().trim()
						+ "\n"
						+ grxx_shenfenzhenghao_tv.getText().toString().trim()
						+ grxx_shenfenzhenghao_et.getText().toString().trim();

				if (null == content || "".equals(content)) {
					Toast.makeText(SZ_GRXXActivity.this, "无法生成二维码...",
							Toast.LENGTH_SHORT).show();
					return;
				}

				try {

					Bitmap qrcodeBitmap = EncodingHandler.createQRCode(content,
							400);
					qrcodeImageView.setImageBitmap(qrcodeBitmap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	@SuppressLint("NewApi")
	public static boolean hasNfc(Context context) {
		boolean bRet = false;
		if (context == null)
			return bRet;
		NfcManager manager = (NfcManager) context
				.getSystemService(Context.NFC_SERVICE);
		NfcAdapter adapter = manager.getDefaultAdapter();
		if (adapter != null && adapter.isEnabled()) {
			// adapter存在，能启用
			bRet = true;
		}
		return bRet;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			// Intent intent = new Intent();
			// intent.setClass(SZ_GRXX.this, SheZhi.class);
			// startActivity(intent);
			SZ_GRXXActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	protected void DisplayToast(String string) {

		// TODO Auto-generated method stub
		Toast.makeText(SZ_GRXXActivity.this, string, Toast.LENGTH_SHORT).show();
	}
}
