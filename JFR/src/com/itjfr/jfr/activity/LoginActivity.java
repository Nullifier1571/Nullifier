package com.itjfr.jfr.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.itjfr.jfr.R;
import com.itjfr.jfr.R.layout;
import com.itjfr.jfr.R.menu;
import com.itjfr.jfr.config.Config;
import com.itjfr.jfr.domain.UserLoginInfo;
import com.itjfr.jfr.splash.SplashFirst;
import com.itjfr.jfr.utils.HttpTools;
import com.itjfr.jfr.utils.InputCheckTool;
import com.itjfr.jfr.utils.HttpTools.RequestNetListener;
import com.itjfr.jfr.utils.LogUtils;
import com.itjfr.jfr.utils.SharePrefaceTool;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
/**
 * 登录页面
 * @author Nullifier
 *
 */
public class LoginActivity extends Activity implements OnClickListener,
		RequestNetListener {
	@ViewInject(R.id.login_bt_login)
	private Button login_bt_login;
	// 注册
	@ViewInject(R.id.login_tv_regist)
	private TextView login_tv_regist;
	// 忘记密码
	@ViewInject(R.id.login_tv_forget_password)
	private TextView login_tv_forget_password;
	// 手机号
	@ViewInject(R.id.login_ev_phone)
	private TextView login_ev_phone;
	// 密码
	@ViewInject(R.id.login_ev_password)
	private TextView login_ev_password;
	private HttpTools httpTools;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ViewUtils.inject(this);

		if (SharePrefaceTool.readboolean(this, "isFirst")) {
			// 将标记置为false
			SharePrefaceTool.writein(this, "isFirst", false);
			// 跳转到引导页面
			startActivity(new Intent(this, SplashFirst.class));
		}

		login_bt_login.setOnClickListener(this);
		login_tv_regist.setOnClickListener(this);
		login_tv_forget_password.setOnClickListener(this);
		// 获取网络连接工具
		httpTools = HttpTools.getHttpToolsInstance();
		// 注册监听器
		httpTools.setRequestNetListener(this);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		// 点击了登录按钮
		case R.id.login_bt_login:
			String phone = login_ev_phone.getText().toString();
			String password = login_ev_password.getText().toString();
			if (!InputCheckTool.isPhoneNumber(phone)) {
				LogUtils.toastMessage(this, "请输入正确的手机号");
				return;
			}
			if (!InputCheckTool.isPassword(password)) {
				LogUtils.toastMessage(this, "密码必须包含且仅包含大小写字母和数字");
				return;
			}
			// 设置参数
			Bundle requestArgs = new Bundle();
			requestArgs.putString("mobile", phone);
			requestArgs.putString("password", password);
			httpTools.doPost(requestArgs, Config.USER_LOGIN, 0);
			break;
		// 点击了注册文字
		case R.id.login_tv_regist:
			startActivity(new Intent(this, RegistActivity.class));
			break;
		// 点击了忘记密码文字
		case R.id.login_tv_forget_password:
			startActivity(new Intent(this, RegistActivity.class));
			break;
		default:
			break;
		}

	}

	@Override
	public void getResultData(boolean isOk, String result, int Tag) {
		// 登录成功
		if (Tag == 0 && isOk) {
			try {
				// 获取用户名和密码
				JSONObject dataResult = new JSONObject(result);
				String mobile = dataResult.getString("mobile");
				String password = dataResult.getString("password");
				// 在内存中创建用户对象
				UserLoginInfo userLoginInfo = UserLoginInfo.getUserLoginInfo();
				userLoginInfo.setPhoneNumber(mobile);
				userLoginInfo.setPassword(password);
				// 跳转到中心页面
				startActivity(new Intent(this, CenterActivity.class));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

}
