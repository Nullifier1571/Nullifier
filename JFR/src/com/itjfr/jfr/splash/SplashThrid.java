package com.itjfr.jfr.splash;

import com.itjfr.jfr.R;

import android.view.View;

public class SplashThrid extends WelcomeBaseActivty {

	@Override
	public int setImage() {
		return R.drawable.ic_launcher;
	}

	@Override
	public String setBody() {
		return "免费上网，睿币随手赚睿币\n商城可兑换各种优惠券和优惠商品";
	}

	@Override
	public String setTitle() {
		return "睿币商城";
	}

	@Override
	public boolean showButton() {
		return true;
	}

	@Override
	public void goNext() {

	}
}
