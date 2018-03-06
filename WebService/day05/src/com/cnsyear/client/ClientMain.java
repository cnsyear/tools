package com.cnsyear.client;

/**
 * 客户端测试代码
 * 
 * @author jiebaobao
 *
 */
public class ClientMain {
	public static void main(String[] args) {
		ServiceImplService factory = new ServiceImplService();//创建工厂
		ServiceImpl serviceImpl = factory.getServiceImplPort();//获取代理对象
		System.out.println("代理对象："+serviceImpl);
		
		String result = serviceImpl.sayHello("Zhao.Jie");
		System.out.println(result);
	}
}
