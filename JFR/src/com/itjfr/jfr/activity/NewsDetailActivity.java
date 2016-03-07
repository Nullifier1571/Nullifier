package com.itjfr.jfr.activity;

import java.io.File;

import com.google.gson.Gson;
import com.itjfr.jfr.R;
import com.itjfr.jfr.config.Config;
import com.itjfr.jfr.domain.NewsDetailInfo;
import com.itjfr.jfr.domain.UserLoginInfo;
import com.itjfr.jfr.utils.HttpTools;
import com.itjfr.jfr.utils.HttpTools.RequestNetListener;
import com.itjfr.jfr.utils.LogUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class NewsDetailActivity extends Activity implements RequestNetListener {
	@ViewInject(R.id.news_tv_detail_content)
	private TextView news_tv_detail_content;

	@ViewInject(R.id.news_lv_detail_comment)
	private ListView news_lv_detail_comment;

	@ViewInject(R.id.news_et_detail_comment)
	private EditText news_et_detail_comment;

	private HttpTools httpTools;

	private Gson gson;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_detail);
		ViewUtils.inject(this);
		String news_id = getIntent().getStringExtra("news_id");
		LogUtils.e("新闻ID" + news_id);
		String phoneNumber = UserLoginInfo.getUserLoginInfo().getPhoneNumber();
		httpTools = HttpTools.getHttpToolsInstance();
		httpTools.setRequestNetListener(this);
		gson = new Gson();
		// 如果新闻ID和用户ID都不为空才发起请求
		if (!TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(news_id)) {
			Bundle requestArgs = new Bundle();
			requestArgs.putString("id", news_id);
			requestArgs.putString("uid", phoneNumber);
			httpTools.doPost(requestArgs, Config.NEWS_DETAIL, 0);
		} else {
			LogUtils.toastMessage(this, "您没有登录不能查看");
		}

	}

	@Override
	public void getResultData(boolean isOk, int errorCode, String result, int Tag) {
		if (isOk) {
			LogUtils.e("请求数据成功" + result);
			NewsDetailInfo newsDetailInfo = gson.fromJson(result, NewsDetailInfo.class);
			LogUtils.e("数据装载" + newsDetailInfo);
			
		} else {
			LogUtils.e("请求数据失败" + result);
		}
	}

	@Override
	public void getDownLoadFile(boolean isOk, File file, int Tag) {
		// TODO Auto-generated method stub
		
	}
}
