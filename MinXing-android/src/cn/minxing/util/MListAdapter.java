package cn.minxing.util;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.zhumingmin.vmsofminxing.R;

public class MListAdapter extends BaseAdapter {
	private Context ctx;
	private ArrayList<Info> infos;
	private RequestQueue mQueue;
	private ImageLoader mImageLoader;

	public MListAdapter(Context ctx, ArrayList<Info> infos) {
		this.ctx = ctx;
		this.infos = infos;
		mQueue = Volley.newRequestQueue(ctx);
		mImageLoader = new ImageLoader(mQueue, new BitmapCache());
	}

	@Override
	public int getCount() {
		return infos.size();
	}

	@Override
	public Info getItem(int position) {
		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(ctx).inflate(R.layout.zixun_list_item, null);
		ImageView imageView = (ImageView) convertView.findViewById(R.id.zx_tupian);
		ImageListener listener = ImageLoader
				.getImageListener(imageView, android.R.drawable.ic_menu_rotate,
						android.R.drawable.ic_delete);
		mImageLoader.get(getItem(position).getUrl(), listener);
		return convertView;
	}

}
