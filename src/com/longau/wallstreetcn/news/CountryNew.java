package com.longau.wallstreetcn.news;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.longau.wallstreetcn.bean.CountryBean;

public class CountryNew {
	private static String urlRegex = ".*\"type\":\"(.*?)\".*\"contentHtml\":\"<p>(.*?)<\\\\/p>\".*\"categorySet\":\"(.*?)\".*";
	private static String encode="UTF-8";
	private static String httpRequest(String requestUrl) { // requestUrl
      // 请求地址，返回html字符串
		StringBuffer buffer = null;
		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		InputStream inputStream = null;
		HttpURLConnection httpUrlConn = null;
		try {
			// 建立get请求
			URL url = new URL(requestUrl);
			httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoInput(true);
			// 获取输入流
			inputStream = httpUrlConn.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream,encode );
			bufferedReader = new BufferedReader(inputStreamReader);
			
			// 从输入流获取结果
			buffer = new StringBuffer();
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				str=new String(str.getBytes(),"UTF-8");
				buffer.append(str);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (httpUrlConn != null) {
				httpUrlConn.disconnect();
			}
		}
		return buffer.toString();
	}

	// 过滤掉无用的信息  (可修改部分)
	private static List<CountryBean> htmlFiter(String html) {

		List<CountryBean> list = new ArrayList<>();
		// 查找目标
		Pattern p = Pattern.compile(urlRegex);
		Matcher m = p.matcher(html);
		while (m.find()) {
			CountryBean bean =new CountryBean();
			bean.setContent(m.group(2));
			bean.setType(m.group(1));
			bean.setCategorySet(m.group(3));
			
			list.add(bean);
		}
		return list;
	}

	public static List<CountryBean> getTodayTemperatureInfo(String DUrl) {
		String html = httpRequest(DUrl);
		System.out.println(html);
		List<CountryBean> resultList = htmlFiter(html);
		return resultList;
	}

	public static void main(String[] args) {
		List<CountryBean> info = getTodayTemperatureInfo("http://api.wallstreetcn.com/v2/livenews?limit=60&callback=jQuery21307816704266474942_1420351706293&page=1");
		for (CountryBean htmlbean : info) {
			System.out.println(htmlbean.getContent());
			System.out.println(info.size());
		}
	}

}
