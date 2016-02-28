package com.itjfr.jfr.splash;

import com.itjfr.jfr.R;

import android.content.Intent;
import android.view.View;

public class SplashSecond extends WelcomeBaseActivty {

	@Override
	public int setImage() {
		// TODO Auto-generated method stub
		return R.drawable.ic_launcher;
	}

	@Override
	public String setBody() {
		// TODO Auto-generated method stub
		return "走到哪看到哪";
	}

	@Override
	public String setTitle() {
		// TODO Auto-generated method stub
		return "新闻  笑话  美图";
	}

	@Override
	public boolean showButton() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void goNext() {
		startActivity(new Intent(this, SplashThrid.class));
		finish();
		
	}
}
