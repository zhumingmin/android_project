package com.minxing.test;

import com.minxing.util.HttpUtils;

import android.test.AndroidTestCase;

public class Test extends AndroidTestCase {
	public void testSendMsg() {
		HttpUtils.sendMsg("西斜七路堵车吗");
		HttpUtils.sendMsg("你好");
		HttpUtils.sendMsg("讲个笑话");
		HttpUtils.sendMsg("新浪体育");
	}

}
