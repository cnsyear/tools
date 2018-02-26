package com.zhao;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sun.io.ByteToCharConverter;
import sun.io.MalformedInputException;

@SuppressWarnings("deprecation")
public class Converter {
	public static String dateToStr(String format, Date date) {
		try {
			if (null != format) {
				return new SimpleDateFormat(format).format(date);
			}
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		} catch (Exception e) {
		}
		return null;
	}

	public static Long toLong(Object obj) {
		try {
			String longString = toBlank(obj);
			return Long.valueOf(longString);
		} catch (Exception e) {
		}
		return null;
	}

	public static String toBlank(Object obj) {
		if (obj == null) {
			return "";
		}
		String className = obj.getClass().getName();
		String oValue = "";
		if ((className.equals("java.util.Date"))
				|| (className.equals("java.sql.Timestamp")))
			try {
				oValue = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(obj).trim();
			} catch (Exception e) {
				try {
					oValue = new SimpleDateFormat("yyyy-MM-dd").format(obj)
							.trim();
				} catch (Exception e_) {
					oValue = "";
				}
			}
		else if (className.equals("oracle.sql.CLOB"))
			try {
				Clob clob = (Clob) obj;
				oValue = clob.getSubString(1L, (int) clob.length()).trim();
			} catch (SQLException e) {
				oValue = "";
			}
		else {
			oValue = obj.toString().trim();
		}
		return oValue;
	}

	public static String[] toArray(List<String> list) {
		if (list == null)
			return null;
		String[] array = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = ((String) list.get(i));
		}
		return array;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<String> toList(String[] array) {
		if (array == null)
			return null;
		List list = new ArrayList();
		for (String a : array) {
			list.add(a);
		}
		return list;
	}

