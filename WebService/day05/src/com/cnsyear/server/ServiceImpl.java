package com.cnsyear.server;

import javax.jws.WebService;

/**
 * 服务端接口的实现
 * @author jiebaobao
 *
 */
@WebService
public class ServiceImpl implements Server {

	@Override
	public String sayHello(String name) {
		System.out.println("Hello World!——"+name);
		return "success";
	}

}