package com.minxing.activity;

import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;  
import android.app.AlertDialog;  
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;  
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;  
  
/** 
 * 使用AlertDialog进行选择功能 
 *  
 * @author jiangqq 
 *  
 */  
public class AlertDialogActivity extends Activity {  
    private Button btn;  
  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_home);  
  
        btn = (Button) findViewById(R.id.DianHuaZiXun);  
        btn.setOnClickListener(new OnClickListener() {  
  
            public void onClick(View v) {  
                final String[] items = getResources().getStringArray(  
                        R.array.item);  
                new AlertDialog.Builder(AlertDialogActivity.this)  
                        .setTitle("请点击选择")  
                        .setItems(items, new DialogInterface.OnClickListener() {  
  
                            public void onClick(DialogInterface dialog,  
                                    int which) {  
                                new AlertDialog.Builder(  
                                        AlertDialogActivity.this)  
                                        .setTitle("你选择了:" + items[which])  
                                        .setMessage("点击选择操作")  
                                        .setPositiveButton(  
                                                "确定",  
                                                new DialogInterface.OnClickListener() {  
  
                                                    public void onClick(  
                                                            DialogInterface dialog,  
                                                            int which) {  
                                                        // 这里是你点击确定之后可以进行的操作   
                                                    }  
                                                })  
                                        .setNegativeButton(  
                                                "取消",  
                                                new DialogInterface.OnClickListener() {  
  
                                                    public void onClick(  
                                                            DialogInterface dialog,  
                                                            int which) {  
                                                        // 这里点击取消之后可以进行的操作   
                                                    }  
                                                }).show();  
                            }  
                        }).show();  
            }  
        });  
    }  
}


/*dianhuazixun.setOnClickListener(new Button.OnClickListener() {

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new Builder(Home.this);
		builder.setMessage("准备接通咨询中……");
		//CharSequence[] itemsId = {"户籍管理咨询","医保管理咨询","财务管理咨询","计划生育咨询","保健管理咨询"};
		builder.setTitle("提示");
		//.setItems(itemsId, listener);
		builder.setPositiveButton("确认咨询",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						arg0.dismiss();
						Intent in2 = new Intent();
						in2.setAction(Intent.ACTION_CALL);
						in2.setData(Uri.parse("tel:18826487090"));
						startActivity(in2);
					}
				});
		builder.setNegativeButton("下次咨询",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();

	}

});
*/
