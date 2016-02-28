package com.itjfr.jfr.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.itjfr.jfr.R;
import com.itjfr.jfr.fragment.FragmentFactory;
import com.itjfr.jfr.fragment.FragmentFactory.FragmentType;
import com.itjfr.jfr.fragment.GuidanceFragment;
import com.itjfr.jfr.fragment.HomeFragment;
import com.itjfr.jfr.fragment.MarketFragment;
import com.itjfr.jfr.fragment.MeFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class CenterActivity extends FragmentActivity implements
		OnCheckedChangeListener {

	@ViewInject(R.id.center_tab)
	private RadioGroup center_tab;
	private Fragment mContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_center);
		ViewUtils.inject(this);
		center_tab.setOnCheckedChangeListener(this);
		switchPage(FragmentFactory.getFragment(FragmentType.HOME));
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int position) {
		switch (position) {
		case 1:
			switchPage(FragmentFactory.getFragment(FragmentType.HOME));
			break;
		case 2:
			switchPage(FragmentFactory.getFragment(FragmentType.MARKET));
			break;
		case 3:
			switchPage(FragmentFactory.getFragment(FragmentType.GUIDANCE));
			break;
		case 4:
			switchPage(FragmentFactory.getFragment(FragmentType.ME));
			break;

		default:
			break;
		}
	}

	private void switchPage(Fragment fagment) {
		FragmentFactory.switchPager(fagment, R.id.center_pager, this);
	}

}
