package com.cnsyear.test;

import java.util.List;

import cn.com.webxml.ArrayOfString;
import cn.com.webxml.WeatherWS;
import cn.com.webxml.WeatherWSSoap;

/**
 * 测试调用天气
 * @author jiebaobao
 *
 */
public class WeatherClientMain {

	public static void main(String[] args) {
		WeatherWS weatherWS = new WeatherWS();
		WeatherWSSoap weatherWSSoap = weatherWS.getWeatherWSSoap();
		ArrayOfString weather = weatherWSSoap.getWeather("北京", null);
		List<String> list = weather.getString();
		for(String s : list) {
			System.out.println(s+"------");
		}
		
	}
}
