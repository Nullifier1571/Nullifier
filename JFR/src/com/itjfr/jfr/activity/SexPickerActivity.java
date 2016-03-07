package com.itjfr.jfr.activity;

import com.itjfr.jfr.R;
import com.itjfr.jfr.utils.SharePrefaceTool;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SexPickerActivity extends Activity implements
		OnCheckedChangeListener {
	@ViewInject(R.id.rg_sex)
	private RadioGroup rg_sex;
	private int temp=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sex_picker);
		ViewUtils.inject(this);
		rg_sex.setOnCheckedChangeListener(this);
		String sex = SharePrefaceTool.readout(this,"sex");
		if("0".equals(sex)){
			rg_sex.check(R.id.rb_no);
		}else if("1".equals(sex)){
			rg_sex.check(R.id.rb_male);
		}else if("2".equals(sex)){
			rg_sex.check(R.id.rb_female);
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_male:
			temp=1;
			break;
		case R.id.rb_female:
			temp=2;
			break;
		case R.id.rb_no:
			temp=0;
			break;
		default:
			break;
		}
	}
	@Override
	public void onBackPressed() {
		
		Intent intent = new Intent();
		intent.putExtra("result", temp+"");
		setResult(0, intent);
		finish();
		super.onBackPressed();
	}
}
