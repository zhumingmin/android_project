package cn.minxing.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

import com.idcard.CardInfo;
import com.idcard.TRECAPIImpl;
import com.idcard.TStatus;
import com.idcard.TengineID;
import com.ui.card.TRCardScan;
import com.zhumingmin.vmsofminxing.R;

import cn.minxing.restwebservice.BaoJianService;
import cn.minxing.restwebservice.ChaXunService;
import cn.minxing.restwebservice.HuJiService;
import cn.minxing.restwebservice.LoginService;
import cn.minxing.util.EncodingHandler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ShenFenRenZhengActivity extends Activity {
	private static final String SERVICE_URL = "http://192.168.191.1:8080/RestWebServiceDemo/rest/shenfen";
	private static final String TAG = "ShenFenRenZhengActivity";
	public int RESULT_GET_OK = 1;
	public int RESULT_GET_CARD_OK = 2;
	private static final String[] m_Countries = { "身份证" }; // 定义数组
	private ArrayAdapter adapter; // 存放数据
	private Spinner spinnerCardNumber; // 下拉框
	private TengineID tengineID = TengineID.TUNCERTAIN;
	private TRECAPIImpl engineDemo = new TRECAPIImpl();
	private Button shenfen_shangchuan, btcard;
	private TextView textView;
	static String account;

	public String getImageFromSDcard(String fileName) {
		String SDCARD_ROOT_PATH = Environment.getExternalStorageDirectory()
				.getPath();
		String imgpath = SDCARD_ROOT_PATH + "/" + fileName;
		return imgpath;
	}

	private LinearLayout ly_fanhui;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.zhumingmin.vmsofminxing.R.layout.activity_shenfenshibie);

		btcard = (Button) findViewById(R.id.BtIDCARD);
		textView = (TextView) findViewById(R.id.textViewResult);
		ly_fanhui = (LinearLayout) findViewById(R.id.ly_fanhui_shenfenrenzheng);
		shenfen_shangchuan = (Button) findViewById(R.id.shenfen_shangchuan);

		ly_fanhui.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		TStatus tStatus = engineDemo.TR_StartUP();
		if (tStatus == TStatus.TR_TIME_OUT) {
			Toast.makeText(getBaseContext(), "引擎过期", Toast.LENGTH_SHORT).show();
		} else if (tStatus == TStatus.TR_FAIL) {
			Toast.makeText(getBaseContext(), "引擎初始化失败", Toast.LENGTH_SHORT)
					.show();
		}

		textView.setText(engineDemo.TR_GetCopyrightInfo() + "\n"
				+ engineDemo.TR_GetVersion() + "\n"
				+ engineDemo.TR_GetUseTimeString());

		/* 以下是获取引擎类型的选择 */
		spinnerCardNumber = (Spinner) findViewById(R.id.SpinnerEngineType);
		// 将可选内容与ArrayAdapter连接，
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, m_Countries);
		// 将adapter添加到m_Spinner中
		spinnerCardNumber.setAdapter(adapter);// 到这里，就完成了下拉框的绑定数据，下拉框中已经有我们想要选择的值了。下面获取选择的值。

		// 添加Spinner事件监听
		spinnerCardNumber
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						String cardNumber = m_Countries[arg2];
						// 设置显示当前选择的项
						arg0.setVisibility(View.VISIBLE);

						if (cardNumber.equals("身份证")) {
							TRCardScan.SetEngineType(TengineID.TIDCARD2);
							tengineID = TengineID.TIDCARD2;
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}
				});
		btcard.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 打开证件识别界面
				TRCardScan.isOpenProgress = true;
				TRCardScan.ShowCopyRightTxt = "由图睿信息提供技术";
				Intent intent = new Intent(ShenFenRenZhengActivity.this,
						TRCardScan.class);
				intent.putExtra("engine", engineDemo);
				startActivityForResult(intent, RESULT_GET_CARD_OK);

			}
		});
		LoginService ls = new LoginService();
		account = ls.account();
		shenfen_shangchuan.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String shenfenxinxi = "该用户的账号为："+account+"\n该用户上传的身份信息为："+"\n"+textView.getText().toString();

				if (!isNetworkAvailable(ShenFenRenZhengActivity.this)) {

					Toast.makeText(getApplicationContext(), "网络未连接，请检查您的网络！", 0)
							.show();
					return;
				}
				if (shenfenxinxi.equals("")) {
					Toast.makeText(getApplicationContext(), "身份信息不能为空！", 0)
							.show();
					return;
				}

				WebServiceTask wst = new WebServiceTask(
						WebServiceTask.POST_TASK, ShenFenRenZhengActivity.this,
						"提交中…");

				wst.addNameValuePair("shenfenxinxi", shenfenxinxi);

				// the passed String is the URL we will POST to
				wst.execute(new String[] { SERVICE_URL });
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Bundle bundle = data.getExtras();
		if (requestCode == RESULT_GET_CARD_OK) {
			// 身份证正面时， 能获取到人头像图片
			// 获取人头像
			Bitmap headimg = TRCardScan.HeadImgBitmap;// 人头像
			Bitmap Takeimg = TRCardScan.TakeBitmap; // 全图
			ImageView HeadImageView = (ImageView) findViewById(R.id.imageViewHead);
			HeadImageView.setImageBitmap(headimg);
			// 处理身份证识别信息（在界面上显示）
			CardInfo cardInfo = (CardInfo) data
					.getSerializableExtra("cardinfo");
			// 获取单个栏目识别信息
			// textView.setText(cardInfo.getFieldString(TFieldID.NUM));//以此类推

			// 获取全部识别信息， 为了演示， 这里采用展示所有信息

			textView.setText(cardInfo.getAllinfo());
		}
	}

	@Override
	protected void onDestroy() {
		engineDemo.TR_ClearUP();
		super.onDestroy();
	}

	public void handleResponse(String response) {

		try {

			JSONObject jso = new JSONObject(response);

		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}

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
				Toast.makeText(getApplicationContext(), "上传成功！", 0).show();

			} else {
				Toast.makeText(getApplicationContext(), "上传失败！", 0).show();
			}
			pDlg.dismiss();

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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			ShenFenRenZhengActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 检测当的网络（WLAN、3G/2G）状态
	 * 
	 * @param context
	 *            Context
	 * @return true 表示网络可用
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info != null && info.isConnected()) {
				// 当前网络是连接的
				if (info.getState() == NetworkInfo.State.CONNECTED) {
					// 当前所连接的网络可用
					return true;
				}
			}
		}
		return false;
	}

	protected void DisplayToast(String string) {

		// TODO Auto-generated method stub
		Toast.makeText(ShenFenRenZhengActivity.this, string, Toast.LENGTH_SHORT)
				.show();
	}
}
