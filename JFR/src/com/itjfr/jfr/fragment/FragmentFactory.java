package com.itjfr.jfr.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * 本工厂管理fragment保证所有的fragment为单例
 * 
 * @author Nullifier
 * 
 */
public class FragmentFactory {
	private static Fragment settingFragment;
	private static HomeFragment homeFragment;
	private static GuidanceFragment guidanceFragment;
	private static MarketFragment marketFragment;
	private static MeFragment meFragment;
	private static PersonDetailFragment personDetailFragment;
	private static ScoreFragment scoreFragment;
	private static ImageFragment imageFragment;
	// 当前fragment缓存
	private static Fragment cacheFragment;

	public static Fragment getNowFragment() {
		return cacheFragment;
	}

	/**
	 * 切换fragment工具方法 由于fragment经常切换如果使用replace方法则会造成fragment重新加载，所以只能hide/show
	 * 
	 * @param target
	 *            目标切换的fragment
	 * @param resourceId
	 *            要替换的资源ID
	 * @param fagmentManager
	 *            fragment管理者
	 */
	public static void switchPager(Fragment target, int resourceId,
			FragmentActivity activity) {
		FragmentManager fagmentManager = activity.getSupportFragmentManager();
		// 缓存cache第一次进来是空的要单独处理
		if (cacheFragment == null) {
			// 第一次进来要切换的fragment作为当前fragment加入到transaction中并显示
			fagmentManager.beginTransaction().add(resourceId, target)
					.show(target).commit();
			// 将此fragment赋值给缓存fragment，停止代码执行
			cacheFragment = target;
			return;
		}
		// 如果目标fragment与当前fragment一样则直接返回不执行
		if (cacheFragment == target) {
			return;
		}
		// 如果目标frragment没有添加进来则添加进transaction中
		if (!target.isAdded()) {
			fagmentManager.beginTransaction().add(resourceId, target).commit();
		}
		// 隐藏当前的fragmeny显示目标的fragment
		fagmentManager.beginTransaction().hide(cacheFragment).show(target)
				.commit();
		// 将目标fragment复制给缓存标记
		cacheFragment = target;
	}

	/**
	 * 获取fragment
	 * 
	 * @param fragment类型
	 *            FragmentType枚举
	 * @return fragment
	 */
	public static Fragment getFragment(FragmentType type) {
		switch (type) {
		case GUIDANCE:
			if (guidanceFragment == null) {
				guidanceFragment = new GuidanceFragment();
			}
			return guidanceFragment;

		case HOME:
			if (homeFragment == null) {
				homeFragment = new HomeFragment();
			}
			return homeFragment;

		case MARKET:
			if (marketFragment == null) {
				marketFragment = new MarketFragment();
			}
			return marketFragment;

		case ME:
			if (meFragment == null) {
				meFragment = new MeFragment();
			}
			return meFragment;

		case PERSONDETAIL:
			if (personDetailFragment == null) {
				personDetailFragment = new PersonDetailFragment();
			}
			return personDetailFragment;

		case SCORE:
			if (scoreFragment == null) {
				scoreFragment = new ScoreFragment();
			}
			return scoreFragment;

		case SETTING:
			if (settingFragment == null) {
				settingFragment = new SettingFragment();
			}
			return settingFragment;
		case IMAGE:
			if (imageFragment == null) {
				imageFragment = new ImageFragment();
			}
			return imageFragment;

		default:
			break;
		}

		return null;
	}

	/**
	 * 通过网络地址集合获取fragment集合，集合不唯一
	 * 
	 * @param type
	 *            类型 一般为IMAGE
	 * @param urlList
	 *            图片集合
	 * @return
	 */
	public static List<Fragment> getFragment(FragmentType type,
			List<String> urlList) {
		switch (type) {
		case IMAGE:
			ArrayList<Fragment> listFragment = new ArrayList<Fragment>();
			if (urlList != null && urlList.size() > 0) {
				for (int i = 0; i < urlList.size(); i++) {
					listFragment.add(new ImageFragment().setIamgeUrl(urlList
							.get(i)));
				}
			}
			return listFragment;

		default:
			break;
		}

		return null;
	}

	/**
	 * 枚举
	 * 
	 * @author Nullifier
	 * 
	 */
	public static enum FragmentType {
		/**
		 * 导航
		 */
		GUIDANCE,
		/**
		 * 主页
		 */
		HOME,
		/**
		 * 市场
		 */
		MARKET,
		/**
		 * 我
		 */
		ME,
		/**
		 * 个人详情
		 */
		PERSONDETAIL,
		/**
		 * 积分
		 */
		SCORE,
		/**
		 * 设置
		 */
		SETTING,
		/**
		 * 图像
		 */
		IMAGE;
	}

}
