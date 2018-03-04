package com.cnsyear.client;

/**
 * 测试客户端请求
 * @author jiebaobao
 *
 */
public class ClientMain {
	public static void main(String[] args) {
		ServiceImplService factory = new ServiceImplService();
		Server server =factory.getServiceImplPort();
		String result = server.sayHello("zz");
		System.out.println(result);
	}
}
