package com.cnsyear.client;

import java.util.List;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;

import com.cnsyear.client.interceptor.MyInterceptor;

/**
 * 测试客户端请求
 * @author jiebaobao
 *
 */
public class ClientMain {
	public static void main(String[] args) {
		ServiceImplService factory = new ServiceImplService();
		Server server =factory.getServiceImplPort();
		
		Client client = ClientProxy.getClient(server);//获取客户端代理对象
		
		//客户端出拦截器
		List<Interceptor<? extends Message>> outInterceptors = client.getOutInterceptors();
		outInterceptors.add(new MyInterceptor("zhaojie","123456"));
		
		String result = server.sayHello("zz");
		System.out.println(result);
	}
}
