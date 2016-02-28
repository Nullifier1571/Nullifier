package com.itjfr.jfr.fragment;

import com.itjfr.jfr.R;
import com.itjfr.jfr.fragment.FragmentFactory.FragmentType;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SettingFragment extends BaseFragment implements OnClickListener {
	private ImageView menu_back;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_setting, null);
		initView(view);
		return view;
	}

	private void initView(View view) {
		menu_back = (ImageView) view.findViewById(R.id.menu_back);
		menu_back.setVisibility(View.VISIBLE);
		menu_back.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu_back:
			switchPage(FragmentFactory.getFragment(FragmentType.ME));
			break;

		default:
			break;
		}
		
	}
}
