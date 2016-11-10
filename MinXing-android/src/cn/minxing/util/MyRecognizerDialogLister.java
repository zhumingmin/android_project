package cn.minxing.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;
import cn.minxing.activity.VoiceToolActivity;

import com.zhumingmin.vmsofminxing.R;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialogListener;

public class MyRecognizerDialogLister implements RecognizerDialogListener {
	private Context context;
	private String text;

	public MyRecognizerDialogLister(Context context) {
		this.context = context;
	}

	@Override
	public void onResult(RecognizerResult results, boolean isLast) {
		// TODO Auto-generated method stub
		this.text = JsonParser.parseIatResult(results.getResultString());
		//System.out.println(text);

		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}

	public String getText() {
		return text;
		
	}

	@Override
	public void onError(SpeechError error) {
		// TODO Auto-generated method stub
		int errorCoder = error.getErrorCode();
		switch (errorCoder) {
		case 10118:
			System.out.println("user don't speak anything");
			break;
		case 10204:
			System.out.println("can't connect to internet");
			break;
		default:
			break;
		}
	}

}
