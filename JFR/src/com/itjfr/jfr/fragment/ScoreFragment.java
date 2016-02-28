package com.itjfr.jfr.fragment;

import java.util.ArrayList;

import com.itjfr.jfr.R;
import com.viewpagerindicator.CirclePageIndicator;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

/**
 * 分数页面
 * @author Nullifier
 *
 */
public class ScoreFragment extends BaseFragment {
	private GridView score_gv_exchange;
	private ViewPager sc_viewpager;
	private TextView tv_score;
	private TextView score_tv_message;
	private CirclePageIndicator mIndicator;
	// private TestFragmentAdapter mAdapter;
	//private ViewPagerFragmentAdapter myTestAdapter;
	private ViewPagerFragmentAdapter viewPagerAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_score, null);
		initView(view);

		return view;
	}

	private void initView(View view) {
		score_gv_exchange = (GridView) view
				.findViewById(R.id.score_gv_exchange);
		sc_viewpager = (ViewPager) view.findViewById(R.id.sc_viewpager);
		tv_score = (TextView) view.findViewById(R.id.tv_score);
		score_tv_message = (TextView) view.findViewById(R.id.score_tv_message);
		ArrayList<String> listImageUrl = new ArrayList<String>();
		listImageUrl.add("1");
		listImageUrl.add("2");
		listImageUrl.add("3");
		listImageUrl.add("4");
		viewPagerAdapter = new ViewPagerFragmentAdapter(getFragmentManager(),listImageUrl);	
		sc_viewpager.setAdapter(viewPagerAdapter);

		mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
		mIndicator.setViewPager(sc_viewpager);
		mIndicator.setCurrentItem(viewPagerAdapter.getCount() - 1);
		
	}
}
