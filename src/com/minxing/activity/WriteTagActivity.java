package com.minxing.activity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.kobjects.base64.Base64;

import com.minxing.util.ImageSelect;
import com.minxing.util.ImageSelect.OnImageSelectClickListener;
import com.zhumingmin.vmsofminxing.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 写入标签
 * 
 * @warn：弹出dialog 允许写入
 * @author shenyang
 * 
 */
@SuppressLint("NewApi")
public class WriteTagActivity extends Activity implements OnClickListener {
	private ImageSelect imageSelect;
	private IntentFilter[] mWriteTagFilters;
	private NfcAdapter nfcAdapter;
	PendingIntent pendingIntent;
	String[][] mTechLists;
	TextView writeBtn;
	boolean isWrite = false;
	private Button bbtn_cancel;
	EditText mContentEditText;
	private Dialog dialog = null;
	private Handler finishHand;
	private Uri uri;
	private ArrayList<Bitmap> images;// 上传的图片

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == ImageSelect.PHOTO_REQUEST_GALLERY) {
			// 从相册返回的数据
			if (data != null) {
				// 得到图片的全路径

				uri = data.getData();

				imageSelect.crop(uri);

			}

		} else if (requestCode == ImageSelect.PHOTO_REQUEST_CAREMA) {
			// 从相机返回的数据
			// if (hasSdcard()) {
			imageSelect.crop(Uri.fromFile(imageSelect.tempFile));
			// } else {
			// Toast.makeText(MyActivity01.this, "未找到存储卡，无法存储照片！", 0).show();
			// }

		} else if (requestCode == ImageSelect.PHOTO_REQUEST_CUT) {
			// 从剪切图片返回的数据
			if (data != null) {
				Bitmap bitmap = data.getParcelableExtra("data");

				imageSelect.addImageByBitmap(bitmap);

			}

			try {
				// 将临时文件删除
				if (imageSelect.tempFile != null) {
					imageSelect.tempFile.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_write_tag);
		initImageSelect();
		writeBtn = (TextView) findViewById(com.zhumingmin.vmsofminxing.R.id.xieru);
		writeBtn.setOnClickListener(this);
		mContentEditText = (EditText) findViewById(com.zhumingmin.vmsofminxing.R.id.content_edit);
		bbtn_cancel = (Button) findViewById(com.zhumingmin.vmsofminxing.R.id.btn_cancel);
		bbtn_cancel.setOnClickListener(this);
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
		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		// 写入标签权限
		IntentFilter writeFilter = new IntentFilter(
				NfcAdapter.ACTION_TECH_DISCOVERED);
		mWriteTagFilters = new IntentFilter[] { writeFilter };
		mTechLists = new String[][] {
				new String[] { MifareClassic.class.getName() },
				new String[] { NfcA.class.getName() } };// 允许扫描的标签类型

		finishHand = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0) {
					dialog.dismiss();
				}
			}
		};
	}

	/**
	 * 写入标签按钮点击事件监听
	 * 
	 * @author shenyang
	 * 
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == com.zhumingmin.vmsofminxing.R.id.btn_cancel) {
			finish();
			// overridePendingTransition(
			// com.zhumingmin.vmsofminxing.R.anim.activity_right_out,
			// com.zhumingmin.vmsofminxing.R.anim.activity_left_in);
		} else {
			isWrite = true;
			if (mContentEditText.getText().toString().isEmpty()) {
				showToast("您得先输入需要写入的数据！");
				return;
			}
			dialog = new AlertDialog.Builder(this)
					.setMessage("请您将需要写入数据的标签贴靠在手机背面！")
					.setPositiveButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
									isWrite = false;
								}
							}).create();
			dialog.show();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		nfcAdapter.enableForegroundDispatch(this, pendingIntent,
				mWriteTagFilters, mTechLists);
	}

	// 写入模式时，才执行写入操作
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		if (isWrite == true
				&& NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			NdefMessage ndefMessage = getNoteAsNdef();
			if (ndefMessage != null) {
				writeTag(getNoteAsNdef(), tag);
			} else {
				showToast("请输入您要写入标签的内容");
			}
		}
	}

	// 根据文本生成一个NdefRecord
	private NdefMessage getNoteAsNdef() {
		images = imageSelect.getImages();
		String[] pojo = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, pojo, null, null, null);

		int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
		cursor.moveToFirst();
		cursor.close();
		String text = mContentEditText.getText().toString();
		String imagepath = imageToString(cursor.getString(columnIndex));
		String resulttext = imagepath;
		if (text.equals("")) {
			return null;
		} else {
			byte[] textBytes = text.getBytes();
			byte[] imageBytes = imagepath.getBytes();
			// image/jpeg text/plain
			NdefRecord textRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
					"image/jpeg".getBytes(), new byte[] {}, textBytes);
			return new NdefMessage(new NdefRecord[] { textRecord });
		}

	}

	// 写入tag
	boolean writeTag(NdefMessage message, Tag tag) {

		int size = message.toByteArray().length;

		try {
			Ndef ndef = Ndef.get(tag);
			if (ndef != null) {
				ndef.connect();

				if (!ndef.isWritable()) {
					showToast("tag不允许写入");
					return false;
				}
				if (ndef.getMaxSize() < size) {
					showToast("文件大小超出容量");
					return false;
				}

				ndef.writeNdefMessage(message);
				Message msg = new Message();
				finishHand.sendEmptyMessage(0);
				showToast("写入数据成功.");

				return true;
			} else {
				NdefFormatable format = NdefFormatable.get(tag);
				if (format != null) {
					try {
						format.connect();
						format.format(message);
						showToast("格式化tag并且写入message");
						return true;
					} catch (IOException e) {
						showToast("格式化tag失败.");
						return false;
					}
				} else {
					showToast("Tag不支持NDEF");
					return false;
				}
			}
		} catch (Exception e) {
			showToast("写入数据失败");
		}

		return false;
	}

	private void showToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	private void initImageSelect() {
		imageSelect = (ImageSelect) findViewById(R.id.imageSelect);
		imageSelect
				.setOnImageSelectClickListener(new OnImageSelectClickListener() {
					@Override
					public void onClick(int id) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "" + id, 0)
								.show();
					}
				});
	}

	/**
	 * 利用BASE64Encoder对图片进行base64转码将图片转为string
	 * 
	 * @param imgFile
	 *            文件路径
	 * @return 返回编码后的string
	 */
	public static String imageToString(String imgFile) {
		// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 返回Base64编码过的字节数组字符串
		String str = new String(Base64.encode(data));
		return str;
	}
}
