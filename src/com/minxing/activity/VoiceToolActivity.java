package com.minxing.activity;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;

import com.minxing.util.VoiceToWord;
import com.zhumingmin.vmsofminxing.R;

public class VoiceToolActivity extends Activity implements OnClickListener {
	Button but = null;
	Button dubiaoqian, xiebiaoqian, but2, soufanshiliu, soufenjiao, soumugua,
			souqita;
	ImageButton yuyinsousuo;
	public SearchView sv;

	// EditText xianshiwenben;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_voicetool);
		// but = (Button)
		// findViewById(com.zhumingmin.vmsofminxing.R.id.YuYinZhuShou);
		// but2 = (Button)
		// findViewById(com.zhumingmin.vmsofminxing.R.id.YuYinZhuShou2);
		soufanshiliu = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.soufanshiliu);
		soufenjiao = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.soufenjiao);
		soumugua = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.soumugua);
		souqita = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.souqita);
		// dubiaoqian = (Button)
		// findViewById(com.zhumingmin.vmsofminxing.R.id.DuBiaoQian);
		yuyinsousuo = (ImageButton) findViewById(com.zhumingmin.vmsofminxing.R.id.yuyinsousuo);
		sv = (SearchView) this.findViewById(R.id.sv);

		sv.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextChange(String str) {
				return false;
			}

			@Override
			public boolean onQueryTextSubmit(String str) {
				String keyword = str;
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://www.baidu.com/s?&wd="
						+ keyword));
				startActivity(intent);
				return false;
			}

		});
		// AssetManager mgr = getAssets();// 得到AssetManager
		// Typeface tf = Typeface.createFromAsset(mgr, "fonts/FZLTCXHJW.TTF");//
		// 根据路径得到Typeface
		// but.setTypeface(tf);
		// but2.setTypeface(tf);
		// dubiaoqian.setTypeface(tf);
		// soufanshiliu.setTypeface(tf);
		// soufenjiao.setTypeface(tf);
		// soumugua.setTypeface(tf);
		// souqita.setTypeface(tf);
		// xiebiaoqian = (Button)
		// findViewById(com.zhumingmin.vmsofminxing.R.id.XieBiaoQian);
		// xianshiwenben = (EditText)
		// findViewById(com.zhumingmin.vmsofminxing.R.id.XianShiWenBen);
		// xianshiwenben.setText(VoiceToWord.mToast);

		soufanshiliu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(VoiceToolActivity.this, SouFanShiLiuActivity.class);
				startActivity(intent);
				VoiceToolActivity.this.finish();
			}

		});
		soufenjiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(VoiceToolActivity.this, SouFenJiaoActivity.class);
				startActivity(intent);
				VoiceToolActivity.this.finish();
			}

		});
		soumugua.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(VoiceToolActivity.this, SouMuGuaActivity.class);
				startActivity(intent);
				VoiceToolActivity.this.finish();
			}

		});
		souqita.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(VoiceToolActivity.this, SouQiTaActivity.class);
				startActivity(intent);
				VoiceToolActivity.this.finish();
			}

		});

		// but.setOnClickListener(this);
		yuyinsousuo.setOnClickListener(this);
		/*
		 * but2.setOnClickListener(new Button.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent intent = new Intent(); intent.setClass(VoiceTool.this,
		 * YuYinZhuShou.class); startActivity(intent); VoiceTool.this.finish();
		 * } });
		 */
		// yuyinsousuo.setOnClickListener(new Button.OnClickListener() {

		// @Override
		// public void onClick(View v) {
		// TODO Auto-generated method stub
		// Intent intent = new Intent();
		// intent.setClass(VoiceTool.this, YuYinZhuShou.class);
		// startActivity(intent);
		// VoiceTool.this.finish();
		// }
		// });
		// dubiaoqian.setOnClickListener(new Button.OnClickListener() {

		// @Override
		// public void onClick(View v) {
		// TODO Auto-generated method stub
		// Intent intent = new Intent();
		// intent.setClass(VoiceTool.this, ReadTag.class);
		// startActivity(intent);
		// overridePendingTransition(
		// com.zhumingmin.vmsofminxing.R.anim.activity_right_out,
		// com.zhumingmin.vmsofminxing.R.anim.activity_left_in);
		// VoiceTool.this.finish();
		// }
		// });
		/*
		 * xiebiaoqian.setOnClickListener(new Button.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent intent = new Intent(); intent.setClass(VoiceTool.this,
		 * WriteTag.class); startActivity(intent); VoiceTool.this.finish(); }
		 * });
		 */
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater()
	// .inflate(com.zhumingmin.vmsofminxing.R.menu.menu, menu);
	// return true;
	// }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case com.zhumingmin.vmsofminxing.R.id.yuyinsousuo:
			VoiceToWord voice = new VoiceToWord(VoiceToolActivity.this, "534e3fe2");
			voice.GetWordFromVoice();
			String text = voice.getSearchText();
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {
			// Intent intent = new Intent();
			// intent.setClass(VoiceTool.this, CaiDanLan.class);
			// startActivity(intent);
			VoiceToolActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
