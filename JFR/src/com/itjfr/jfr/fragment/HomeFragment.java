package com.itjfr.jfr.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.itjfr.jfr.R;
import com.itjfr.jfr.adapter.GuidanceAdapter;
import com.itjfr.jfr.adapter.NewsAdapter;
import com.itjfr.jfr.config.Config;
import com.itjfr.jfr.domain.GuidanceData;
import com.itjfr.jfr.domain.NewsInfo;
import com.itjfr.jfr.domain.UserLoginInfo;
import com.itjfr.jfr.utils.HttpTools;
import com.itjfr.jfr.utils.LogUtils;
import com.itjfr.jfr.utils.HttpTools.RequestNetListener;
import com.itjfr.jfr.utils.UITool;
import com.viewpagerindicator.CirclePageIndicator;

public class HomeFragment extends BaseFragment implements RequestNetListener {
	private ViewPager sc_viewpager;
	private GridView gv_feature;
	private ListView home_lv_new;
	private ViewPagerFragmentAdapter viewPagerAdapter;
	private CirclePageIndicator mIndicator;
	private HttpTools httpTools;
	private GuidanceAdapter featureAdapter;
	private NewsAdapter newsAdapter;
	private ArrayList<NewsInfo> list_News;
	private Gson gson;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_home, null);
		initView(view);
		initData();
		return view;
	}

	private void initView(View view) {
		sc_viewpager = (ViewPager) view.findViewById(R.id.sc_viewpager);
		mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
		gv_feature = (GridView) view.findViewById(R.id.gv_feature);
		home_lv_new = (ListView) view.findViewById(R.id.home_lv_new);

	}

	private void initData() {
		httpTools = HttpTools.getHttpToolsInstance();
		httpTools.setRequestNetListener(this);
		// 添加特色gridView适配器并加载数据
		List<GuidanceData> list = addFeature();
		featureAdapter = new GuidanceAdapter(list, getActivity());
		gv_feature.setAdapter(featureAdapter);
		gv_feature.setOnItemClickListener(featureAdapter);
		// 设置gridView的高度
		UITool.setListViewHeightBasedOnChildren(gv_feature, getActivity());

		// 设置顶部ViewPager的数据
		ArrayList<String> listImageUrl = new ArrayList<String>();
		listImageUrl.add("1");
		listImageUrl.add("2");
		listImageUrl.add("3");
		listImageUrl.add("4");
		viewPagerAdapter = new ViewPagerFragmentAdapter(getFragmentManager(),
				listImageUrl);
		sc_viewpager.setAdapter(viewPagerAdapter);
		// 关联指针
		mIndicator.setViewPager(sc_viewpager);
		mIndicator.setCurrentItem(viewPagerAdapter.getCount() - 1);

		gson = new Gson();
		// 初始化新闻集合
		list_News = new ArrayList<NewsInfo>();

		// 发起请求获取新闻数据
		Bundle bundle = new Bundle();
		bundle.putString("Page", "1");
		bundle.putString("token", UserLoginInfo.getUserLoginInfo().getToken());
		httpTools.doPost(bundle, Config.HOME_NEWS, 0);
	}

	private List<GuidanceData> addFeature() {
		List<GuidanceData> list = new ArrayList<GuidanceData>();

		GuidanceData guidanceData = new GuidanceData();
		guidanceData.setTitle("新闻");
		guidanceData.setUrl("");
		guidanceData.setImageUrl("");
		guidanceData.setImageResource(R.drawable.seller_1haodian);
		list.add(guidanceData);

		GuidanceData guidanceData1 = new GuidanceData();
		guidanceData1.setTitle("开心一刻");
		guidanceData1.setUrl("");
		guidanceData1.setImageUrl("");
		guidanceData1.setImageResource(R.drawable.seller_baidu);
		list.add(guidanceData1);

		GuidanceData guidanceData2 = new GuidanceData();
		guidanceData2.setTitle("美图");
		guidanceData2.setUrl("");
		guidanceData2.setImageUrl("");
		guidanceData2.setImageResource(R.drawable.seller_suning);
		list.add(guidanceData2);

		GuidanceData guidanceData3 = new GuidanceData();
		guidanceData3.setTitle("免费试用");
		guidanceData3.setUrl("");
		guidanceData3.setImageUrl("");
		guidanceData3.setImageResource(R.drawable.seller_dangdang);
		list.add(guidanceData3);

		GuidanceData guidanceData4 = new GuidanceData();
		guidanceData4.setTitle("积分兑换");
		guidanceData4.setUrl("");
		guidanceData4.setImageUrl("");
		guidanceData4.setImageResource(R.drawable.seller_guomei);
		list.add(guidanceData4);
		return list;
	}

	@Override
	public void getResultData(boolean isOk, int errorCode, String result,
			int Tag) {
		switch (Tag) {
		case 0:
			try {
				JSONArray jsonArray = new JSONArray(result);
				// 每次使用前先清空集合
				list_News.clear();
				for (int i = 0; i < jsonArray.length(); i++) {
					NewsInfo newsInfo = gson.fromJson(jsonArray.getString(i),
							NewsInfo.class);
					list_News.add(newsInfo);
				}
				// 如果数据请求成功则创建adapter加载数据
				if (newsAdapter == null) {
					newsAdapter = new NewsAdapter(list_News, getActivity());
					home_lv_new.setAdapter(newsAdapter);
					UITool.setListViewHeightBasedOnChildren(home_lv_new);
					home_lv_new.setOnItemClickListener(newsAdapter);
				} else {
					newsAdapter.refresh(list_News);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void getDownLoadFile(boolean isOk, File file, int Tag) {
		// TODO Auto-generated method stub

	}

}
