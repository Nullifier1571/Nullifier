package com.itjfr.jfr.fragment;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.itjfr.jfr.fragment.FragmentFactory.FragmentType;
import com.viewpagerindicator.IconPagerAdapter;

class ViewPagerFragmentAdapter extends FragmentPagerAdapter implements
		IconPagerAdapter {
	
	private List<String> imageUrls;

	public ViewPagerFragmentAdapter(FragmentManager fm, List<String> imageUrls) {
		super(fm);
		this.imageUrls = imageUrls;
	}

	@Override
	public Fragment getItem(int position) {
		List<Fragment> listFragment = FragmentFactory.getFragment(FragmentType.IMAGE, imageUrls);
		return listFragment.get(position%imageUrls.size());
	}

	@Override
	public int getCount() {
		return imageUrls.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "测试";
	}

	@Override
	public int getIconResId(int index) {
		return index%imageUrls.size();
	}

	public void setCount(int count) {
		notifyDataSetChanged();
	}
}
