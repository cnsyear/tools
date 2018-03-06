package com.cnsyear.sevice;

import javax.xml.ws.Endpoint;

/**
 * cxf服务端测试启动
 * @author jiebaobao
 *
 */
public class MainCxf {
	public static void main(String[] args) {
		String address = "http://localhost:80/cxf";
		 Endpoint.publish(address, new CxfDataTypeImpl());
		System.out.println(" cxf服务端测试启动Ok！");
	}
}	
