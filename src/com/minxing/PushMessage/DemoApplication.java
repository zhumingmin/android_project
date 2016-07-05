package com.minxing.PushMessage;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.umeng.comm.core.location.Locateable;
import com.umeng.comm.core.sdkmanager.LocationSDKManager;
import com.umeng.community.location.DefaultLocationImpl;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.editorpage.location.DefaultLocationProvider;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

/**
 * 1、为了打开客户端的日志，便于在开发过程中调试，需要自定义一个 Application。 并将自定义的 application 注册在
 * AndroidManifest.xml 文件中。<br/>
 * 2、为了提高 push 的注册率，您可以在 Application 的 onCreate 中初始化 push。你也可以根据需要，在其他地方初始化
 * push。
 * 
 * @author wangkuiwei
 */
public class DemoApplication extends Application {

	// user your appid the key.
	private static final String APP_ID = "2882303761517479183";
	// user your appid the key.
	private static final String APP_KEY = "5651747962183";

	// 此TAG在adb logcat中检索自己所需要的信息， 只需在命令行终端输入 adb logcat | grep
	// com.xiaomi.mipushdemo
	public static final String TAG = "com.zhumingmin.vmsofminxing";

	private static DemoHandler sHandler = null;
	private static XiaoMiTuiSong sMainActivity = null;

	@Override
	public void onCreate() {

		// 确保不要重复注入同一类型的对象,建议在Application类的onCreate中执行该代码。
		LocationSDKManager.getInstance().addAndUse(new DefaultLocationImpl());

		PlatformConfig.setWeixin("wx967daebe835fbeac",
				"5bb696d9ccd75a38c8a0bfe0675559b3");
		// 微信 appid appsecret
		PlatformConfig.setSinaWeibo("3921700954",
				"04b48b094faeb16683c32669824ebdad");
		// 新浪微博 appkey appsecret
		PlatformConfig.setQQZone("100424468",
				"c7394704798a158208a74ab60104f0ba");
		// PlatformConfig.setWeixin("wx96110a1e3af63a39",
		// "c60e3d3ff109a5d17013df272df99199");
		// PlatformConfig.setSinaWeibo("275392174",
		// "d96fb6b323c60a42ed9f74bfab1b4f7a");
		// PlatformConfig.setQQZone("1104606393", "X4BAsJAVKtkDQ1zQ");
		super.onCreate();

		// 注册push服务，注册成功后会向DemoMessageReceiver发送广播
		// 可以从DemoMessageReceiver的onCommandResult方法中MiPushCommandMessage对象参数中获取注册信息
		if (shouldInit()) {
			MiPushClient.registerPush(this, APP_ID, APP_KEY);
		}

		LoggerInterface newLogger = new LoggerInterface() {

			@Override
			public void setTag(String tag) {
				// ignore
			}

			@Override
			public void log(String content, Throwable t) {
				Log.d(TAG, content, t);
			}

			@Override
			public void log(String content) {
				Log.d(TAG, content);
			}
		};
		Logger.setLogger(this, newLogger);
		if (sHandler == null) {
			sHandler = new DemoHandler(getApplicationContext());
		}
	}

	private boolean shouldInit() {
		ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
		List<RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
		String mainProcessName = getPackageName();
		int myPid = Process.myPid();
		for (RunningAppProcessInfo info : processInfos) {
			if (info.pid == myPid && mainProcessName.equals(info.processName)) {
				return true;
			}
		}
		return false;
	}

	public static DemoHandler getHandler() {
		return sHandler;
	}

	public static void setMainActivity(XiaoMiTuiSong activity) {
		sMainActivity = activity;
	}

	public static class DemoHandler extends Handler {

		private Context context;

		public DemoHandler(Context context) {
			this.context = context;
		}

		@Override
		public void handleMessage(Message msg) {
			String s = (String) msg.obj;
			if (sMainActivity != null) {
				sMainActivity.refreshLogInfo();
			}
			if (!TextUtils.isEmpty(s)) {
				Toast.makeText(context, s, Toast.LENGTH_LONG).show();
			}
		}
	}
}