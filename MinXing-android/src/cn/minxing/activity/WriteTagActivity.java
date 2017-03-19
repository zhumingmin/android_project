package cn.minxing.activity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import org.kobjects.base64.Base64;

import cn.minxing.restwebservice.WangJiMiMaService;
import cn.minxing.util.CustomArrayAdapter;
import cn.minxing.util.ImageSelect;
import cn.minxing.util.ImageSelect.OnImageSelectClickListener;

import com.zhumingmin.vmsofminxing.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
	EditText mContentEditText, et_lianxidianhua, et_chuchandi,
			et_chanpinshuliang, et_dangqianshijian;
	private Dialog dialog = null;
	private Handler finishHand;
	private Uri uri;
	private ArrayList<Bitmap> images;// 上传的图片
	private static final String SERVICE_URL = "http://192.168.191.1:8080/RestWebServiceDemo/rest/nfctag";
	private static final String TAG = "WriteTagActivity";
	private LinearLayout ly_fanhui;
	private Spinner sp_chanpinzhonglei;
	private CustomArrayAdapter<CharSequence> mAdapter;
	String chanpinzhonglei;

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	//
	// if (requestCode == ImageSelect.PHOTO_REQUEST_GALLERY) {
	// // 从相册返回的数据
	// if (data != null) {
	// // 得到图片的全路径
	//
	// uri = data.getData();
	//
	// imageSelect.crop(uri);
	//
	// }
	//
	// } else if (requestCode == ImageSelect.PHOTO_REQUEST_CAREMA) {
	// // 从相机返回的数据
	// // if (hasSdcard()) {
	// imageSelect.crop(Uri.fromFile(imageSelect.tempFile));
	// // } else {
	// // Toast.makeText(MyActivity01.this, "未找到存储卡，无法存储照片！", 0).show();
	// // }
	//
	// } else if (requestCode == ImageSelect.PHOTO_REQUEST_CUT) {
	// // 从剪切图片返回的数据
	// if (data != null) {
	// Bitmap bitmap = data.getParcelableExtra("data");
	//
	// imageSelect.addImageByBitmap(bitmap);
	//
	// }
	//
	// try {
	// // 将临时文件删除
	// if (imageSelect.tempFile != null) {
	// imageSelect.tempFile.delete();
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// super.onActivityResult(requestCode, resultCode, data);
	// }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_write_tag);
		// initImageSelect();
		ly_fanhui = (LinearLayout) findViewById(R.id.ly_fanhui_writetag);
		writeBtn = (TextView) findViewById(R.id.xieru);
		mContentEditText = (EditText) findViewById(R.id.content_edit);
		et_lianxidianhua = (EditText) findViewById(R.id.et_lianxidianhua);
		et_chanpinshuliang = (EditText) findViewById(R.id.et_chanpinshuliang);
		et_chuchandi = (EditText) findViewById(R.id.et_chuchandi);
		et_dangqianshijian = (EditText) findViewById(R.id.et_dangqianshijian);
		bbtn_cancel = (Button) findViewById(R.id.btn_cancel);
		sp_chanpinzhonglei = (Spinner) findViewById(R.id.sp_chanpinzhonglei);

		bbtn_cancel.setOnClickListener(this);
		writeBtn.setOnClickListener(this);

		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!et_chanpinshuliang.getText().toString().equals("")
						&& !et_chuchandi.getText().toString().equals("")
						&& !et_lianxidianhua.getText().toString().equals("")) {
					WebServiceTask wst = new WebServiceTask(
							WebServiceTask.POST_TASK, WriteTagActivity.this,
							"上传中...");

					wst.addNameValuePair("nfctag", mContentEditText.getText()
							.toString());

					wst.execute(new String[] { SERVICE_URL });
					finish();
				} else {
					finish();

				}
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
		String[] kexuanchanpinzhonglei = getResources().getStringArray(
				R.array.item1);
		this.mAdapter = new CustomArrayAdapter<CharSequence>(this,
				kexuanchanpinzhonglei);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item,
				kexuanchanpinzhonglei);
		sp_chanpinzhonglei.setAdapter(adapter);
		sp_chanpinzhonglei
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						arg0.setVisibility(View.VISIBLE);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}

				});

		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日 HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		et_dangqianshijian.setText(str);

		// et_lianxidianhua.setOnKeyListener(new EditText.OnKeyListener() {
		// @Override
		// public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
		// // TODO Auto-generated method stub
		// TextView tx_spinner = (TextView) sp_chanpinzhonglei
		// .getSelectedView();
		// chanpinzhonglei = tx_spinner.getText().toString();
		//
		// if (et_chanpinshuliang.getText().toString() != null
		// && et_lianxidianhua.getText().toString() != null) {
		//
		// mContentEditText
		// .setText("产品种类：" + chanpinzhonglei + "\n" + "产品数量："
		// + et_chanpinshuliang.getText().toString()
		// + "\n" + "联系方式："
		// + et_lianxidianhua.getText().toString()
		// + "\n" + "当前时间："
		// + et_dangqianshijian.getText().toString());
		// }
		//
		// return false;
		// }
		// });

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
			TextView tx_spinner = (TextView) sp_chanpinzhonglei
					.getSelectedView();
			chanpinzhonglei = tx_spinner.getText().toString();

			if (et_chanpinshuliang.getText().toString() != null
					&& et_lianxidianhua.getText().toString() != null) {
				new Thread() {
					public void run() {
						// 这儿是耗时操作，完成之后更新UI；
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// 更新UI
								mContentEditText.setText("产品种类："
										+ chanpinzhonglei
										+ "\n"
										+ "产品数量："
										+ et_chanpinshuliang.getText()
												.toString()
										+ "\n"
										+ "出产地："
										+ et_chuchandi.getText().toString()
										+ "\n"
										+ "联系方式："
										+ et_lianxidianhua.getText().toString()
										+ "\n"
										+ "当前时间："
										+ et_dangqianshijian.getText()
												.toString());
							}

						});
					}
				}.start();

			}

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
		// images = imageSelect.getImages();
		// String[] pojo = { MediaStore.Images.Media.DATA };
		// Cursor cursor = managedQuery(uri, pojo, null, null, null);
		//
		// int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
		// cursor.moveToFirst();
		// cursor.close();
		String text = mContentEditText.getText().toString();

		// String imagepath = imageToString(cursor.getString(columnIndex));
		// String resulttext = imagepath;
		if (text.equals("")) {
			return null;
		} else {

			byte[] textBytes = text.getBytes();
			// byte[] imageBytes = imagepath.getBytes();
			// image/jpeg text/plain
			NdefRecord textRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
					"text/plain".getBytes(), new byte[] {}, textBytes);

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

	public void handleResponse(String response) {

		try {

			JSONObject jso = new JSONObject(response);

		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}

	}

	// 隐藏键盘
	private void hideKeyboard() {

		InputMethodManager inputManager = (InputMethodManager) WriteTagActivity.this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow(WriteTagActivity.this
				.getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	// 主要操作部分
	private class WebServiceTask extends AsyncTask<String, Integer, String> {

		public static final int POST_TASK = 1;
		public static final int GET_TASK = 2;

		private static final String TAG = "WebServiceTask";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 5000;

		private int taskType = GET_TASK;
		private Context mContext = null;
		private String processMessage = "Processing...";

		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		private ProgressDialog pDlg = null;

		public WebServiceTask(int taskType, Context mContext,
				String processMessage) {

			this.taskType = taskType;
			this.mContext = mContext;
			this.processMessage = processMessage;
		}

		public void addNameValuePair(String name, String value) {

			params.add(new BasicNameValuePair(name, value));
		}

		@SuppressWarnings("deprecation")
		private void showProgressDialog() {

			pDlg = new ProgressDialog(mContext);
			pDlg.setMessage(processMessage);
			pDlg.setProgressDrawable(mContext.getWallpaper());
			pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDlg.setCancelable(false);
			pDlg.show();

		}

		@Override
		protected void onPreExecute() {

			hideKeyboard();
			showProgressDialog();

		}

		protected String doInBackground(String... urls) {

			String url = urls[0];
			String result = "";

			HttpResponse response = doResponse(url);

			if (response == null) {
				return result;
			} else {

				try {

					result = inputStreamToString(response.getEntity()
							.getContent());

				} catch (IllegalStateException e) {
					Log.e(TAG, e.getLocalizedMessage(), e);

				} catch (IOException e) {
					Log.e(TAG, e.getLocalizedMessage(), e);
				}

			}

			return result;
		}

		@Override
		protected void onPostExecute(String response) {

			handleResponse(response);
			if (response != null) {
				Toast.makeText(getApplicationContext(), "查询成功！", 0).show();

			} else {
				Toast.makeText(getApplicationContext(), "查询失败！", 0).show();
			}
			pDlg.dismiss();
			// System.out.println("输出"+response);

		}

		// Establish connection and socket (data retrieval) timeouts
		private HttpParams getHttpParams() {

			HttpParams htpp = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
			HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

			return htpp;
		}

		private HttpResponse doResponse(String url) {

			// Use our connection and data timeouts as parameters for our
			// DefaultHttpClient
			HttpClient httpclient = new DefaultHttpClient(getHttpParams());

			HttpResponse response = null;

			try {
				switch (taskType) {

				case POST_TASK:
					HttpPost httppost = new HttpPost(url);
					// Add parameters
					httppost.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					response = httpclient.execute(httppost);
					break;
				case GET_TASK:
					HttpGet httpget = new HttpGet(url);
					response = httpclient.execute(httpget);
					break;
				}
			} catch (Exception e) {

				Log.e(TAG, e.getLocalizedMessage(), e);

			}

			return response;
		}

		private String inputStreamToString(InputStream is) {

			String line = "";
			StringBuilder total = new StringBuilder();

			// Wrap a BufferedReader around the InputStream
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			try {
				// Read response until the end
				while ((line = rd.readLine()) != null) {
					total.append(line);
				}
			} catch (IOException e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
			}

			// Return full string
			return total.toString();
		}

	}

	// private void initImageSelect() {
	// imageSelect = (ImageSelect) findViewById(R.id.imageSelect);
	// imageSelect
	// .setOnImageSelectClickListener(new OnImageSelectClickListener() {
	// @Override
	// public void onClick(int id) {
	// // TODO Auto-generated method stub
	// Toast.makeText(getApplicationContext(), "" + id, 0)
	// .show();
	// }
	// });
	// }
	//
	// /**
	// * 利用BASE64Encoder对图片进行base64转码将图片转为string
	// *
	// * @param imgFile
	// * 文件路径
	// * @return 返回编码后的string
	// */
	// public static String imageToString(String imgFile) {
	// // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	// InputStream in = null;
	// byte[] data = null;
	// // 读取图片字节数组
	// try {
	// in = new FileInputStream(imgFile);
	// data = new byte[in.available()];
	// in.read(data);
	// in.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// // 返回Base64编码过的字节数组字符串
	// String str = new String(Base64.encode(data));
	// return str;
	// }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			if (!et_chanpinshuliang.getText().toString().equals("")
					&& !et_chuchandi.getText().toString().equals("")
					&& !et_lianxidianhua.getText().toString().equals("")) {
				WebServiceTask wst = new WebServiceTask(
						WebServiceTask.POST_TASK, WriteTagActivity.this,
						"上传中...");

				wst.addNameValuePair("nfctag", mContentEditText.getText()
						.toString());

				wst.execute(new String[] { SERVICE_URL });
				finish();
			} else {
				finish();

			}

		}
		return super.onKeyDown(keyCode, event);
	}
}
