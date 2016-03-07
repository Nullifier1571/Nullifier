package com.itjfr.jfr.utils;

import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 本类为Http请求的工具类
 * 
 * @author Nullifier
 * 
 */
public class HttpTools {

	private static HttpTools httpTools;
	private static HttpUtils httpUtils;
	private RequestNetListener listener;

	private HttpTools() {

	}

	/**
	 * 获取网络请求管理类
	 * 
	 * @return
	 */
	public synchronized static HttpTools getHttpToolsInstance() {
		if (httpTools == null) {
			httpTools = new HttpTools();
			httpUtils = new HttpUtils(5000);
			httpUtils.configCurrentHttpCacheExpiry(5000); // 设置缓存5秒,5秒内直接返回上次成功请求的结果。
		}
		return httpTools;

	}

	/**
	 * 设置请求结果的回调监听对象
	 * 
	 * @param listener
	 */
	public void setRequestNetListener(RequestNetListener listener) {

		this.listener = listener;
	}

	/**
	 * 发起Get请求
	 * 
	 * @param requestArgs
	 *            请求参数Bundle
	 * @param requestUrl
	 *            请求地址
	 * @param Tag
	 *            该条请求的标记
	 */
	public void doGet(Bundle requestArgs, String requestUrl, final int Tag) {
		String param = "";
		// 获取参数bundle并进行解析
		Set<String> keySet = requestArgs.keySet();
		Iterator<String> it = keySet.iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = requestArgs.getString(key);
			param = param + "&&" + key + "=" + value;
		}
		// 拼接请求参数
		String url = requestUrl + "?" + param.substring(2, param.length());
		LogUtils.e("请求地址------------------" + url);
		// 发起请求
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
			// 请求成功后的回调
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				LogUtils.e("请求成功------------------" + responseInfo.result);
				try {
					// 请求成功后解析数据判断结果是否正确
					JSONObject json = new JSONObject(responseInfo.result);
					// json结果标记
					boolean isOk = json.getBoolean("ok");
					// json结果消息
					String msg = json.getString("msg");
					// json结果数据
					String data = json.getString("data");
					int error_code = json.getInt("error_code");
					if (!isOk) {
						// 回调结果
						listener.getResultData(isOk, error_code, msg, Tag);
					} else {
						listener.getResultData(isOk, error_code, data, Tag);
					}

				} catch (JSONException e) {
					// json解析异常
					listener.getResultData(false, 100,
							"json解析异常,行数72" + e.getMessage(), Tag);
					e.printStackTrace();
				}

			}

			// 网络请求失败
			@Override
			public void onFailure(HttpException error, String msg) {
				listener.getResultData(false, 100, msg, Tag);
				LogUtils.e("请求失败------------------" + msg);
			}
		});
	}

	/**
	 * 
	 * 发起Post请求（带文件）
	 * 
	 * @param requestArgs
	 *            请求参数Bundle
	 * @param requestUrl
	 *            请求地址
	 * @param fileRequestName
	 *            文件名字
	 * @param file
	 *            文件对象
	 * @param Tag
	 *            该条请求的标记
	 */
	public void doPost(Bundle requestArgs, String requestUrl,
			String fileRequestName, File file, final int Tag) {
		RequestParams requestParams = new RequestParams();
		Set<String> keySet = requestArgs.keySet();
		Iterator<String> it = keySet.iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = requestArgs.getString(key);
			requestParams.addBodyParameter(key, value);
		}
		// 当发送的数据是文件的时候必须加上MIME_TYPE以向服务器标识文件的类型，否则可能服务器类型解析失败错误
		requestParams.addBodyParameter(fileRequestName, file, "image/png");
		LogUtils.e("请求地址------------------" + requestUrl);
		httpUtils.send(HttpMethod.POST, requestUrl, requestParams,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						LogUtils.e("请求成功------------------"
								+ responseInfo.result);
						try {
							JSONObject json = new JSONObject(
									responseInfo.result);
							boolean isOk = json.getBoolean("ok");
							String msg = json.getString("msg");
							String data = json.getString("data");
							int error_code = json.getInt("error_code");
							if (!isOk) {
								listener.getResultData(isOk, error_code, msg,
										Tag);
							} else {
								listener.getResultData(isOk, error_code, data,
										Tag);
							}

						} catch (JSONException e) {
							listener.getResultData(false, 100, "json解析异常,行数117"
									+ e.getMessage(), Tag);
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						listener.getResultData(false, 100, msg, Tag);
						LogUtils.e("请求失败------------------" + msg);
					}
				});
	}

	public static void uploadFile(String url, String fileName, File file) {
		try {
			URL url2 = new URL(url);
			HttpURLConnection openConnection = (HttpURLConnection) url2
					.openConnection();
			openConnection.setRequestMethod("POST");
			openConnection.setDoOutput(true);
			OutputStream outputStream = openConnection.getOutputStream();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 发起Post请求（不带文件）
	 * 
	 * @param requestArgs
	 *            请求参数Bundle
	 * @param requestUrl
	 *            请求地址
	 * @param Tag
	 *            该条请求的标记
	 */
	public void doPost(Bundle requestArgs, String requestUrl, final int Tag) {
		RequestParams requestParams = new RequestParams();
		Set<String> keySet = requestArgs.keySet();
		Iterator<String> it = keySet.iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = requestArgs.getString(key);
			requestParams.addBodyParameter(key, value);
		}

		LogUtils.e("请求地址------------------" + requestUrl);
		httpUtils.send(HttpMethod.POST, requestUrl, requestParams,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						DefaultHttpClient httpClient = (DefaultHttpClient) httpUtils
								.getHttpClient();

						CookieStore cookieStore = httpClient.getCookieStore();
						List<Cookie> cookies = cookieStore.getCookies();
						String aa = null;
						for (int i = 0; i < cookies.size(); i++) {
							if ("JSESSIONID".equals(cookies.get(i).getName())) {
								aa = cookies.get(i).getValue();
								break;
							}
						}
						LogUtils.e("比较sessionid" + aa);
						LogUtils.e("请求成功------------------"
								+ responseInfo.result);
						try {
							JSONObject json = new JSONObject(
									responseInfo.result);
							boolean isOk = json.getBoolean("ok");
							String msg = json.getString("msg");
							String data = json.getString("data");
							int error_code = json.getInt("error_code");
							if (!isOk) {
								listener.getResultData(isOk, error_code, msg,
										Tag);
							} else {
								listener.getResultData(isOk, error_code, data,
										Tag);
							}

						} catch (JSONException e) {
							listener.getResultData(false, 100, "json解析异常,行数160"
									+ responseInfo.result, Tag);
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						LogUtils.e("请求失败------------------" + msg);
						listener.getResultData(false, 100, msg, Tag);

					}
				});
	}

	/**
	 * 从网络上获取图片
	 * 
	 * @param url
	 *            图片的URL
	 * @param localPath
	 *            在本地存储的位置
	 */
	public void downLoadFile(String requestUrl, String localPath, final int Tag) {

		LogUtils.e("请求地址------------------" + requestUrl);
		httpUtils.download(requestUrl, localPath, new RequestCallBack<File>() {

			@Override
			public void onSuccess(ResponseInfo<File> responseInfo) {
				File resultFile = responseInfo.result;
				listener.getDownLoadFile(true, resultFile, Tag);
				LogUtils.e("请求成功获取到文件------------------" + resultFile);

			}

			@Override
			public void onFailure(HttpException error, String msg) {
				LogUtils.e("请求失败------------------" + msg);
				listener.getResultData(false,100, msg, Tag);
			}
		});

	}

	/**
	 * 网络请求回调接口
	 * 
	 * @author Nullifier
	 * 
	 */
	public interface RequestNetListener {
		/**
		 * 请求结果
		 * 
		 * @param isOk
		 *            是否请求成功
		 * @param result
		 *            请求数据，如果失败则为失败的原因
		 */
		public void getResultData(boolean isOk, int errorCode, String result,
				int Tag);

		public void getDownLoadFile(boolean isOk, File file, int Tag);

	}
}
