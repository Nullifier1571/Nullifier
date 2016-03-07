package com.itjfr.jfr.activity;

import com.itjfr.jfr.R;
import com.itjfr.jfr.utils.SharePrefaceTool;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 昵称修改 type 1 个人简介 type 2
 * 
 * @author Nullifier
 * 
 */
public class InputActivity extends Activity implements OnClickListener {
	// 输入文本框
	@ViewInject(R.id.input_et)
	private EditText input_et;
	// 提交按钮
	@ViewInject(R.id.input_commit)
	private Button input_commit;
	// 顶部条目标题
	@ViewInject(R.id.menu_title)
	private TextView menu_title;

	private int type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input);
		ViewUtils.inject(this);
		type = getIntent().getExtras().getInt("type");
		input_commit.setOnClickListener(this);
		switch (type) {
		case 0:
			String olderNickname = SharePrefaceTool.readout(this, "nickname");
			input_et.setText(olderNickname);
			input_et.setSelection(olderNickname.length());
			menu_title.setText("昵称修改");
			break;

		case 1:
			String olderProfile = SharePrefaceTool.readout(this, "profile");
			input_et.setText(olderProfile);
			input_et.setSelection(olderProfile.length());
			menu_title.setText("简介修改");
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		String string = input_et.getText().toString();
		// 如果用户什么都没写，则还是把原来的字段传回去
		if (TextUtils.isEmpty(string)) {
			switch (type) {
			case 0:
				string = SharePrefaceTool.readout(this, "nickname");
				break;
			case 1:
				string = SharePrefaceTool.readout(this, "profile");
				break;

			default:
				break;
			}
		}
		Intent intent = new Intent();
		intent.putExtra("result", string);
		setResult(0, intent);
		finish();
	}
}
