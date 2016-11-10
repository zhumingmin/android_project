package cn.minxing.activity;

import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.impl.CommunityFactory;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

public class MinXingSheQuActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		// 获取CommunitySDK实例, 参数1为Context类型
		CommunitySDK mCommSDK = CommunityFactory.getCommSDK(MinXingSheQuActivity.this);
		// 打开微社区的接口, 参数1为Context类型
		mCommSDK.openCommunity(MinXingSheQuActivity.this);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

	}

	private void DisplayToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {

			MinXingSheQuActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
