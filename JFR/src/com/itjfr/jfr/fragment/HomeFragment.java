package com.itjfr.jfr.fragment;

import com.itjfr.jfr.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends BaseFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		View view=inflater.inflate(R.layout.fragment_home, null);
		
		return view;
	}
	
	
}
