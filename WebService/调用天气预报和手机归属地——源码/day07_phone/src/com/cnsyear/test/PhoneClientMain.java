package com.cnsyear.test;

import cn.com.webxml.MobileCodeWS;
import cn.com.webxml.MobileCodeWSSoap;

/**
 * 测试调用手机归属地
 * @author jiebaobao
 *
 */
public class PhoneClientMain {

	public static void main(String[] args) {
		MobileCodeWS factory = new MobileCodeWS();
		MobileCodeWSSoap mobileCodeWSSoap = factory.getMobileCodeWSSoap();
		String result = mobileCodeWSSoap.getMobileCodeInfo("15901503412", null);
		System.out.println(result);
	}
}
