package com.itjfr.jfr.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itjfr.jfr.R;

public class MarketFragment extends BaseFragment {
	private TextView gpstest;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		//View view = inflater.inflate(R.layout.fragment_market, null);
		View view = inflater.inflate(R.layout.fragment_setting, null);

		return view;
	}
}
