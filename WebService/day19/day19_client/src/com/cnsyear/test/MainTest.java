package com.cnsyear.test;

import java.util.List;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.message.Message;

import com.cnsyear.client.Hello;
import com.cnsyear.client.HelloImplService;
 

/**
 * 测试
 * @author jiebaobao
 *
 */
public class MainTest {
	
	public static void main(String[] args) {
		HelloImplService factory = new HelloImplService();
		Hello hello = factory.getHelloImplPort();
		Client client = ClientProxy.getClient(hello);//获取客户端代理对象
		List<Interceptor<? extends Message>> outInterceptors =client.getOutInterceptors();
		outInterceptors.add(new LoggingOutInterceptor());
		hello.say("zzz");
		
		
		/**
		 
		 
		 <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><ns2:say xmlns:ns2="http://server.cnsyear.com/"><arg0>zzz</arg0></ns2:say></soap:Body></soap:Envelope>
		 
		 
		 
		 
		 */
	}

}
