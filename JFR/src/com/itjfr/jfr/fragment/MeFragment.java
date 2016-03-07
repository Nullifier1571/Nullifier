package com.itjfr.jfr.fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.itjfr.jfr.R;
import com.itjfr.jfr.activity.LoginActivity;
import com.itjfr.jfr.config.Config;
import com.itjfr.jfr.domain.UserLoginInfo;
import com.itjfr.jfr.fragment.FragmentFactory.FragmentType;
import com.itjfr.jfr.utils.FileTool;
import com.itjfr.jfr.utils.HttpTools;
import com.itjfr.jfr.utils.HttpTools.RequestNetListener;
import com.itjfr.jfr.utils.LogUtils;
import com.itjfr.jfr.utils.MessageSecurityTool;
import com.itjfr.jfr.utils.SharePrefaceTool;
import com.lidroid.xutils.BitmapUtils;

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
	private EditText me_ev_sign;
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
	private UserLoginInfo userLoginInfo;
	private boolean setSign;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_me, null);
		// 初始化组件
		initView(view);
		initData();
		return view;
	}

	@Override
	public void onStop() {

		super.onStop();
	}

	@Override
	public void onStart() {
		/*
		 * httpTools = HttpTools.getHttpToolsInstance();
		 * httpTools.setRequestNetListener(this);
		 */
		// 进入页面加载本地数据
		/*File file = new File(userLoginInfo.getLocalPhoto(userLoginInfo
				.getPhoneNumber()));
		if (file.exists()) {
			
		}*/
		me_iv_photo
		.setImageBitmap(BitmapFactory.decodeFile(userLoginInfo.getLocalPhoto(userLoginInfo
				.getPhoneNumber())));
		me_tv_name.setText(SharePrefaceTool.readout(getActivity(), "nickname"));
		me_ev_sign
				.setText(SharePrefaceTool.readout(getActivity(), "signWords"));
		super.onStart();
	}

	private void initData() {

		userLoginInfo = UserLoginInfo.getUserLoginInfo();
		if (userLoginInfo.getNickName() != null) {
			// 设置给组件显示
			me_tv_name.setText(SharePrefaceTool.readout(getActivity(),
					"nickname"));
			me_ev_sign.setText(SharePrefaceTool.readout(getActivity(),
					"signWords"));
			me_iv_photo.setImageBitmap(BitmapFactory.decodeFile(userLoginInfo
					.getLocalPhoto(userLoginInfo.getPhoneNumber())));
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
				requestArgs.putString("id", UserLoginInfo.getUserLoginInfo().getPhoneNumber());
				requestArgs.putString("token", UserLoginInfo.getUserLoginInfo().getToken());
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
		me_ev_sign = (EditText) view.findViewById(R.id.me_ev_sign);
		me_tv_score = (TextView) view.findViewById(R.id.me_tv_score);
		me_tv_message = (TextView) view.findViewById(R.id.me_tv_message);
		area_1 = (RelativeLayout) view.findViewById(R.id.area_1);
		area_2 = (RelativeLayout) view.findViewById(R.id.area_2);
		rl_1 = (RelativeLayout) view.findViewById(R.id.rl_1);
		rl_2 = (RelativeLayout) view.findViewById(R.id.rl_2);
		rl_3 = (RelativeLayout) view.findViewById(R.id.rl_3);
		me_bt_logout = (Button) view.findViewById(R.id.me_bt_logout);
		menu_setting.setOnClickListener(this);
		me_ev_sign.setOnClickListener(this);
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
			// 点击了个人详情页面进行跳转
			FragmentFactory.getCenterRadioButton(3).setChecked(false);
			switchPage(FragmentFactory.getFragment(FragmentType.PERSONDETAIL));
			// 因为在我的页面进行跳转，此时我的radoibutton还是被选中的状态，此时应不让他被选中
			break;
		case R.id.rl_2:

			break;
		case R.id.rl_3:

			break;
		case R.id.area_1:
			// 跳转积分页面
			FragmentFactory.getCenterRadioButton(3).setChecked(false);
			switchPage(FragmentFactory.getFragment(FragmentType.SCORE));
			break;
		case R.id.area_2:

			break;

		case R.id.me_bt_logout:
			UserLoginInfo.logoutUser();
			startActivity(new Intent(getActivity(), LoginActivity.class));
			getActivity().finish();
			break;
		case R.id.menu_setting:
			// 跳转设置页面
			FragmentFactory.getCenterRadioButton(3).setChecked(false);
			switchPage(FragmentFactory.getFragment(FragmentType.SETTING));
			break;
		case R.id.me_ev_sign:

			if (!setSign) {
				// 不可编辑再点就让他编辑
				setSign = true;
			} else {
				// 可编辑再点就不让编辑
				setSign = false;
				String sign = me_ev_sign.getText().toString();
				modifySignword(sign);
			}
			// 点击了个性签名
			me_ev_sign.setCursorVisible(setSign);
		default:
			break;
		}

	}

	/*
	 * private void uploadSign(String signWords) {
	 * 
	 * String olderSignWords = SharePrefaceTool.readout(getActivity(),
	 * "signWords"); // 校验有更改去请求网络 if (!signWords.equals(olderSignWords)) { //
	 * 将新的昵称存入本地 SharePrefaceTool.writein(getActivity(), "signWords",
	 * signWords); if (UserLoginInfo.getUserLoginInfo() != null) { // 更改内存中的数据
	 * UserLoginInfo.getUserLoginInfo().setSign(signWords); // 设置参数 Bundle
	 * requestArgs = new Bundle(); requestArgs.putString("id",
	 * UserLoginInfo.getUserLoginInfo() .getPhoneNumber());
	 * requestArgs.putString("sign", signWords); httpTools.doPost(requestArgs,
	 * Config.USER_UP_SIGNWORD, 1); } }
	 * 
	 * }
	 */

	@Override
	public void getResultData(boolean isOk, int errorCode, String result,
			int Tag) {
		switch (Tag) {
		case 0:
			if (isOk) {
				try {
					// 获取用户个人页面的数据
					JSONObject dataResult = new JSONObject(result);
					String id = dataResult.getString("id");
					String nickname = dataResult.getString("nickname");
					String avatar = dataResult.getString("avatar");
					String points = dataResult.getString("points");
					String signWords = dataResult.getString("signWords");

					// TODO
					// me_iv_photo 用户头像待服务器完善只保存在本地做更新
					// me_tv_message 我的消息待以后完善

					// bitmapUtils.display(me_iv_photo, avatar);
					UserLoginInfo userLoginInfo = UserLoginInfo
							.getUserLoginInfo();
					userLoginInfo.setNickName(nickname);
					// 暂时设置为空 头像图片只更新在本地 不进行设置
					userLoginInfo.setScore(points);
					userLoginInfo.setSign(signWords);

					String[] strings = SharePrefaceTool.readout(getActivity(),
							new String[] { "nickname", "signWords" });
					if (TextUtils.isEmpty(strings[0])) {
						modifyNickname(userLoginInfo.getNickName());
					} else {
						modifyNickname(strings[0]);
					}

					if (TextUtils.isEmpty(strings[1])) {
						modifySignword(userLoginInfo.getSign());
					} else {
						modifySignword(strings[1]);
					}
					// 设置给组件显示
					me_tv_name.setText(SharePrefaceTool.readout(getActivity(),
							"nickname"));
					me_ev_sign.setText(SharePrefaceTool.readout(getActivity(),
							"signWords"));
					me_tv_score.setText(points);
					// SharePrefaceTool.writein(getActivity(), new String[]
					// {"nickname", "signWords" }, new String[] {
					// nickname,signWords });
					// 设置进内存对象中

					// 将服务器图片信息同步到本地中
					httpTools
							.downLoadFile(
									Config.TestHostAddress + "/" + avatar,
									userLoginInfo.getNetPhotoPath(userLoginInfo
											.getPhoneNumber()), 1);

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
			break;
		case 1:
			if (isOk) {
				LogUtils.e("个性签名上传完毕");
			}
			break;
		case 2:
			if (isOk) {
				LogUtils.e("本地文件与网络不同上传完毕");
			}
			break;
		default:
			break;
		}

	}

	private void modifySignword(String signWords) {

		String olderSignWords = SharePrefaceTool.readout(getActivity(),
				"signWords");
		// 校验有更改去请求网络
		if (!signWords.equals(olderSignWords)) {
			// 将新的昵称存入本地
			SharePrefaceTool.writein(getActivity(), "signWords", signWords);
			if (UserLoginInfo.getUserLoginInfo() != null) {
				// 更改内存中的数据
				UserLoginInfo.getUserLoginInfo().setSign(signWords);
				// 设置参数
				Bundle requestArgs = new Bundle();
				requestArgs.putString("id", UserLoginInfo.getUserLoginInfo()
						.getPhoneNumber());
				requestArgs.putString("sign", signWords);
				requestArgs.putString("token", UserLoginInfo.getUserLoginInfo().getToken());
				httpTools.doPost(requestArgs, Config.USER_UP_SIGNWORD, 1);
			}
		}

	}

	private void modifyNickname(String newNickName) {
		String olderNickname = SharePrefaceTool.readout(getActivity(),
				"nickname");
		// 校验有更改去请求网络
		if (!olderNickname.equals(newNickName)) {
			// 将新的昵称存入本地
			SharePrefaceTool.writein(getActivity(), "nickname", newNickName);
			if (UserLoginInfo.getUserLoginInfo() != null) {
				// 更改内存中的数据
				UserLoginInfo.getUserLoginInfo().setNickName(newNickName);
				// 设置参数
				Bundle requestArgs = new Bundle();
				requestArgs.putString("id", UserLoginInfo.getUserLoginInfo()
						.getPhoneNumber());
				requestArgs.putString("token", UserLoginInfo.getUserLoginInfo().getToken());
				requestArgs.putString("username", newNickName);
				httpTools.doPost(requestArgs, Config.USER_UP_NICKNAME, 2);
			}
		}
	}

	@Override
	public void getDownLoadFile(boolean isOk, File file, int Tag) {
		switch (Tag) {
		case 1:
			try {
				// 检测从网络上获取的文件sha1值
				String newFileSha1 = MessageSecurityTool
						.getFileSha1(new FileInputStream(file));
				// 获取本地文件对象
				File localFile = new File(
						userLoginInfo.getLocalPhoto(userLoginInfo
								.getPhoneNumber()));
				// 如果文件不存在 则是本地第一次进入以网络的为准不再上传本地的
				if (!localFile.exists()) {
					if (localFile.createNewFile()) {
						FileTool.copyFile(file.getPath(), localFile.getPath());
						me_iv_photo.setImageBitmap(BitmapFactory
								.decodeFile(file.getPath()));
						return;
					}
				}

				String olderFileSha1 = MessageSecurityTool
						.getFileSha1(new FileInputStream(localFile));
				// 如果本地文件与网络文件不同步则需要上传本地文件
				if (!newFileSha1.equals(olderFileSha1)) {
					Bundle requestArgs = new Bundle();
					// requestArgs.putString("id",
					// UserLoginInfo.getUserLoginInfo().getPhoneNumber());
					requestArgs.putString("token", UserLoginInfo.getUserLoginInfo().getToken());
					httpTools.doPost(requestArgs, Config.USER_UP_AVATER, "pic",
							localFile, 2);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;

		default:
			break;
		}

	}

}
