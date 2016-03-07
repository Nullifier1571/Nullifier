package com.itjfr.jfr.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.itjfr.jfr.R;
import com.itjfr.jfr.adapter.GuidanceAdapter;
import com.itjfr.jfr.config.Config;
import com.itjfr.jfr.domain.GuidanceData;
import com.itjfr.jfr.domain.UserLoginInfo;
import com.itjfr.jfr.utils.HttpTools;
import com.itjfr.jfr.utils.LogUtils;
import com.itjfr.jfr.utils.HttpTools.RequestNetListener;

public class GuidanceFragment extends BaseFragment implements
		RequestNetListener {
	private GridView guidance_gv;
	private TextView menu_title;
	private HttpTools httpTools;
	private GuidanceAdapter guidanceAdapter;
	private List<GuidanceData> list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_guidance, null);
		initView(view);
		initData();
		return view;
	}

	private void initData() {
		httpTools = HttpTools.getHttpToolsInstance();
		httpTools.setRequestNetListener(this);

	}

	private void initView(View view) {
		guidance_gv = (GridView) view.findViewById(R.id.guidance_gv);
		menu_title = (TextView) view.findViewById(R.id.menu_title);
		menu_title.setText("导航");
		if (guidanceAdapter == null) {
			list = addSeller();
			// result转换为list
			guidanceAdapter = new GuidanceAdapter(list, getActivity());
			guidance_gv.setAdapter(guidanceAdapter);
			// guidanceAdapter.refresh(null);
			LogUtils.e("GuidanceFragment initView()方法执行guidanceAdapter为空重新创建"
					+ "list的大小" + list.size());
		} else {
			LogUtils.e("GuidanceFragment initView()方法执行guidanceAdapter不为空"
					+ "list的大小" + list.size() + "gridView对象是否为空" + guidance_gv);
			guidanceAdapter.refresh(list);
		}
		LogUtils.e("GuidanceFragment initData()方法执行" + list.size());
	}

	@Override
	public void onStart() {

		super.onStart();
	}

	@Override
	public void getResultData(boolean isOk, int errorCode, String result,
			int Tag) {
		if (guidanceAdapter == null) {
			List<GuidanceData> list = addSeller();
			// result转换为list
			guidanceAdapter = new GuidanceAdapter(list, getActivity());
			guidance_gv.setAdapter(guidanceAdapter);
		} else {
			guidanceAdapter.refresh(null);
		}

	}

	private List<GuidanceData> addSeller() {
		List<GuidanceData> list = new ArrayList<GuidanceData>();
		GuidanceData guidanceData = new GuidanceData();
		guidanceData.setTitle("1号店");
		guidanceData.setUrl("");
		guidanceData.setImageUrl("");
		guidanceData.setImageResource(R.drawable.seller_1haodian);
		list.add(guidanceData);

		GuidanceData guidanceData1 = new GuidanceData();
		guidanceData1.setTitle("百度");
		guidanceData1.setUrl("");
		guidanceData1.setImageUrl("");
		guidanceData1.setImageResource(R.drawable.seller_baidu);
		list.add(guidanceData1);

		GuidanceData guidanceData2 = new GuidanceData();
		guidanceData2.setTitle("苏宁易购");
		guidanceData2.setUrl("");
		guidanceData2.setImageUrl("");
		guidanceData2.setImageResource(R.drawable.seller_suning);
		list.add(guidanceData2);

		GuidanceData guidanceData3 = new GuidanceData();
		guidanceData3.setTitle("当当");
		guidanceData3.setUrl("");
		guidanceData3.setImageUrl("");
		guidanceData3.setImageResource(R.drawable.seller_dangdang);
		list.add(guidanceData3);

		GuidanceData guidanceData4 = new GuidanceData();
		guidanceData4.setTitle("国美");
		guidanceData4.setUrl("");
		guidanceData4.setImageUrl("");
		guidanceData4.setImageResource(R.drawable.seller_guomei);
		list.add(guidanceData4);

		GuidanceData guidanceData5 = new GuidanceData();
		guidanceData5.setTitle("京东");
		guidanceData5.setUrl("");
		guidanceData5.setImageUrl("");
		guidanceData5.setImageResource(R.drawable.seller_jingdong);
		list.add(guidanceData5);

		GuidanceData guidanceData6 = new GuidanceData();
		guidanceData6.setTitle("美丽说");
		guidanceData6.setUrl("");
		guidanceData6.setImageUrl("");
		guidanceData6.setImageResource(R.drawable.seller_meilishuo);
		list.add(guidanceData6);

		GuidanceData guidanceData7 = new GuidanceData();
		guidanceData7.setTitle("美团");
		guidanceData7.setUrl("");
		guidanceData7.setImageUrl("");
		guidanceData7.setImageResource(R.drawable.seller_meituan);
		list.add(guidanceData7);

		GuidanceData guidanceData8 = new GuidanceData();
		guidanceData8.setTitle("蘑菇街");
		guidanceData8.setUrl("");
		guidanceData8.setImageUrl("");
		guidanceData8.setImageResource(R.drawable.seller_mogujie);
		list.add(guidanceData8);

		GuidanceData guidanceData9 = new GuidanceData();
		guidanceData9.setTitle("百度糯米");
		guidanceData9.setUrl("");
		guidanceData9.setImageUrl("");
		guidanceData9.setImageResource(R.drawable.seller_baidunuomi);
		list.add(guidanceData9);

		GuidanceData guidanceData10 = new GuidanceData();
		guidanceData10.setTitle("淘宝");
		guidanceData10.setUrl("");
		guidanceData10.setImageUrl("");
		guidanceData10.setImageResource(R.drawable.seller_taobao);
		list.add(guidanceData10);

		GuidanceData guidanceData11 = new GuidanceData();
		guidanceData11.setTitle("腾讯");
		guidanceData11.setUrl("");
		guidanceData11.setImageUrl("");
		guidanceData11.setImageResource(R.drawable.seller_tengxu);
		list.add(guidanceData11);

		GuidanceData guidanceData12 = new GuidanceData();
		guidanceData12.setTitle("头条");
		guidanceData12.setUrl("");
		guidanceData12.setImageUrl("");
		guidanceData12.setImageResource(R.drawable.seller_toutiao);
		list.add(guidanceData12);

		GuidanceData guidanceData13 = new GuidanceData();
		guidanceData13.setTitle("唯品会");
		guidanceData13.setUrl("");
		guidanceData13.setImageUrl("");
		guidanceData13.setImageResource(R.drawable.seller_weipinhui);
		list.add(guidanceData13);

		GuidanceData guidanceData14 = new GuidanceData();
		guidanceData14.setTitle("窝窝团");
		guidanceData14.setUrl("");
		guidanceData14.setImageUrl("");
		guidanceData14.setImageResource(R.drawable.seller_wo);
		list.add(guidanceData14);

		GuidanceData guidanceData15 = new GuidanceData();
		guidanceData15.setTitle("亚马逊");
		guidanceData15.setUrl("");
		guidanceData15.setImageUrl("");
		guidanceData15.setImageResource(R.drawable.seller_yamas);
		list.add(guidanceData15);

		return list;
	}

	@Override
	public void getDownLoadFile(boolean isOk, File file, int Tag) {
		// TODO Auto-generated method stub

	}
}
