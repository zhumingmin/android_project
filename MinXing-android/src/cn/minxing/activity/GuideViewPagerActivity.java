package cn.minxing.activity;

import java.util.ArrayList;
import java.util.List;

import cn.minxing.restwebservice.LoginService;

import com.zhumingmin.vmsofminxing.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GuideViewPagerActivity extends Activity implements OnPageChangeListener {

	View mGuideView1, mGuideView2, mGuideView3;// /引导页
	List<View> mViewList;// 引导页List
	ViewPager mViewPager;
	Button mStartButton;// “马上体验”按钮
	LinearLayout dot_linearlayout;// LinearLayout小圆点L
	int dot_cur_position = 0;// 小圆点的当前位置，初始为0

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.viewpager_guide_page);

		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		dot_linearlayout = (LinearLayout) findViewById(R.id.dot_linearlayout);

		LayoutInflater inflater = LayoutInflater.from(this);
		mGuideView1 = inflater.inflate(R.layout.guide_page_1, null);
		mGuideView2 = inflater.inflate(R.layout.guide_page_2, null);
		mGuideView3 = inflater.inflate(R.layout.guide_page_3, null);
		mViewList = new ArrayList<View>();
		mViewList.add(mGuideView1);
		mViewList.add(mGuideView2);
		mViewList.add(mGuideView3);

		mViewPager.setAdapter(new PagerAdapter() {

			/**
			 * PagerAdapter的一个方法是instantiateItem(ViewGroup container, int
			 * position)该方法声明了返回值不一定是view，可以是任意对象。
			 * 要知道view的添加是在该方法内部，通过container来添加的，所以这个方法不一定要返回view。
			 * 而isViewFromObject方法是用来判断pager的一个view是否和instantiateItem方法返回的object有关联
			 * ，如果有关联做什么呢？
			 * 去看代码吧ViewPager源码，你去看下addNewItem方法，会找到instantiateItem的使用方法
			 * ，注意这里的mItems变量。
			 * 然后你再搜索下isViewFromObject，会发现其被infoForChild方法调用，返回值是ItemInfo。
			 * 再去看下ItemInfo的结构，其中有一个object对象，该值就是instantiateItem返回的。
			 * 也就是说，ViewPager里面用了一个mItems(ArrayList)来存储每个page的信息(ItemInfo)，
			 * 当界面要展示或者发生变化时，需要依据page的当前信息来调整，但此时只能通过view来查找，
			 * 所以只能遍历mItems通过比较view和object来找到对应的ItemInfo。 说的有些乱，好好看源码就懂了！
			 */
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			// 获取要滑动的控件的数量，在这里我们以滑动的广告栏为例，那么这里就应该是展示的广告图片的ImageView数量
			@Override
			public int getCount() {
				return mViewList.size();
			}

			// PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
			@Override
			public void destroyItem(ViewGroup view, int position, Object object) {
				view.removeView(mViewList.get(position));
			}

			// 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(mViewList.get(position));
				return mViewList.get(position);
			}
		});

		for (int i = 0; i < mViewList.size(); i++) {
			ImageView imageView = new ImageView(this);
			// 默认第一个页面的小圆点为选中状态
			if (i == 0) {
				imageView.setImageResource(R.drawable.point_checked);
			} else {
				imageView.setImageResource(R.drawable.point_unchecked);
			}
			dot_linearlayout.addView(imageView);
		}

		mStartButton = (Button) mGuideView3.findViewById(R.id.start_button);
		mStartButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(GuideViewPagerActivity.this,
						LoginService.class);
				startActivity(intent);
				GuideViewPagerActivity.this.finish();
			}
		});
		mViewPager.setOnPageChangeListener(this);

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		// 设置前一个选中的不圆点不未选中状态
		ImageView im1 = (ImageView) dot_linearlayout
				.getChildAt(dot_cur_position);
		im1.setImageResource(R.drawable.point_unchecked);
		// 设置当前选中的小圆点为选中状态
		ImageView im2 = (ImageView) dot_linearlayout.getChildAt(position);
		im2.setImageResource(R.drawable.point_checked);
		dot_cur_position = position;
	}
}
