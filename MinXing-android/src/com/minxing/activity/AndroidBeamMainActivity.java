package com.minxing.activity;

import java.nio.charset.Charset;
import java.util.Locale;

import com.minxing.activity.HuJiGuanLiActivity;
import com.minxing.activity.SZ_GRXXActivity;
import com.minxing.util.TextRecord;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 两部android手机，进行传入文本信息。
 * 
 * @author dr
 * 
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
@SuppressLint("NewApi")
public class AndroidBeamMainActivity extends Activity implements
		CreateNdefMessageCallback, OnNdefPushCompleteCallback {

	private EditText mBeamText;

	private NfcAdapter mNfcAdapter;
	private PendingIntent mPendingIntent;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_android_beam);

		mBeamText = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.edittext_beam_text);

		mNfcAdapter = mNfcAdapter.getDefaultAdapter(this);
		mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()), 0);

		mNfcAdapter.setNdefPushMessageCallback(this, this);
		mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
		Intent intent = getIntent();

		String text = intent.getStringExtra("GGXX");
		mBeamText.setText(text);
	}

	@Override
	/** 窗口处理完成 */
	public void onNdefPushComplete(NfcEvent event) {
		Log.d("message", "complete");
	}

	@Override
	/** 如果另外一台手机，靠近当前这部手机 */
	public NdefMessage createNdefMessage(NfcEvent event) {
		String text = mBeamText.getText().toString().trim();
		if ("".equals(text)) {
			text = "默认文本";
		}
		/*
		 * "com.android.calculator2" 官方原生计算器包。 当另外一部手机靠近这部手机时，会启动计算器。
		 * 
		 * NdefMessage ndefMessage = new NdefMessage( new NdefRecord[] {
		 * NdefRecord .createApplicationRecord("com.android.calculator2") });
		 */
		NdefMessage ndefMessage = new NdefMessage(
				new NdefRecord[] { createTextRecord(text) });

		return ndefMessage;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mNfcAdapter != null)
			mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null,
					null);
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mNfcAdapter != null)
			mNfcAdapter.disableForegroundDispatch(this);
	}

	@Override
	public void onNewIntent(Intent intent) {
		// 用于显示接收手机的信息。如果接收手机没有打开，会默认调用系统的黑界面。
		processIntent(intent);
	}

	/** 根据文本创建 NdefRecord 这个对象 */
	public NdefRecord createTextRecord(String text) {
		byte[] langBytes = Locale.CHINA.getLanguage().getBytes(
				Charset.forName("US-ASCII"));
		Charset utfEncoding = Charset.forName("UTF-8");
		byte[] textBytes = text.getBytes(utfEncoding);
		int utfBit = 0;
		char status = (char) (utfBit + langBytes.length);
		byte[] data = new byte[1 + langBytes.length + textBytes.length];
		data[0] = (byte) status;
		System.arraycopy(langBytes, 0, data, 1, langBytes.length);
		System.arraycopy(textBytes, 0, data, 1 + langBytes.length,
				textBytes.length);
		NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
				NdefRecord.RTD_TEXT, new byte[0], data);

		return record;
	}

	void processIntent(Intent intent) {
		Parcelable[] rawMsgs = intent
				.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

		NdefMessage msg = (NdefMessage) rawMsgs[0];
		String text = TextRecord.parse(msg.getRecords()[0]).getText();
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			Intent intent = new Intent();
			intent.setClass(AndroidBeamMainActivity.this, SZ_GRXXActivity.class);
			startActivity(intent);
			AndroidBeamMainActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}