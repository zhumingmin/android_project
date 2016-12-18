package cn.minxing.activity;

import com.zhumingmin.vmsofminxing.R;

import cn.minxing.fragment.WoDeFragment;
import cn.minxing.restfulwebservice.CertificateRest;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ZhangHaoAnQuanActivity extends Activity {

	private Button zhanghaoanquan, bangzhu, guanyu, shezhi_zhanghaoanquan,
			anquanzhongxin, ceshirukou;
	private TextView xianshizhanghao, xianshixingming, xianshishoujihao;
	private LinearLayout ly_fanhui;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_zhanghaoanquan);
		xianshizhanghao = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.xianshizhanghao);
		xianshixingming = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.xianshixingming);
		xianshishoujihao = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.xianshishoujihao);
		anquanzhongxin = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.anquanzhongxin);
		ceshirukou = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.ceshirukou);
		ly_fanhui = (LinearLayout) findViewById(R.id.ly_fanhui_zhanghaoanquan);
		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		Intent intent = getIntent();
		if (!TextUtils.isEmpty(intent.getStringExtra("data3"))
				&& intent.getStringExtra("data3").length() > 6) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < intent.getStringExtra("data3").length(); i++) {
				char c = intent.getStringExtra("data3").charAt(i);
				if (i >= 3 && i <= 13) {
					sb.append('*');
				} else {
					sb.append(c);
				}
			}

			xianshizhanghao.setText(sb.toString());
		}
		// xianshizhanghao.setText(intent.getStringExtra("data3"));

		xianshixingming.setText(intent.getStringExtra("data1"));
		xianshishoujihao.setText(intent.getStringExtra("data2"));
		ceshirukou.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// DisplayToast("稍后开通，敬请关注！");
				Intent intent = new Intent();
				intent.setClass(ZhangHaoAnQuanActivity.this,
						CertificateRest.class);
				// intent.setClass(ZhangHaoAnQuan.this, TuiJian.class);
				startActivity(intent);

			}
		});
		anquanzhongxin.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast("稍后开通，敬请关注！");
				Intent intent = new Intent();
				intent.setClass(ZhangHaoAnQuanActivity.this,
						MinXingAnQuanZhongXinActivity.class);
				// intent.setClass(ZhangHaoAnQuan.this, TuiJian.class);
				startActivity(intent);

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
			// intent.setClass(ZhangHaoAnQuan.this, SZ_SheZhi.class);
			// startActivity(intent);
			ZhangHaoAnQuanActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
