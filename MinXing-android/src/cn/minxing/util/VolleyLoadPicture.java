package cn.minxing.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;

/**
 * 用Volley加载图片
 * @author zzy
 *
 */
public class VolleyLoadPicture {
	
	private ImageLoader mImageLoader = null;
	private BitmapCache mBitmapCache;
	
	private ImageListener one_listener;
	
	public VolleyLoadPicture(Context context,ImageView imageView){
		one_listener = ImageLoader.getImageListener(imageView, 0, 0);
		
		RequestQueue mRequestQueue = Volley.newRequestQueue(context);
		mBitmapCache = new BitmapCache();
		mImageLoader = new ImageLoader(mRequestQueue, mBitmapCache);
	}

	public ImageLoader getmImageLoader() {
		return mImageLoader;
	}

	public void setmImageLoader(ImageLoader mImageLoader) {
		this.mImageLoader = mImageLoader;
	}

	public ImageListener getOne_listener() {
		return one_listener;
	}

	public void setOne_listener(ImageListener one_listener) {
		this.one_listener = one_listener;
	}
	
	class BitmapCache implements ImageCache {
		private LruCache<String, Bitmap> mCache;
		private int sizeValue;
		
		public BitmapCache() {
			int maxSize = 10 * 1024 * 1024;
			mCache = new LruCache<String, Bitmap>(maxSize) {
				@Override
				protected int sizeOf(String key, Bitmap value) {
					sizeValue = value.getRowBytes() * value.getHeight();
					return sizeValue;
				}
				
			};
		}

		@Override
		public Bitmap getBitmap(String url) {
			return mCache.get(url);
		}

		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			mCache.put(url, bitmap);
		}
	}
	

}
