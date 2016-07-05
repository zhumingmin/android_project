package com.minxing.PushMessage;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

/*
 * 写一个ExitApplication 类，利用单例模式管理Activity，
 * 在每个在Activity 的onCreate()方法中调用ExitApplication.getInstance().addActivity(this)方法,
 * 在退出时调用ExitApplication.getInstance().exit()方法，
 * 就可以完全退出应用程序了。
 */
public class ExitApplication extends Application {

	private List<Activity> activityList = new LinkedList<Activity>();
	private static ExitApplication instance;

	private ExitApplication() {
	}

	// 单例模式中获取唯一的ExitApplication 实例
	public static ExitApplication getInstance() {
		if (null == instance) {
			instance = new ExitApplication();
		}
		return instance;
	}

	// 添加Activity 到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// 遍历所有Activity 并finish

	public void exit() {

		for (Activity activity : activityList) {
			activity.finish();
		}

		System.exit(0);

	}
}