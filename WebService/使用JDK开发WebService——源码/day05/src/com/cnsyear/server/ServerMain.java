package com.cnsyear.server;

import javax.xml.ws.Endpoint;

/**
 * 测试启动类
 * 
 * @author jiebaobao
 *
 */
public class ServerMain {
	public static void main(String[] args) {
			String address="http://127.0.0.1:8888/sayHello";//地址
			Endpoint.publish(address, new ServiceImpl());//发布方法
			System.out.println("发布WebSerive成功！"+address);
	}
}
