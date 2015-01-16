package com.longau.wallstreetcn.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CategorySet {
	public CategorySet() {
	}

	public String setDis(String categorySet) {
		Map<String, String> dmap = new HashMap<String, String>();
		dmap.put("9", "中国");
		dmap.put("10", "美国");
		dmap.put("11", "欧元区");
		dmap.put("12", "日本");
		dmap.put("13", "英国");
		dmap.put("14", "澳洲");
		dmap.put("15", "加拿大");
		dmap.put("16", "瑞士");
		dmap.put("17", "其他地区");
		Set<String> keySet = dmap.keySet(); // 遍历map中的所有键，并存入数组strkeys
		String strkeys[] = new String[9];
		strkeys = keySet.toArray(new String[0]);

		String[] strArray = null;
		strArray = categorySet.split(","); // 拆分字符为"," ,然后把结果交给数组strArray

		int iArr = 0; // 获取的数组的计数
		int iKey = 0; // map集合的计数
		StringBuffer disStr = new StringBuffer(); // 存放结果集
		// 获取需要的信息
		int length = strArray.length;
		int length1=strkeys.length;
		if (length > 0) {
			for (iArr = 0; iArr < length; iArr++) {
				String a = strArray[iArr];
//				if (a.equals("9")||a.equals("10")||a.equals("11")||a.equals("12")||a.equals("13")||a.equals("14")||a.equals("15")||a.equals("16")||a.equals("17")) {
					for (; iKey < length1; iKey++) {
						if (a.equals(strkeys[iKey])) {
							disStr.append(dmap.get(a));
							disStr.append(",");
							break;
						}
					}
					iKey = 0;
//				}
			}
		}
		while ((length - 1) >= 1) {
			disStr = disStr.deleteCharAt(disStr.length() - 1);
			break;
		}
		System.out.println(disStr);
		return disStr.toString();
	}

	public String setPro(String property) {
		Map<String, String> pmap = new HashMap<String, String>();
		pmap.put("1", "外汇");
		pmap.put("2", "股市");
		pmap.put("3", "商品");
		pmap.put("4", "债市");
		Set<String> keySet = pmap.keySet(); // 遍历map中的所有键，并存入数组strkeys
		String strkeys[] = keySet.toArray(new String[0]);

		String[] strArray = null;
		strArray = property.split(","); // 拆分字符为"," ,然后把结果交给数组strArray

		int iArr = 0; // 获取的数组的计数
		int iKey = 0; // map集合的计数
		StringBuffer proStr = new StringBuffer(); // 存放结果集
		// 获取需要的信息 9,1,3,2,4
		int length = strArray.length;
		int length1=strkeys.length;
		if (length > 0) {
			for (iArr = 0; iArr < length; iArr++) {
				String a = strArray[iArr];
					for (; iKey < length1; iKey++) {
						if (a.equals(strkeys[iKey])) {
							proStr.append(pmap.get(a));
							proStr.append(",");
							break;
						}
					}
					iKey = 0;
				}
		}
		while ((length - 1) >= 1) {
			proStr = proStr.deleteCharAt(proStr.length() - 1);
			break;
		}
		System.out.println(proStr);
		return proStr.toString();
	}

	public String setCen(String centralbank) {
		Map<String, String> cmap = new HashMap<String, String>();
		cmap.put("5", "央行");

		Set<String> keySet = cmap.keySet(); // 遍历map中的所有键，并存入数组strkeys
		String strkeys[] = keySet.toArray(new String[0]);

		String[] strArray = null;
		strArray = centralbank.split(","); // 拆分字符为"," ,然后把结果交给数组strArray

		int iArr = 0; // 获取的数组的计数
		int iKey = 0; // map集合的计数
		StringBuffer cenStr = new StringBuffer(); // 存放结果集
		// 获取需要的信息
		int length = strArray.length;
		int length1=strkeys.length;
		if (length > 0) {
			for (iArr = 0; iArr < length; iArr++) {
				String a = strArray[iArr];
					for (; iKey < length1; iKey++) {
						if (a.equals(strkeys[iKey])) {
							cenStr.append(cmap.get(a));
							cenStr.append(",");
							break;
						}
					}
					iKey = 0;

				}

		}
		while ((length - 1) >= 1) {
			cenStr = cenStr.deleteCharAt(cenStr.length() - 1);
			break;
		}

		System.out.println(cenStr);
		return cenStr.toString();
	}


	public static void main(String[] args) {
		CategorySet set = new CategorySet();
		set.setDis("9,1,10");
		set.setPro("9,1,3,2,4");
		set.setCen("9,5");
	}
}
