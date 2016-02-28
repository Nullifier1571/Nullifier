package com.itjfr.jfr.fragment;

import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.itjfr.jfr.R;
import com.itjfr.jfr.config.Config;
import com.itjfr.jfr.domain.UserLoginInfo;
import com.itjfr.jfr.fragment.FragmentFactory.FragmentType;
import com.itjfr.jfr.utils.HttpTools;
import com.itjfr.jfr.utils.HttpTools.RequestNetListener;

public class MeFragment extends BaseFragment implements OnClickListener,
		RequestNetListener {

	// 标题
	private TextView menu_title;
	// 设置按钮
	private ImageView menu_setting;
	// 我的头像
	private ImageView me_iv_photo;
	// 我的名字
	private TextView me_tv_name;
	// 我的签名
	private TextView me_tv_sign;
	// 我的积分
	private TextView me_tv_score;
	// 我的消息
	private TextView me_tv_message;
	// 个人信息设置
	private RelativeLayout rl_1;
	// 商场中心
	private RelativeLayout rl_2;
	// 兑换中心
	private RelativeLayout rl_3;
	// 退出登录按钮
	private Button me_bt_logout;
	private HttpTools httpTools;
	private RelativeLayout area_1;
	private RelativeLayout area_2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_me, null);
		
		// 初始化组件
		initView(view);
		initData();
		return view;
	}

	private void initData() {
		// 设置进内存对象中
		UserLoginInfo userLoginInfo = UserLoginInfo.getUserLoginInfo();
		if (userLoginInfo.getNickName() != null) {
			// 设置给组件显示
			me_tv_name.setText(userLoginInfo.getNickName());
			me_tv_sign.setText(userLoginInfo.getSign());
			me_tv_score.setText(userLoginInfo.getScore());
			// TODO
			// me_iv_photo 用户头像待服务器完善
			// me_tv_message 我的消息待以后完善
		} else {
			// 如果内存中没有个人信息则初始化网络请求工具从网络中下载
			httpTools = HttpTools.getHttpToolsInstance();
			httpTools.setRequestNetListener(this);

			if (UserLoginInfo.getUserLoginInfo() != null) {
				// 设置参数
				Bundle requestArgs = new Bundle();
				requestArgs.putString("id", UserLoginInfo.getUserLoginInfo()
						.getPhoneNumber());
				httpTools.doPost(requestArgs, Config.USER_PERSONAL, 0);
			}
		}
	}

	private void initView(View view) {
		menu_title = (TextView) view.findViewById(R.id.menu_title);
		menu_title.setText("我");
		menu_setting = (ImageView) view.findViewById(R.id.menu_setting);
		menu_setting.setVisibility(View.VISIBLE);
		me_iv_photo = (ImageView) view.findViewById(R.id.me_iv_photo);
		me_tv_name = (TextView) view.findViewById(R.id.me_tv_name);
		me_tv_sign = (TextView) view.findViewById(R.id.me_tv_sign);
		me_tv_score = (TextView) view.findViewById(R.id.me_tv_score);
		me_tv_message = (TextView) view.findViewById(R.id.me_tv_message);
		area_1 = (RelativeLayout) view.findViewById(R.id.area_1);
		area_2 = (RelativeLayout) view.findViewById(R.id.area_2);
		rl_1 = (RelativeLayout) view.findViewById(R.id.rl_1);
		rl_2 = (RelativeLayout) view.findViewById(R.id.rl_2);
		rl_3 = (RelativeLayout) view.findViewById(R.id.rl_3);
		me_bt_logout = (Button) view.findViewById(R.id.me_bt_logout);
		menu_setting.setOnClickListener(this);
		rl_1.setOnClickListener(this);
		rl_2.setOnClickListener(this);
		rl_3.setOnClickListener(this);
		area_1.setOnClickListener(this);
		area_2.setOnClickListener(this);
		me_bt_logout.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_1:
			Fragment personDetailFragment = FragmentFactory
					.getFragment(FragmentType.PERSONDETAIL);

			switchPage(personDetailFragment);
			break;
		case R.id.rl_2:

			break;
		case R.id.rl_3:

			break;
		case R.id.area_1:
			switchPage(FragmentFactory.getFragment(FragmentType.SCORE));
			break;
		case R.id.area_2:

			break;

		case R.id.me_bt_logout:

			break;
		case R.id.menu_setting:
			Fragment settingFragment = FragmentFactory
					.getFragment(FragmentType.SETTING);

			switchPage(settingFragment);

			break;

		default:
			break;
		}

	}

	@Override
	public void getResultData(boolean isOk, String result, int Tag) {
		if (Tag == 0 && isOk) {
			try {
				// 获取用户个人页面的数据
				JSONObject dataResult = new JSONObject(result);
				String id = dataResult.getString("id");
				String nickname = dataResult.getString("nickname");
				String avatar = dataResult.getString("avatar");
				String points = dataResult.getString("points");
				String signWords = dataResult.getString("signWords");
				// 设置给组件显示
				me_tv_name.setText(nickname);
				me_tv_sign.setText(signWords);
				me_tv_score.setText(points);
				// TODO
				// me_iv_photo 用户头像待服务器完善
				// me_tv_message 我的消息待以后完善

				// 设置进内存对象中
				UserLoginInfo userLoginInfo = UserLoginInfo.getUserLoginInfo();
				userLoginInfo.setNickName(nickname);
				// 暂时设置为空
				userLoginInfo.setPhoto(null);
				userLoginInfo.setScore(points);
				userLoginInfo.setSign(signWords);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

}
