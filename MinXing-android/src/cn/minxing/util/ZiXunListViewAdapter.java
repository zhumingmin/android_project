package cn.minxing.util;

import java.util.ArrayList;
import java.util.List;

import cn.minxing.fragment.zixun.ReDianDetailActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.zhumingmin.vmsofminxing.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ZiXunListViewAdapter extends ArrayAdapter<ZiXun> {

	private Context ctx;
	private int resourceId;
	List<ZiXun> object;
	ZiXun zixun;
	private ImageLoader mImageLoader;
	private RequestQueue mQueue;
	ImageView tupian;

	public ZiXunListViewAdapter(Context ctx, int textViewResourceId,
			List<ZiXun> object) {

		super(ctx, textViewResourceId, object);
		this.ctx = ctx;
		this.object = object;
		resourceId = textViewResourceId;
		mQueue = Volley.newRequestQueue(ctx);
		mImageLoader = new ImageLoader(mQueue, new BitmapCache());

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		zixun = getItem(position);

		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

		TextView biaoti = (TextView) view.findViewById(R.id.zx_biaoti);
		TextView laiyuan = (TextView) view.findViewById(R.id.zx_laiyuan);
		TextView shijian = (TextView) view.findViewById(R.id.zx_shijian);
		TextView yuedu = (TextView) view.findViewById(R.id.zx_yuedu);
		tupian = (ImageView) view.findViewById(R.id.zx_tupian);

		biaoti.setText(zixun.getBiaoTi());
		laiyuan.setText(zixun.getLaiYuan());
		shijian.setText(zixun.getShiJian());
		yuedu.setText(zixun.getYueDu());

		ImageListener listener = ImageLoader
				.getImageListener(tupian, android.R.drawable.ic_menu_rotate,
						android.R.drawable.ic_delete);
		mImageLoader.get(zixun.getTuPian(), listener);

		return view;
	}

}
