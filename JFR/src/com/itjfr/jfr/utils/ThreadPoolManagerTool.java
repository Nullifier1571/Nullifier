package com.itjfr.jfr.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池管理类 
 * @author Nullifier
 *
 */
public class ThreadPoolManagerTool {
	private static ThreadPoolManagerTool manager;
	private ExecutorService service;

	public ThreadPoolManagerTool() {
		// 获取手机的处理器个数 动态的开启线程
		int core = Runtime.getRuntime().availableProcessors();
		// 创建线程池
		service = Executors.newFixedThreadPool(core * 2);
	}

	// 单例模式获取管理着
	public static ThreadPoolManagerTool getInstance() {
		if (manager == null) {
			manager = new ThreadPoolManagerTool();
		}
		return manager;
	}

	// 在这里处理线程方法
	public void addTask(Runnable runnable) {
		service.submit(runnable);
	}
}
