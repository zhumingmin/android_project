package cn.minxing.activity;

import cn.minxing.restfulwebservice.CertificateRest;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ZhangHaoAnQuanActivity extends Activity {

	private Button zhanghaoanquan, bangzhu, guanyu, shezhi_zhanghaoanquan,
			anquanzhongxin, ceshirukou;
	private TextView xianshizhanghao, xianshixingming, xianshishoujihao;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_zhanghaoanquan);
		xianshizhanghao = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.xianshizhanghao);
		xianshixingming = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.xianshixingming);
		xianshishoujihao = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.xianshishoujihao);
		anquanzhongxin = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.anquanzhongxin);
		ceshirukou = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.ceshirukou);

		xianshizhanghao.setText("123009988433337657");
		xianshixingming.setText("张三");
		xianshishoujihao.setText("18826487090");
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
				ZhangHaoAnQuanActivity.this.finish();
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
				ZhangHaoAnQuanActivity.this.finish();
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
