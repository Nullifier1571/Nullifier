package com.itjfr.jfr.listener;

import android.content.DialogInterface;

import com.itjfr.jfr.view.WheelView;

public class SexWheelListener implements OnWheelChangedListener,DialogInterface.OnClickListener {

	private int newValue;
	private int oldValue;
	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		this.newValue=newValue;
		this.oldValue=oldValue;
		
	}
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}

}
