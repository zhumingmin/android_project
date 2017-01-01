package cn.minxing.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import activeandroid.util.Log;
import android.content.Context;

/**
 * 缓存服务器接收到的数据到本地，设置一个时间阈值 一定时间内访问同一个连接直接从本地文件获取 超过一定时间，缓存过期，需要重新从网络获取
 */
public class CacheServerResponse {
	/**
	 * 设置缓存有效期是20分钟 可以专门把失效时间作为一个参数，供不同失效时间场景定制缓存失效时间 这个是默认缓存有效期的时间
	 */
	private final static int CACHE_TIME_DEFAULT = 1000 * 60 * 20;
	/**
	 * 文件名为键，缓存有效时间为值，可以实现针对每一个缓存文件设置不同的有效时间
	 */
	private final static HashMap<String, Integer> mMap = new HashMap<String, Integer>();
	/**
	 * 缓存文件的后缀
	 */
	private static final String CACHE_EXTENSION = ".ser";
	private static final String CACHE_SHARED_PREFERENCES = "cache_sharedpreferences";

	/**
	 * 不带缓存时间的写缓存，就使用默认的缓存有效时间CACHE_TIME_DEFAULT
	 * 
	 * @param AppContext
	 * @param fileName
	 * @param serializableObject
	 * @return
	 */
	public static boolean saveObject(Context AppContext, String fileName,
			Serializable serializableObject) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = AppContext.openFileOutput(fileName + CACHE_EXTENSION,
					AppContext.MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(serializableObject);
			oos.flush();
			Log.v("response 文件 " + fileName + CACHE_EXTENSION + " 已缓存");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 增加一个参数，可以为每一个缓存文件设置自己的失效时间
	 * 
	 * @param AppContext
	 * @param fileName
	 * @param serializableObject
	 * @param validMinutes
	 * @return
	 */
	public static boolean saveObject(Context AppContext, String fileName,
			Serializable serializableObject, int validMinutes) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		mMap.put(fileName, validMinutes);
		try {
			fos = AppContext.openFileOutput(fileName + CACHE_EXTENSION,
					AppContext.MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(serializableObject);
			oos.flush();
			Log.d("response 文件 " + fileName + CACHE_EXTENSION + " 已缓存");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读缓存
	 * 
	 * @param AppContext
	 * @param file
	 * @return
	 */
	public static Serializable readObject(Context AppContext, String file) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = AppContext.openFileInput(file + CACHE_EXTENSION);
			ois = new ObjectInputStream(fis);
			return (Serializable) ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 检查此文件的缓存是否已经失效
	 */
	public static boolean isCacheDataFailure(Context AppContext,
			String cacheFileName) {
		boolean failure = false;
		int cache_time = CACHE_TIME_DEFAULT;
		if (null != mMap.get(cacheFileName)) {
			/* 换算成毫秒 */
			cache_time = mMap.get(cacheFileName) * 60 * 1000;
		}
		File data = AppContext.getFileStreamPath(cacheFileName
				+ CACHE_EXTENSION);
		if (data.exists()
				&& (System.currentTimeMillis() - data.lastModified()) > cache_time)
			failure = true;
		else if (!data.exists())
			failure = true;
		return failure;
	}

	/**
	 * 清除所有的缓存
	 * 
	 * @param mContext
	 */
	public static void clearAllCache(Context mContext) {
		clearAllCache(mContext);
		File filesDir = mContext.getFilesDir();
		Log.v("缓存文件根路径：" + filesDir.getAbsolutePath());
		// 实现FilenameFilter接口的类实例可用于过滤器文件名
		FilenameFilter filter = new FilenameFilter() {
			// accept(File dir, String name)
			// 测试指定文件是否应该包含在某一文件列表中。
			public boolean accept(File dir, String name) {
				return name.endsWith(CACHE_EXTENSION);
			}
		};
		// 返回一个字符串数组，这些字符串指定此抽象路径名表示的目录中满足指定过滤器的文件和目录
		String[] cacheFileList = filesDir.list(filter);
		File temp;
		for (String s : cacheFileList) {
			Log.v("缓存文件名：" + s);
			/*
			 * temp=new File(s); 这样打开文件只是在根目录下打开了文件，并不是在缓存文件夹打开文件，所以删除缓存失败
			 */
			/* new File(s)：/MyQuestionActivity.ser */
			/* DebugLog.v("new File(s)："+temp.getAbsolutePath()); */
			temp = mContext.getFileStreamPath(s);
			Log.v("new File(s)：" + temp.getAbsolutePath());
			if (temp.delete()) {
				Log.v("已删除缓存文件：" + s);
			} else {
				Log.v("缓存文件删除失败：" + s);
			}
		}
	}
}