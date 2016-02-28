package com.itjfr.jfr.splash;

import com.itjfr.jfr.R;

import android.content.Intent;
import android.view.View;

public class SplashFirst extends WelcomeBaseActivty {

	@Override
	public int setImage() {
		return R.drawable.ic_launcher;
	}

	@Override
	public String setBody() {
		return "只需一键，轻松畅想免费wifi";
	}

	@Override
	public String setTitle() {
		return "一键上网";
	}

	@Override
	public boolean showButton() {
		return false;
	}

	@Override
	public void goNext() {
		startActivity(new Intent(this, SplashSecond.class));
		finish();

	}
}
