package com.itjfr.jfr.activity;

import com.itjfr.jfr.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
/**
 * 获取手机照片 未用
 * @author Nullifier
 *
 */
public class GetPhotoActivity extends Activity implements OnClickListener {
	@ViewInject(R.id.getphoto_iv_back)
	private ImageView getphoto_iv_back;

	@ViewInject(R.id.getphoto_bt_send)
	private Button getphoto_bt_send;

	@ViewInject(R.id.getphoto_gv_getphoto)
	private GridView getphoto_gv_getphoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_getphoto);
		ViewUtils.inject(this);
		getphoto_iv_back.setOnClickListener(this);
		getphoto_bt_send.setOnClickListener(this);
		getphoto_gv_getphoto.setOnItemClickListener(null);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.getphoto_iv_back:
			finish();
			break;

		case R.id.getphoto_bt_send:

			break;

		default:
			break;
		}

	}
}
