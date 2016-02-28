package com.itjfr.jfr.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itjfr.jfr.R;
import com.itjfr.jfr.config.Config;
import com.itjfr.jfr.splash.SplashFirst;
import com.itjfr.jfr.utils.HttpTools;
import com.itjfr.jfr.utils.HttpTools.RequestNetListener;
import com.itjfr.jfr.utils.InputCheckTool;
import com.itjfr.jfr.utils.LogUtils;
import com.itjfr.jfr.utils.SharePrefaceTool;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
/**
 * 注册页面
 * @author Nullifier
 *
 */
public class RegistActivity extends Activity implements OnClickListener,
		RequestNetListener {
	@ViewInject(R.id.regist_bt_regist)
	private Button regist_bt_regist;
	@ViewInject(R.id.regist_bt_get_token)
	private Button regist_bt_get_token;
	// 顶部条目标题
	@ViewInject(R.id.menu_title)
	private TextView menu_title;
	// 手机号
	@ViewInject(R.id.regist_ev_phone)
	private EditText regist_ev_phone;
	// 密码
	@ViewInject(R.id.regist_ev_password)
	private EditText regist_ev_password;
	// 验证码
	@ViewInject(R.id.regist_ev_token)
	private EditText regist_ev_token;
	// 协议
	@ViewInject(R.id.regist_cb_accept_protocol)
	private CheckBox regist_cb_accept_protocol;
	
	// 后退键
	@ViewInject(R.id.menu_back)
	private ImageView menu_back;
	

	private HttpTools httpTools;

	private Handler handler;

	private int TIMER = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		ViewUtils.inject(this);
		menu_title.setText("注册");
		menu_back.setVisibility(View.VISIBLE);
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// 计时器不到0则循环发送消息
				if (msg.what - 1 != -1) {
					handler.sendEmptyMessageDelayed(msg.what - 1, 1000);
					regist_bt_get_token.setText(msg.what - 1 + "S");
				} else {
					// 计时器到0则移除消息
					handler.removeMessages(1);
					// 按钮可点击
					regist_bt_get_token.setEnabled(true);
					regist_bt_get_token.setText("获取验证码");
				}

				super.handleMessage(msg);
			}
		};
		regist_bt_regist.setOnClickListener(this);
		regist_bt_get_token.setOnClickListener(this);
		menu_back.setOnClickListener(this);
		httpTools = HttpTools.getHttpToolsInstance();
		httpTools.setRequestNetListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		// 点击了注册按钮
		case R.id.regist_bt_regist:
			String phone = regist_ev_phone.getText().toString();
			String password = regist_ev_password.getText().toString();
			String token = regist_ev_token.getText().toString();
			// 检查是否合法
			if (InputCheckTool.isPhoneNumber(phone)
					&& InputCheckTool.isPassword(password)
					&& InputCheckTool.isToken(token)) {

				if (!regist_cb_accept_protocol.isChecked()) {
					LogUtils.toastMessage(this, "请阅读用户协议并勾选接受");
					return;
				}
				// 设置参数
				Bundle requestArgs = new Bundle();
				requestArgs.putString("mobile", phone);
				requestArgs.putString("code", token);
				requestArgs.putString("password", password);
				httpTools.doPost(requestArgs, Config.USER_REGIST, 1);
			} else {
				LogUtils.toastMessage(this, "请输入正确的手机号码");
			}

			break;
		// 点击了获取验证码按钮
		case R.id.regist_bt_get_token:
			String phoneNumber = regist_ev_phone.getText().toString();

			// 检查是否合法
			if (InputCheckTool.isPhoneNumber(phoneNumber)) {
				Bundle requestArgs = new Bundle();
				requestArgs.putString("mobile", phoneNumber);
				httpTools.doPost(requestArgs, Config.USER_REGIST_GET_TOKEN, 0);
				// 发送循环消息
				handler.sendEmptyMessage(TIMER);
				// 按钮不可点击
				regist_bt_get_token.setEnabled(false);
			} else {
				LogUtils.toastMessage(this, "请输入正确的手机号码");
			}
			break;
			
		case R.id.menu_back:
			finish();

		default:
			break;
		}

	}

	@Override
	public void getResultData(boolean isOk, String result, int Tag) {
		switch (Tag) {
		// 获取验证码
		case 0:
			if (!isOk) {
				LogUtils.toastMessage(this, "获取验证码失败，请稍候重试");
				return;
			}
			try {
				// 获取验证码并自动填写到验证码框内
				JSONObject dataResult = new JSONObject(result);
				String code = dataResult.getString("code");
				regist_ev_token.setText(code);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case 1:

			break;
		default:
			break;
		}

	}

}
