package cn.minxing.util;

import cn.minxing.activity.GetMessageActivity;
import cn.minxing.activity.MainActivity;

import com.zhumingmin.vmsofminxing.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class NotificationService extends Service {

	// 获取消息线程
	private MessageThread messageThread = null;

	// 点击查看
	private Intent messageIntent = null;
	private PendingIntent messagePendingIntent = null;

	// 通知栏消息
	private int messageNotificationID = 1000;
	private Notification messageNotification = null;
	private NotificationManager messageNotificatioManager = null;

	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// 初始化
		messageNotification = new Notification();
		messageNotification.icon = com.zhumingmin.vmsofminxing.R.drawable.tongzhi;
		messageNotification.tickerText = "民兴之家";
		messageNotification.defaults = Notification.DEFAULT_SOUND;
		messageNotificatioManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		messageIntent = new Intent(this, GetMessageActivity.class);
		messagePendingIntent = PendingIntent.getActivity(this, 0,
				messageIntent, 0);

		// 开启线程
		messageThread = new MessageThread();
		messageThread.isRunning = true;
		messageThread.start();

		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 从服务器端获取消息
	 * 
	 */
	class MessageThread extends Thread {
		// 设置是否循环推送
		public boolean isRunning = true;

		@SuppressWarnings("deprecation")
		public void run() {
			// while (isRunning) {
			try {
				// 间隔时间
				Thread.sleep(1000);
				// 获取服务器消息
				String serverMessage = getServerMessage();
				if (serverMessage != null && !"".equals(serverMessage)) {
					// 更新通知栏
					messageNotification.setLatestEventInfo(
							getApplicationContext(), "民兴通知", "本月6号召开全体村民会议！"
									+ serverMessage, messagePendingIntent);
					messageNotificatioManager.notify(messageNotificationID,
							messageNotification);
					// 每次通知完，通知ID递增一下，避免消息覆盖掉
					messageNotificationID++;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void onDestroy() {
		System.exit(0);
		// messageThread.isRunning = false;
		super.onDestroy();
	}

	/**
	 * 模拟发送消息
	 * 
	 * @return 返回服务器要推送的消息，否则如果为空的话，不推送
	 */
	public String getServerMessage() {
		return "Yes!";
	}

	public String getMessage() {
		return "本月6号召开全体村民会议！";
	}
}