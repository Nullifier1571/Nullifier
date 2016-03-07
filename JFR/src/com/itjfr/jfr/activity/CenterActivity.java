package com.itjfr.jfr.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.itjfr.jfr.R;
import com.itjfr.jfr.fragment.FragmentFactory;
import com.itjfr.jfr.fragment.FragmentFactory.FragmentType;
import com.itjfr.jfr.fragment.GuidanceFragment;
import com.itjfr.jfr.fragment.HomeFragment;
import com.itjfr.jfr.fragment.MarketFragment;
import com.itjfr.jfr.fragment.MeFragment;
import com.itjfr.jfr.utils.FileTool;
import com.itjfr.jfr.utils.LogUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class CenterActivity extends FragmentActivity implements
		OnCheckedChangeListener {

	@ViewInject(R.id.center_tab)
	private RadioGroup center_tab;

	@ViewInject(R.id.rb_home)
	private RadioButton rb_home;

	@ViewInject(R.id.rb_market)
	private RadioButton rb_market;

	@ViewInject(R.id.rb_guide)
	private RadioButton rb_guide;

	@ViewInject(R.id.rb_me)
	private RadioButton rb_me;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_center);
		ViewUtils.inject(this);
		center_tab.setOnCheckedChangeListener(this);
		FragmentFactory.setCenterRadioGroup(center_tab);
		switchPage(FragmentFactory.getFragment(FragmentType.HOME));
		LogUtils.e("可点击的按钮" + center_tab.getCheckedRadioButtonId());
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int position) {

		LogUtils.e("RadioGroup的ID:+++" + arg0.getId());
		LogUtils.e("Position:+++" + position);

		switch (arg0.getCheckedRadioButtonId()) {
		case R.id.rb_home:
			switchPage(FragmentFactory.getFragment(FragmentType.HOME));
			break;

		case R.id.rb_market:
			switchPage(FragmentFactory.getFragment(FragmentType.MARKET));
			break;

		case R.id.rb_guide:
			switchPage(FragmentFactory.getFragment(FragmentType.GUIDANCE));
			break;
		case R.id.rb_me:
			switchPage(FragmentFactory.getFragment(FragmentType.ME));
			break;
		default:
			break;
		}

	}

	private void switchPage(Fragment fagment) {
		FragmentFactory.switchPager(fagment, R.id.center_pager, this);
	}

	@Override
	public void onBackPressed() {
		FragmentFactory.cleanAllFragment();
		finish();
		super.onBackPressed();
	}
}
