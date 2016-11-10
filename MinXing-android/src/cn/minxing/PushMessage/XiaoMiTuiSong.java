package cn.minxing.PushMessage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.minxing.PushMessage.TimeIntervalDialog.TimeIntervalInterface;

import com.xiaomi.mipush.sdk.MiPushClient;
import com.zhumingmin.vmsofminxing.R;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 1、本 demo 可以直接运行，设置 topic 和 alias。 服务器端使用 appsecret 即可以向demo发送广播和单点的消息。<br/>
 * 2、为了修改本 demo 为使用你自己的 appid，你需要修改几个地方：DemoApplication.java 中的 APP_ID,
 * APP_KEY，AndroidManifest.xml 中的 packagename，和权限 permission.MIPUSH_RECEIVE
 * 的前缀为你的 packagename。
 * 
 * @author wangkuiwei
 */
public class XiaoMiTuiSong extends Activity {

	public static List<String> logList = new CopyOnWriteArrayList<String>();

	private TextView mLogView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pushmessage_xiaomituisong);
		DemoApplication.setMainActivity(this);

		mLogView = (TextView) findViewById(R.id.log);
		// 设置别名
		findViewById(R.id.set_alias).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final EditText editText = new EditText(XiaoMiTuiSong.this);
				new AlertDialog.Builder(XiaoMiTuiSong.this)
						.setTitle(R.string.set_alias)
						.setView(editText)
						.setPositiveButton(R.string.ok,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										String alias = editText.getText()
												.toString();
										MiPushClient
												.setAlias(XiaoMiTuiSong.this,
														alias, null);
									}

								}).setNegativeButton(R.string.cancel, null)
						.show();
			}
		});
		// 撤销别名
		findViewById(R.id.unset_alias).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						final EditText editText = new EditText(
								XiaoMiTuiSong.this);
						new AlertDialog.Builder(XiaoMiTuiSong.this)
								.setTitle(R.string.unset_alias)
								.setView(editText)
								.setPositiveButton(R.string.ok,
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												String alias = editText
														.getText().toString();
												MiPushClient.unsetAlias(
														XiaoMiTuiSong.this,
														alias, null);
											}

										})
								.setNegativeButton(R.string.cancel, null)
								.show();

					}
				});
		// 设置帐号
		findViewById(R.id.set_account).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						final EditText editText = new EditText(
								XiaoMiTuiSong.this);
						new AlertDialog.Builder(XiaoMiTuiSong.this)
								.setTitle(R.string.set_account)
								.setView(editText)
								.setPositiveButton(R.string.ok,
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												String account = editText
														.getText().toString();
												MiPushClient.setUserAccount(
														XiaoMiTuiSong.this,
														account, null);
											}

										})
								.setNegativeButton(R.string.cancel, null)
								.show();

					}
				});
		// 撤销帐号
		findViewById(R.id.unset_account).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						final EditText editText = new EditText(
								XiaoMiTuiSong.this);
						new AlertDialog.Builder(XiaoMiTuiSong.this)
								.setTitle(R.string.unset_account)
								.setView(editText)
								.setPositiveButton(R.string.ok,
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												String account = editText
														.getText().toString();
												MiPushClient.unsetUserAccount(
														XiaoMiTuiSong.this,
														account, null);
											}

										})
								.setNegativeButton(R.string.cancel, null)
								.show();
					}
				});
		// 设置标签
		findViewById(R.id.subscribe_topic).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						final EditText editText = new EditText(
								XiaoMiTuiSong.this);
						new AlertDialog.Builder(XiaoMiTuiSong.this)
								.setTitle(R.string.subscribe_topic)
								.setView(editText)
								.setPositiveButton(R.string.ok,
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												String topic = editText
														.getText().toString();
												MiPushClient.subscribe(
														XiaoMiTuiSong.this,
														topic, null);
											}

										})
								.setNegativeButton(R.string.cancel, null)
								.show();
					}
				});
		// 撤销标签
		findViewById(R.id.unsubscribe_topic).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						final EditText editText = new EditText(
								XiaoMiTuiSong.this);
						new AlertDialog.Builder(XiaoMiTuiSong.this)
								.setTitle(R.string.unsubscribe_topic)
								.setView(editText)
								.setPositiveButton(R.string.ok,
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												String topic = editText
														.getText().toString();
												MiPushClient.unsubscribe(
														XiaoMiTuiSong.this,
														topic, null);
											}

										})
								.setNegativeButton(R.string.cancel, null)
								.show();
					}
				});
		// 设置接收消息时间
		findViewById(R.id.set_accept_time).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						new TimeIntervalDialog(XiaoMiTuiSong.this,
								new TimeIntervalInterface() {

									@Override
									public void apply(int startHour,
											int startMin, int endHour,
											int endMin) {
										MiPushClient
												.setAcceptTime(
														XiaoMiTuiSong.this,
														startHour, startMin,
														endHour, endMin, null);
									}

									@Override
									public void cancel() {
										// ignore
									}

								}).show();
					}
				});
		// 暂停推送
		findViewById(R.id.pause_push).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MiPushClient.pausePush(XiaoMiTuiSong.this, null);
			}
		});

		findViewById(R.id.resume_push).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						MiPushClient.resumePush(XiaoMiTuiSong.this, null);
					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshLogInfo();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		DemoApplication.setMainActivity(null);
	}

	public void refreshLogInfo() {
		String AllLog = "";
		for (String log : logList) {
			AllLog = AllLog + log + "\n\n";
		}
		mLogView.setText(AllLog);
	}
}
