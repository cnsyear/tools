package com.cnsyear.client.interceptor;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.xml.utils.DOMHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 客户端 拦截器
 * 
 * @author jiebaobao
 *
 */
public class MyInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
	private String name;
	private String password;

	
	public MyInterceptor(String name, String password) {
		super(Phase.PRE_PROTOCOL);//准备协议化时拦截
		this.name = name;
		this.password = password;
	}

	/*
 	<Envelope>
 		<head>
 			<atguigu>
 				<name>xfzhang</name>
 				<password>123456</password>
 			</atguigu>
 			<atguigu2>
 				<name>xfzhang</name>
 				<password>123456</password>
 			</atguigu2>
 		<head>
 		<Body>
 			<sayHello>
 				<arg0>BOB</arg0>
 			<sayHello>
 		</Body>
 	</Envelope>
	 */
	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		List<Header> headers = message.getHeaders();
		if (headers == null && headers.size() <= 0) {
			throw new Fault(new IllegalArgumentException("没有header>>>"));
		} 
		/*
		 * <user> <name>xfzhang</name> <password>123456</password> </user>
		 */
		Document document = DOMHelper.createDocument();
		Element rootEle = document.createElement("user");
		Element nameELe = document.createElement("name");
		nameELe.setTextContent(name);
		rootEle.appendChild(nameELe);
		Element passwordELe = document.createElement("password");
		passwordELe.setTextContent(password);
		rootEle.appendChild(passwordELe);
		
		headers.add(new Header(new QName("user"), rootEle));
		System.out.println("client handleMessage()....");

	}

}
