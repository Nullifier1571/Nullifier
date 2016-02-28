package com.itjfr.jfr.splash;

import com.itjfr.jfr.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Nullifier 引导页面的父类
 */
public abstract class WelcomeBaseActivty extends Activity implements
		OnClickListener {
	// 说明文字
	@ViewInject(R.id.splash_body)
	private TextView splash_body;
	// 标题文字
	@ViewInject(R.id.splash_title)
	private TextView splash_title;
	// 图片
	@ViewInject(R.id.splash_image)
	private ImageView splash_image;
	// 进入按钮
	@ViewInject(R.id.splash_button)
	private Button splash_button;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new Handler(Looper.myLooper()) {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				handler.removeMessages(0);
				goNext();
			}
		};

		setContentView(R.layout.activity_splash);
		// ViewUtils.inject(this);
		splash_image = (ImageView) findViewById(R.id.splash_image);
		splash_body = (TextView) findViewById(R.id.splash_body);
		splash_title = (TextView) findViewById(R.id.splash_title);
		splash_button = (Button) findViewById(R.id.splash_button);

		splash_image.setImageResource(setImage());
		splash_body.setText(setBody());
		splash_title.setText(setTitle());
		if (showButton()) {
			splash_button.setVisibility(View.VISIBLE);
			splash_button.setOnClickListener(this);
		}
		handler.sendEmptyMessageDelayed(0, 3000);
	}

	/**
	 * 子类去复写此方法以设置图片
	 * 
	 * @return 图片资源的ID
	 */
	public abstract int setImage();

	/**
	 * 子类去复写此方法以设置文字说明
	 * 
	 * @return 说明文字
	 */
	public abstract String setBody();

	/**
	 * 子类去复写此方法以设置标题文字
	 * 
	 * @return 标题文字
	 */
	public abstract String setTitle();

	public abstract void goNext();

	public abstract boolean showButton();

	@Override
	public void onClick(View view) {
		// 点击了按钮
		finish();

	}
}
