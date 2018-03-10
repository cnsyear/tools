package com.cnsyear.server;

import javax.jws.WebService;

/**
 * SEI的实现
 * @author jiebaobao
 *
 */
@WebService
public class HelloImpl implements Hello {

	@Override
	public String say(String name) {
		System.out.println("name:"+name);
		return "name:"+name;
	}

}
