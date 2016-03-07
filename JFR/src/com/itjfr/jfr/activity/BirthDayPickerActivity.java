package com.itjfr.jfr.activity;

import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itjfr.jfr.R;
import com.itjfr.jfr.utils.LogUtils;
import com.itjfr.jfr.utils.SharePrefaceTool;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class BirthDayPickerActivity extends Activity implements
		OnDateChangedListener, OnClickListener {
	@ViewInject(R.id.date_picker)
	private DatePicker date_picker;

	@ViewInject(R.id.date_picker_commit)
	private Button date_picker_commit;
	// 顶部条目标题
	@ViewInject(R.id.menu_title)
	private TextView menu_title;

	private int year;
	private int monthOfYear;
	private int dayOfMonth;

	private Calendar calendar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_birthday_picker);
		ViewUtils.inject(this);
		menu_title.setText("生日修改");
		date_picker_commit.setOnClickListener(this);
		calendar = Calendar.getInstance();
		String birthday = SharePrefaceTool.readout(this, "birthday");
		
		if (!TextUtils.isEmpty(birthday)) {
			String[] split = birthday.split("-");

			int year = Integer.valueOf(split[0]);
			int monthOfYear = Integer.valueOf(split[1]);
			int dayOfMonth = Integer.valueOf(split[2]);
			date_picker.setCalendarViewShown(false);
			date_picker.init(year, monthOfYear, dayOfMonth, this);
		} else {
			Calendar calendar = Calendar.getInstance(Locale.CHINA);
			int year = calendar.get(Calendar.YEAR);
			int monthOfYear = calendar.get(Calendar.MONTH);
			int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
			date_picker.setCalendarViewShown(false);
			date_picker.init(year, monthOfYear, dayOfMonth, this);
		}

	}

	@Override
	public void onBackPressed() {

		Intent intent = new Intent();
		intent.putExtra("result", year + "-" + monthOfYear + "-" + dayOfMonth);
		setResult(0, intent);
		finish();
		super.onBackPressed();
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		this.year = year;
		this.monthOfYear = monthOfYear;
		this.dayOfMonth = dayOfMonth;
	}

	@Override
	public void onClick(View v) {

        //通过自定义控件AlertDialog实现
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_datepicker, null);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        //设置日期简略显示 否则详细显示 包括:星期周
        datePicker.setCalendarViewShown(false);
        //初始化当前日期
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 
                calendar.get(Calendar.DAY_OF_MONTH), null); 
        //设置date布局
        builder.setView(view);
        builder.setTitle("设置日期信息");
        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() { 
                private int mYear;
				private int mMonth;
				private int mDay;

				@Override
                public void onClick(DialogInterface dialog, int which) { 
                    //日期格式
                    StringBuffer sb = new StringBuffer(); 
                    sb.append(String.format("%d-%02d-%02d",  
                            datePicker.getYear(),  
                            datePicker.getMonth() + 1, 
                            datePicker.getDayOfMonth())); 
                    LogUtils.e("测试项目-------------------"+sb);
                    //赋值后面闹钟使用
                    mYear = datePicker.getYear();
                    mMonth = datePicker.getMonth();
                    mDay = datePicker.getDayOfMonth();
                    dialog.cancel(); 
                } 
        }); 
        builder.setNegativeButton("取  消", new DialogInterface.OnClickListener() { 
            @Override
            public void onClick(DialogInterface dialog, int which) { 
                dialog.cancel(); 
            }
        });
        builder.create().show();
		
	}
}
