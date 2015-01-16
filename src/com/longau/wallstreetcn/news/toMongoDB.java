package com.longau.wallstreetcn.news;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.longau.wallstreetcn.bean.CountryBean;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class toMongoDB {
	private static String url1 = "http://api.wallstreetcn.com/v2/livenews?limit=60&cid[]=";
	private static String url2 = "&callback=jQuery21306012721447985994_1419925822457&page=";
	private static String url3 = "&_=1419925822459";
	private static String DUrl = "http://api.wallstreetcn.com/v2/livenews?limit=60&callback=jQuery21307816704266474942_1420351706293&page=2&_=1420351706296";

	public static void main(String[] args) throws UnsupportedEncodingException {
		String source = "wangstreetcn";
		// 链接数据库
		try {
			Mongo mongo = new Mongo("localhost", 27017);
			DB db = mongo.getDB("test");
			DBCollection collection = db.getCollection("wangstreetcn");
			db.requestStart();
			for (int c = 0; c <= 8; c++) {
				String country[] = { "中国", "美国", "欧元区", "日本", "英国", "澳洲",
						"加拿大", "瑞士", "其他地区" };
				int cnum = c + 9;
				// 调用抓取的方法获取内容
				for (int i = 1; i < 500; i++) {
					String stri = i + "";
					String strcnum = cnum + "";
					DUrl = url1 + strcnum + url2 + stri + url3;

					CountryNews news = new CountryNews();
					List<CountryBean> info = news.getTodayTemperatureInfo(DUrl);
					for (CountryBean htmlbean : info) {
						BasicDBObject dbObject = new BasicDBObject();
						String content = new String(
								(htmlbean.getContent()).getBytes(), "UTF-8");
						String content1 = toMongoDB.unicodeToString(content);
						System.out.println(content1);
						dbObject.put("content", content1); // 具体内容

						Date date = new Date();
						DateFormat time = DateFormat.getDateTimeInstance();
						String time_str = time.format(date);

						dbObject.put("createdtime", time_str); // 创建时间
						dbObject.put("source", source); // 信息来源
						dbObject.put("district", country[c]); // 所属
						dbObject.put("type", htmlbean.getType());

						collection.insert(dbObject);
					}
				}
			}
		} catch (UnknownHostException e) {
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