	public static Date toDate(String dateString) {
		dateString = toBlank(dateString);
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(dateString);
		} catch (ParseException e) {
			try {
				return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
			} catch (ParseException e1) {
			}
		}
		return null;
	}

	public static Date toDate(String dateString, String dateFormater) {
		dateString = toBlank(dateString);
		try {
			return new SimpleDateFormat(dateFormater).parse(dateString);
		} catch (ParseException e) {
		}
		return null;
	}

	public static String toString(Date date) {
		if (date == null)
			return "";
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		} catch (Exception e) {
			try {
				return new SimpleDateFormat("yyyy-MM-dd").format(date);
			} catch (Exception e1) {
			}
		}
		return "";
	}

	public static String toString(Date date, String formater) {
		if (date == null)
			return "";
		try {
			return new SimpleDateFormat(formater).format(date);
		} catch (Exception e) {
		}
		return toString(date);
	}

	public static String toString1(Date date) {
		if (date == null)
			return "";
		try {
			return new SimpleDateFormat("yyyyMMddhhmmss").format(date);
		} catch (Exception e) {
			try {
				return new SimpleDateFormat("yyyyMMdd").format(date);
			} catch (Exception e1) {
			}
		}
		return "";
	}

	public static String toString(Object obj, SimpleDateFormat format1,
			SimpleDateFormat format2) {
		String temp = "";
		if (obj != null) {
			try {
				temp = format1.format(format2.parse(obj.toString()));
			} catch (Exception e) {
				return "";
			}
		}
		return temp.trim();
	}

	public static Integer toInteger(Object obj) {
		try {
			String integerString = toBlank(obj);
			return Integer.valueOf(integerString);
		} catch (Exception e) {
		}
		return null;
	}

	public static String getUnicode(String toEncoded, String encoding) {
		String retString = "";
		if ((toEncoded.equals("")) || (toEncoded.trim().equals("")))
			return toEncoded;
		try {
			byte[] b = toEncoded.getBytes(encoding);
			ByteToCharConverter convertor = ByteToCharConverter
					.getConverter(encoding);
			char[] c = convertor.convertAll(b);
			for (int i = 0; i < c.length; i++)
				retString = retString + String.valueOf(c[i]);
		} catch (UnsupportedEncodingException e) {
			System.out.println(e);
		} catch (MalformedInputException e) {
			System.out.println(e);
		}
		return retString;
	}

	public static String getFileSimpleName(String filePath) {
		if ((filePath == null) || (filePath.trim().length() <= 0)) {
			return null;
		}
		filePath = filePath.replace("\\", "/");
		filePath = filePath.substring(filePath.lastIndexOf("/") + 1,
				filePath.length());
		return filePath;
	}

	public static void main(String[] args) {
		System.out.println("【1】"
				+ getFileSimpleName("..\\..\\file\\demo\\xxx.text"));
		System.out
				.println("【2】" + getFileSimpleName("..\\..\\file\\demo\\xxx"));
		System.out.println("【3】" + getFileSimpleName("..\\..\\file\\demo\\"));
	}

	public static String getFileSimpleName(File file) {
		return getFileSimpleName(file.getPath());
	}

	public static String[] unin(String[] keyword) {
		if ((keyword == null) || (keyword.length <= 0)) {
			return null;
		}
		String[] res = new String[0];
		for (int i = 0; i < keyword.length; i++) {
			res = unin(res, formatKeyword(keyword[i]));
		}
		return res;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String[] unin(String[] to, String[] from) {
		String flag = "";
		if ((from == null) || (from.length <= 0)) {
			if ((to == null) || (to.length <= 0))
				flag = "";
			else {
				flag = "T";
			}
		} else if ((to == null) || (to.length <= 0))
			flag = "F";
		else {
			flag = "FT";
		}

		List list = new ArrayList();
		if (flag.contains("T")) {
			for (int i = 0; i < to.length; i++) {
				if (!list.contains(to[i]))
					list.add(to[i]);
			}
		}
		if (flag.contains("F")) {
			for (int i = 0; i < from.length; i++) {
				if (!list.contains(from[i]))
					list.add(from[i]);
			}
		}
		if ((list == null) || (list.size() <= 0)) {
			return null;
		}
		String[] res = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			res[i] = ((String) list.get(i));
		}
		return res;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String[] formatKeyword(String keyword) {
		if ((keyword == null) || (keyword.trim().length() <= 0)) {
			return null;
		}
		String[] token = keyword.split(" ");
		if ((token != null) && (token.length > 0)) {
			List list = new ArrayList();
			for (int i = 0; i < token.length; i++) {
				String temp = token[i] == null ? "" : token[i].trim();
				if (!temp.equals(""))
					list.add(token[i].trim());
			}
			if ((list == null) || (list.size() <= 0)) {
				return null;
			}
			String[] rs = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				rs[i] = ((String) list.get(i));
			}
			return rs;
		}

		return null;
	}

	public static String highlight(String str, List<String> keywords) {
		if ((str != null) && (str.trim().length() > 0) && (keywords != null)
				&& (keywords.size() > 0)) {
			for (int i = 0; i < keywords.size(); i++) {
				String keyword = (String) keywords.get(i);
				str = str.replaceAll(keyword, "<font color='red'>" + keyword
						+ "</font>");
			}
		}
		return str;
	}

	public static String highlight(String str, String[] keywords) {
		if ((str != null) && (str.trim().length() > 0) && (keywords != null)
				&& (keywords.length > 0)) {
			for (int i = 0; i < keywords.length; i++) {
				String keyword = keywords[i];
				str = str.replaceAll(keyword, "<font color='red'>" + keyword
						+ "</font>");
			}
		}
		return str;
	}

	public static String toString(Object o, List<String> standardKeywords) {
		if ((standardKeywords == null) || (standardKeywords.size() <= 0)) {
			return toString(o, new String[0]);
		}
		return toString(o, (String[]) standardKeywords.toArray());
	}

	public static String toString(Object o, String[] standardKeywords) {
		if (o == null) {
			return "";
		}
		String className = o.getClass().getName();
		String oValue = "";
		if (className.equals("java.util.Date"))
			try {
				oValue = highlight(
						toBlank(
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.format(o)).trim(), standardKeywords);
			} catch (Exception e) {
				try {
					oValue = highlight(
							toBlank(
									new SimpleDateFormat("yyyy-MM-dd")
											.format(o)).trim(),
							standardKeywords);
				} catch (Exception e_) {
					oValue = "";
				}
			}
		else if (className.equals("oracle.sql.CLOB"))
			try {
				Clob clob = (Clob) o;
				oValue = highlight(
						toBlank(clob.getSubString(1L, (int) clob.length()))
								.trim(), standardKeywords);
			} catch (SQLException e) {
				oValue = "";
			}
		else {
			oValue = highlight(toBlank(o).trim(), standardKeywords);
		}
		return oValue;
	}

	public static Long toLong(Object obj, Long defValue) {
		try {
			String longString = toBlank(obj);
			return Long.valueOf(longString);
		} catch (Exception e) {
		}
		return defValue;
	}

	public static Double toDouble(Object obj) {
		try {
			String longString = toBlank(obj);
			return Double.valueOf(longString);
		} catch (Exception e) {
		}
		return Double.valueOf(0.0D);
	}

	public static Boolean toBoolean(Object obj) {
		String s = toBlank(obj).toLowerCase();
		if (s.equals("")) {
			return null;
		}
		return Boolean.valueOf(s);
	}
}