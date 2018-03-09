package com.cnsyear.server.interceptor;
import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Element;
/**
 * 服务端 拦截器
 * 
 * @author jiebaobao
 *
 */
public class ServiceInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

	public ServiceInterceptor() {
		super(Phase.PRE_PROTOCOL);
	}

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		Header header = message.getHeader(new QName("user"));
		if(header!=null) {
			Element userEle = (Element) header.getObject();
			String name = userEle.getElementsByTagName("name").item(0).getTextContent();
			String password = userEle.getElementsByTagName("password").item(0).getTextContent();
			if("zhaojie".equals(name) && "123456".equals(password)) {
				System.out.println("Server 通过拦截器....");
				return;
			}
		}
		//不能通过
		System.out.println("Server 没有通过拦截器....");
		throw new Fault(new RuntimeException("请求需要一个正确的用户名和密码!"));
	}

}
