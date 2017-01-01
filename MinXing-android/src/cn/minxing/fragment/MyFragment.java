package cn.minxing.fragment;

import android.support.v4.app.Fragment;

public abstract class MyFragment extends Fragment {
	protected boolean isVisible;

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint()) {
			isVisible = true;
			delayLoad();
		} else {
			isVisible = false;
		}
	}

	protected abstract void delayLoad();
}
