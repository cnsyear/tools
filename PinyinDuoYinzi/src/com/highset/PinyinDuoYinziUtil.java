package com.highset;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * <b>类描述：多音字排序工具类</b><br/>
 * <b>类名称：</b>PinyinDuoYinziUtil.java<br/>
 * <b>创建人：</b>jie.zhao<br/>
 * <b>创建时间：</b>2017年10月16日<br/>
 * <b>备注：</b><br/>
 *
 * @version 1.0.0<br/>
 */
public class PinyinDuoYinziUtil {

	private static Map<String, List<String>> pinyinMap = new HashMap<String, List<String>>();
	
	/**
	 * 静态代码块 -->加载字典文件 
	 */
	static {
		try {
			initPinyin("dict.txt");// 这是自定义字典文件
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <b>Description:主程序</b><br> 
	 * @param args
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @Note
	 * <b>Author:</b>Jie.Zhao</a>
	 * <br><b>Date:</b> 2017年12月1日 下午5:21:10
	 * <br><b>Version:</b> 1.0
	 */
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		List<String> list = new ArrayList<String>();
		list.add("浙江");
		list.add("北京");
		list.add("重庆");
		list.add("云南");
		list.add("和");
		list.add("天津");
		System.out.println("排序前："+list);
		Collections.sort(list, new Comparator<String>() {
			public int compare(String o1, String o2) {
				return getPinYin(o1).compareTo(getPinYin(o2));
			}
		});
		System.out.println("排序后："+list);

	}
		
	/**
	 * <b>Description: 这是核心方法</b><br> 
	 * @param chinese
	 * @return
	 * @Note
	 * <b>Author:</b>Jie.Zhao</a>
	 * <br><b>Date:</b> 2017年12月1日 下午5:22:05
	 * <br><b>Version:</b> 1.0
	 */
	public static String getPinYin(String chinese) {
		char[] arr = null;
		arr = chinese.toCharArray();
		String[] results = new String[arr.length];
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		StringBuffer pinyin = new StringBuffer();
		String result = "";
		try {
			for (int i = 0; i < arr.length; i++) {
				char ch = arr[i];
				// 判断是否为汉字字符
				if (java.lang.Character.toString(ch).matches("[\\u4E00-\\u9FA5]+")) {
					results = net.sourceforge.pinyin4j.PinyinHelper.toHanyuPinyinStringArray(ch, format);
					int len = results.length;

					if (len == 1) { // 不是多音字
						result = results[0];
					} else if (results[0].equals(results[1])) {// 非多音字 有多个音，取第一个
						result = results[0];
					} else {
						// 多音字
						int length = chinese.length();
						boolean flag = false;
						String s = null;
						List<String> keyList = null;
						for (int x = 0; x < len; x++) {
							String py = results[x];
							if (py.contains("u:")) { // 过滤 u:
								py = py.replace("u:", "v");
								// System.out.println("filter u:" + py);
							}
							keyList = pinyinMap.get(py);
							if (i + 3 <= length) { // 后向匹配2个汉字 大西洋
								s = chinese.substring(i, i + 3);
								if (keyList != null && (keyList.contains(s))) {
									// System.out.println("last 2 > " + py);
									result = py;
									flag = true;
									break;
								}
							}

							if (i + 2 <= length) { // 后向匹配 1个汉字 大西
								s = chinese.substring(i, i + 2);
								if (keyList != null && (keyList.contains(s))) {
									// System.out.println("last 1 > " + py);
									result = py;
									flag = true;
									break;
								}
							}

							if ((i - 2 >= 0) && (i + 1 <= length)) { // 前向匹配2个汉字 龙固大
								s = chinese.substring(i - 2, i + 1);
								if (keyList != null && (keyList.contains(s))) {
									// System.out.println("before 2 < " + py);
									result = py;
									flag = true;
									break;
								}
							}

							if ((i - 1 >= 0) && (i + 1 <= length)) { // 前向匹配1个汉字 固大
								s = chinese.substring(i - 1, i + 1);
								if (keyList != null && (keyList.contains(s))) {
									// System.out.println("before 1 < " + py);
									result = py;
									flag = true;
									break;
								}
							}

							if ((i - 1 >= 0) && (i + 2 <= length)) { // 前向1个，后向1个 固大西
								s = chinese.substring(i - 1, i + 2);
								if (keyList != null && (keyList.contains(s))) {
									// System.out.println("before last 1 <> " + py);
									result = py;
									flag = true;
									break;
								}
							}
						}

						if (!flag) { // 都没有找到，匹配默认的 读音 大
							s = String.valueOf(ch);
							for (int x = 0; x < len; x++) {
								String py = results[x];
								if (py.contains("u:")) { // 过滤 u:
									py = py.replace("u:", "v");
									// System.out.println("filter u:");
								}
								keyList = pinyinMap.get(py);

								if (keyList != null && (keyList.contains(s))) {
									// System.out.println("default = " + py);
									result = py;// 拼音首字母 大写
									break;
								}
							}
						}
					}
					pinyin.append(result);
				} else {
					pinyin.append(java.lang.Character.toString(arr[i]));

				}
			}
			return pinyin.toString();
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		// System.out.println(pinyin.toString());
		return pinyin.toString();
	}

	/**
	 * <b>Description:初始化 所有的多音字词组</b><br> 
	 * @param filePath
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @Note
	 * <b>Author:</b>Jie.Zhao</a>
	 * <br><b>Date:</b> 2017年12月1日 下午5:22:22
	 * <br><b>Version:</b> 1.0
	 */
	public static void initPinyin(String filePath) throws FileNotFoundException, UnsupportedEncodingException {
		// 读取多音字的全部拼音表;
		InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "gbk");
		BufferedReader br = new BufferedReader(isr);
		String s = null;
		try {
			while ((s = br.readLine()) != null) {
				if (s != null) {
					String[] arr = s.split("#");
					String pinyin = arr[0];
					String chinese = arr[1];
					if (chinese != null) {
						String[] strs = chinese.split(" ");
						List<String> list = Arrays.asList(strs);
						pinyinMap.put(pinyin, list);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}