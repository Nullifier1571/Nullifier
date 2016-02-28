package com.itjfr.jfr.fragment;

import com.itjfr.jfr.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment {
	//public FragmentManager fagmentManager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public void switchPage(Fragment fagment) {
		FragmentFactory.switchPager(fagment, R.id.center_pager, getActivity());
	}
}
