package cn.minxing.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import com.sy.nfc.test.p;
import com.zhumingmin.vmsofminxing.R;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.os.Parcelable;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class ReadTagActivity extends Activity {
	private NfcAdapter nfcAdapter;
	private TextView resultText, textView3, textView2, textView1;
	private PendingIntent pendingIntent;
	private IntentFilter[] mFilters;
	private String[][] mTechLists;
	private Button mJumpTagBtn, btn_cancel, xieru;
	private boolean isFirst = true;
	private ImageView resultImage;
	private LinearLayout ly_fanhui;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_read_tag);
		ly_fanhui = (LinearLayout) findViewById(R.id.ly_fanhui_read_tag);
		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		// 获取nfc适配器，判断设备是否支持NFC功能
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (nfcAdapter == null) {
			Toast.makeText(
					this,
					getResources().getString(
							com.zhumingmin.vmsofminxing.R.string.no_nfc),
					Toast.LENGTH_SHORT).show();
		} else if (!nfcAdapter.isEnabled()) {
			Toast.makeText(
					this,
					getResources().getString(
							com.zhumingmin.vmsofminxing.R.string.open_nfc),
					Toast.LENGTH_SHORT).show();
		}

		resultImage = (ImageView) findViewById(com.zhumingmin.vmsofminxing.R.id.resultImage);

		WriteTagActivity wt = new WriteTagActivity();

		// resultImage.setImageBitmap(null);
		// 显示结果Text
		resultText = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.resultText);
		// textView3 = (TextView)
		// findViewById(com.zhumingmin.vmsofminxing.R.id.textView3);
		// textView2 = (TextView)
		// findViewById(com.zhumingmin.vmsofminxing.R.id.textView2);
		// textView1 = (TextView)
		// findViewById(com.zhumingmin.vmsofminxing.R.id.textView1);

		// 写入标签按钮
		mJumpTagBtn = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.jump);
		btn_cancel = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.btn_cancel);
		xieru = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.xieru);

		mJumpTagBtn.setOnClickListener(new WriteBtnOnClick());

		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
		ndef.addCategory("*/*");
		mFilters = new IntentFilter[] { ndef };// 过滤器
		mTechLists = new String[][] {
				new String[] { MifareClassic.class.getName() },
				new String[] { NfcA.class.getName() },
				new String[] { NfcB.class.getName() },
				new String[] { NfcF.class.getName() } };// 允许扫描的标签类型
		p.getInstance(this).setSilentTime(1);
		p.getInstance(this).isEnable(true);

		// mJumpTagBtn.setOnClickListener(new Button.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Intent intent = new Intent(ReadTag.this, WriteTag.class);
		// startActivity(intent);
		// overridePendingTransition(R.anim.activity_right_out,
		// R.anim.activity_left_in);
		// }
		// });

	}

	@SuppressLint("NewApi")
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		nfcAdapter.enableForegroundDispatch(this, pendingIntent, mFilters,
				mTechLists);
		if (isFirst) {
			if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent()
					.getAction())) {
				String result = processIntent(getIntent());
				resultText.setText(result);
			}
			isFirst = false;
		}

	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
			String result = processIntent(intent);
			resultText.setText(result);
		}
	}

	@SuppressLint("NewApi")
	private String processIntent(Intent intent) {
		Parcelable[] rawmsgs = intent
				.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
		if (rawmsgs == null) {
			return "该产品标签为空！";
		} else {
			NdefMessage msg = (NdefMessage) rawmsgs[0];// 空指针异常

			NdefRecord[] records = msg.getRecords();
			String resultStr = new String(records[0].getPayload());
			return "该产品标签为：\n" + resultStr;
		}

	}

	// @SuppressLint("NewApi")
	// private String processIntent(Intent intent) {
	// if (intent.getType() != null) {
	// Parcelable[] rawmsgs = intent
	// .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
	// NdefMessage msg = (NdefMessage) rawmsgs[0];// 空指针异常
	// NdefRecord[] records = msg.getRecords();
	// String resultStr = new String(records[0].getPayload());
	// return resultStr;
	// } else {
	// return "该标签为空！";
	// }
	//
	// }

	class WriteBtnOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.jump:
				Intent intent = new Intent(ReadTagActivity.this,
						WriteTagActivity.class);
				startActivity(intent);

			default:
				break;
			}
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {
			// Intent intent = new Intent();
			// intent.setClass(ReadTag.this, SheZhi.class);
			// startActivity(intent);
			ReadTagActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
