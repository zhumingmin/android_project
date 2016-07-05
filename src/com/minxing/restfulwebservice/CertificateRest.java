package com.minxing.restfulwebservice;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.minxing.activity.ZhangHaoAnQuanActivity;
import com.zhumingmin.vmsofminxing.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;

@SuppressLint({ "SimpleDateFormat", "NewApi" })
public class CertificateRest extends Activity {
	private static final String getURL = "http://192.168.191.1:9000/certificates/123456/1";
	// private static final String postURL =
	// "http://192.168.191.1:9000/certificates/123456";
	private static final String postURL = "http://172.18.212.175:9000/certificates/123456";

	private EditText txResult, certificateId, type, staff, villagerId,
			attachment, status, checkDate, evaluation;
	private Button getmessage, postmessage;
	protected String Result;
	private DatePicker datePicker;

	/**
	 * Called when the activity is first created.
	 */
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// 在Android2.2以后必须添加以下代码
		// 本应用采用的Android4.0
		// 设置线程的策略
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		// 设置虚拟机的策略
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects()

				.penaltyLog().penaltyDeath().build());

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置UI布局
		setContentView(R.layout.certificate);

		// 获取结果显示文本框
		certificateId = (EditText) findViewById(R.id.certificateId);
		type = (EditText) findViewById(R.id.type);
		staff = (EditText) findViewById(R.id.staff);
		villagerId = (EditText) findViewById(R.id.villagerId);
		attachment = (EditText) findViewById(R.id.attachment);
		status = (EditText) findViewById(R.id.status);
		checkDate = (EditText) findViewById(R.id.checkDate);
		evaluation = (EditText) findViewById(R.id.evaluation);

		txResult = (EditText) findViewById(R.id.result);

		getmessage = (Button) findViewById(R.id.getmessage);
		postmessage = (Button) findViewById(R.id.postmessage);
		// datePicker = (DatePicker) findViewById(R.id.dpPicker);

		// datePicker.init(2013, 8, 20, new OnDateChangedListener() {
		//
		// @Override
		// public void onDateChanged(DatePicker view, int year,
		// int monthOfYear, int dayOfMonth) {
		// // 获取一个日历对象，并初始化为当前选中的时间
		// Calendar calendar = Calendar.getInstance();
		// calendar.set(year, monthOfYear, dayOfMonth);
		// SimpleDateFormat format = new SimpleDateFormat(
		// "yyyy年MM月dd日  HH:mm");
		// Toast.makeText(CertificateRest.this,
		// format.format(calendar.getTime()), Toast.LENGTH_SHORT)
		// .show();
		// }
		// });
		/*
		 * 从服务器GET数据
		 */
		getmessage.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setAccept(Collections
						.singletonList(new MediaType("application", "json")));

				HttpEntity<?> requestEntity = new HttpEntity<Object>(
						requestHeaders);

				RestTemplate restTemplate = new RestTemplate();

				restTemplate.getMessageConverters().add(
						new MappingJackson2HttpMessageConverter());
				restTemplate.getMessageConverters().add(
						new StringHttpMessageConverter());

				Certificate c = restTemplate.getForObject(getURL,
						Certificate.class);

				// Certificate[] events = responseEntity.getBody();
				txResult.setText(c.toString());
				// evaluation.setText(events.toString());

				certificateId.setText(c.getCertificateId().toString());
				type.setText(c.getType());
				staff.setText(c.getStaff());
				villagerId.setText(c.getVillagerId());
				attachment.setText(c.getAttachment());
				status.setText(c.getStaff());
				checkDate.setText(c.getCheckDate().toString());
				evaluation.setText(c.getEvaluation());
				/*
				 * 另一种通过GET方式获取JSON数据的方法
				 */
				// RestTemplate restTemplate = new RestTemplate();
				// restTemplate.getMessageConverters().add(new
				// MappingJackson2HttpMessageConverter());
				// Certificate[] events = restTemplate.getForObject(processURL,
				// Certificate[].class);
				// txResult.setText(events.toString());
			}
		});

		/*
		 * POST数据到服务器，先获取服务器端设置的token存到本地，然后请求的时候在http头文件中加上这个token
		 */
		postmessage.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {

				Certificate certificate = new Certificate();
				certificate.setAttachment("张三");
				// String to Timestamp
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String time = df.format(new Date());
				Timestamp ts = Timestamp.valueOf(time);
				// Timestamp to String
				SimpleDateFormat df2 = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");// 定义格式，不显示毫秒
				Timestamp now = new Timestamp(System.currentTimeMillis());// 获取系统当前时间
				String str = df2.format(now);

				// certificate.setCertificateId(certificateId.getText().);
				certificate.setCheckDate(str);
				certificate.setEvaluation("满意");
				certificate.setStaff("李四");
				certificate.setStatus("成功");
				certificate.setType("户口迁入");
				certificate.setVillagerId("1234567");

				// 设置 Content-Type
				// HttpHeaders requestHeaders = new HttpHeaders();
				// requestHeaders.setContentType(new MediaType("application",
				// "json"));
				// HttpEntity<Certificate> requestEntity = new
				// HttpEntity<Certificate>(
				// certificate, requestHeaders);
				// RestTemplate restTemplate = new RestTemplate();
				// restTemplate.getMessageConverters().add(
				// new MappingJackson2HttpMessageConverter());
				// restTemplate.getMessageConverters().add(
				// new StringHttpMessageConverter());
				// ResponseEntity<String> responseEntity =
				// restTemplate.exchange(
				// postURL, HttpMethod.POST, requestEntity, String.class);
				// String result = responseEntity.getBody();
				//
				// txResult.setText(result);
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(
						new MappingJackson2HttpMessageConverter());
				restTemplate.getMessageConverters().add(
						new StringHttpMessageConverter());
				String response = restTemplate.postForObject(postURL,
						certificate, String.class);
				txResult.setText(response);
			}
		});

	}

	// /**
	// * 获取服务器端的结果信息
	// */
	// public void getRESTWebService() {
	// try {
	// // 创建一个HttpClient对象
	// HttpClient httpclient = new DefaultHttpClient();
	// // 创建HttpGet对象
	// HttpGet request = new HttpGet(processURL);
	//
	// request.addHeader("application", "json");
	// // 获取响应的结果
	// HttpResponse response = httpclient.execute(request);
	// // 获取HttpEntity
	// HttpEntity entity = response.getEntity();
	// // 获取响应的结果信息
	// result = EntityUtils.toString(entity);
	// txResult.setText(result);
	// } catch (ClientProtocolException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			Intent intent = new Intent();
			intent.setClass(CertificateRest.this, ZhangHaoAnQuanActivity.class);
			startActivity(intent);
			CertificateRest.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void DisplayToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

}
