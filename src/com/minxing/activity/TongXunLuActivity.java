package com.minxing.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class TongXunLuActivity extends Activity {

	private String[] data = { "书记：18826487090", "村长：18826487090",
			"妇联主任：18826487090", "大陆/台湾地区报警求助：110", "香港报警：999", "医疗救护：120",
			"火警：119", "交通事故：122" };

	// private List<TongXunLu> tongxunlu = new ArrayList<TongXunLu>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_tongxunlu);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(TongXunLuActivity.this,
				android.R.layout.simple_list_item_1, data);
		ListView listview = (ListView) findViewById(com.zhumingmin.vmsofminxing.R.id.list_view);
		listview.setAdapter(adapter);
		listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri
						.parse("tel:" + get_StringNum(data[position])));
				startActivity(intent);
				return false;

			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			// Intent intent = new Intent();
			// intent.setClass(TongXunLu.this, SheZhi.class);
			// startActivity(intent);
			TongXunLuActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 获取listview中的数字
	 */
	public String get_StringNum(String str) {

		str = str.trim();
		String str2 = "";
		if (str != null && !"".equals(str)) {
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
					str2 += str.charAt(i);
				}
			}

		}

		return str2;

	}
}
