package com.itjfr.jfr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.itjfr.jfr.utils.LogUtils;

import android.app.Application;
import android.os.Environment;

/**
 * 本类为全局application类 负责控制异常处理等功能
 * 
 * @author Nullifier
 * 
 */
public class JFRApplication extends Application {
	private MyUncaughtExceptionHandler myUncaughtExceptionHandler;

	@Override
	public void onCreate() {
		super.onCreate();
		// 创建异常捕获类，单例设计模式
		if(myUncaughtExceptionHandler==null){
			myUncaughtExceptionHandler = new MyUncaughtExceptionHandler();
		}
	}

	//异常类
	private class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

		@Override
		public void uncaughtException(Thread thread, Throwable ex) {
			try {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				LogUtils.e("检测到未经捕获的异常！！");
				//输出异常日志
				ex.printStackTrace();
				//创建文件流记录
				File file = new File(Environment.getExternalStorageDirectory(),"log.txt");
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(sw.toString().getBytes());
				fos.close();
				pw.close();
				sw.close();
			} catch (Exception e) {
				//异常记录失败
				e.printStackTrace();
				LogUtils.e("异常记录失败"+e.toString());
			}
			//杀死进程
			android.os.Process.killProcess(android.os.Process.myPid());
		}

	}
}
