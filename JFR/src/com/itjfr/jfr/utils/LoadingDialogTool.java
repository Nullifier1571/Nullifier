package com.itjfr.jfr.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;

import com.itjfr.jfr.R;

public class LoadingDialogTool {
	private AlertDialog dlg;

	public LoadingDialogTool(Activity activity) {
		
		WindowManager windowManager = activity.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		dlg = new AlertDialog.Builder(activity).create();
		dlg.setCancelable(false);
		
		
		WindowManager.LayoutParams lp = dlg.getWindow().getAttributes();
		lp.width = (int)(display.getWidth()); //设置宽度
		
		//lp.width = 300; // 宽度
        lp.height = (int)(display.getHeight()); // 高度
        lp.alpha = 0.7f; // 透明度
		dlg.getWindow().setAttributes(lp);
		View view = View.inflate(activity, R.layout.item_alert_style, null);
		view.measure(0, 0);
		view.setLayoutParams(new LayoutParams(view.getMeasuredWidth(), view.getMeasuredHeight()));
		dlg.setView(view);
	}

	public void showDialog() {
		dlg.show();
	}

	public void dismissDialog() {
		dlg.dismiss();
	}
}
