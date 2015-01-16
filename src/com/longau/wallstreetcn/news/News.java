package com.longau.wallstreetcn.news;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale.Category;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.longau.wallstreetcn.bean.CategorySet;
import com.longau.wallstreetcn.bean.CountryBean;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class News {
	private static String url1 = "http://api.wallstreetcn.com/v2/livenews?&page=";
	private static String DUrl = "";

	public static void main(String[] args){
		System.out.println("线程启动");
		String source = "wangstreetcn";
		// 链接数据库
		try {
			Mongo mongo = new Mongo("localhost", 27017);
			DB db = mongo.getDB("test");
			DBCollection collection = db.getCollection("wangstreetcn");   //?
			db.requestStart();
			// 调用抓取的方法获取内容
			for (int i = 1; i < 3100; i++) {   
				String stri = i + "";
				DUrl = url1 + stri ;     //?

				List<CountryBean> info = CountryNews.getTodayTemperatureInfo(DUrl);
			
				for (CountryBean htmlbean : info) {
					System.out.println(DUrl);
					BasicDBObject dbObject = new BasicDBObject();
					String content = new String(
							(htmlbean.getContent()).getBytes(), "UTF-8");
					String content1 = News.unicodeToString(content);
					System.out.println(content1);
					dbObject.put("content", content1); // 具体内容

					Date date = new Date();
					DateFormat time = DateFormat.getDateTimeInstance();
					String time_str = time.format(date);

					CategorySet category = new CategorySet();
					String district = category.setDis(htmlbean.getCategorySet());           //获取到对应对setid
					String property = category.setPro(htmlbean.getCategorySet()); 
					String centralbank = category.setCen(htmlbean.getCategorySet()); 
					
					dbObject.put("createdtime", time_str); // 创建时间
					dbObject.put("source", source); // 信息来源
					dbObject.put("district", district); // 所属地区
					dbObject.put("property", property);   //资产类别
					dbObject.put("centralbank", centralbank);   //资产类别
					dbObject.put("type", htmlbean.getType());

					collection.insert(dbObject);
				}
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String unicodeToString(String str) {
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		return str;
	}

	
}
