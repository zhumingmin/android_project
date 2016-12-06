package cn.minxing.activity;

import java.util.ArrayList;
import java.util.List;

import cn.minxing.restwebservice.MinYiZhengJiService;
import cn.minxing.rsystem.SerachListActivity;
import cn.minxing.util.RS_News;
import cn.minxing.util.TP_PiaoShu;
import cn.minxing.util.TP_PiaoShuAdapter;

import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TouPiaoJieGuoActivity extends Activity {
	private LinearLayout ly_fanhui;
	private TextView canxuanren, canxuanzhiwu, gerenshiji, piaoshu;
	public static List<TP_PiaoShu> piaoshulist = new ArrayList<TP_PiaoShu>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_toupiaojieguo);

		canxuanren = (TextView) findViewById(R.id.tp_canxuanren);
		canxuanzhiwu = (TextView) findViewById(R.id.tp_canxuanzhiwu);
		gerenshiji = (TextView) findViewById(R.id.tp_gerenshiji);
		piaoshu = (TextView) findViewById(R.id.tp_piaoshu);
		ly_fanhui = (LinearLayout) findViewById(R.id.ly_fanhui_toupiaojieguo);
		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TouPiaoJieGuoActivity.this,
						MinYiZhengJiService.class);
				startActivity(intent);
				finish();
			}
		});
		Intent intent = getIntent();
		String[] canxuanren = intent.getStringArrayExtra("canxuanren");
		String[] canxuanzhiwu = intent.getStringArrayExtra("canxuanzhiwu");
		String[] gerenshiji = intent.getStringArrayExtra("gerenshiji");
		String[] piaoshu = intent.getStringArrayExtra("piaoshu");
		for (int i = 0; i < canxuanren.length; i++) {

			TP_PiaoShu tp_piaoshu = new TP_PiaoShu("参选人：" + canxuanren[i],
					"参选职务：" + canxuanzhiwu[i], "个人事迹：" + gerenshiji[i], "票数："
							+ piaoshu[i]);
			piaoshulist.add(tp_piaoshu);
		}
		TP_PiaoShuAdapter adapter1 = new TP_PiaoShuAdapter(
				TouPiaoJieGuoActivity.this, R.layout.toupiao_list_item,
				piaoshulist);
		adapter1.notifyDataSetChanged();
		ListView listview = (ListView) findViewById(R.id.tp_list);
		listview.setAdapter(adapter1);

	}

	private void DisplayToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {
			Intent intent = new Intent(TouPiaoJieGuoActivity.this,
					MinYiZhengJiService.class);
			startActivity(intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
