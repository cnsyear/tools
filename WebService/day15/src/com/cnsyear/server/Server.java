package com.cnsyear.server;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * 服务端接口
 * @author jiebaobao
 *
 */
@WebService
public interface Server {
	/**
	 * 测试方法
	 * @return
	 */
	@WebMethod
	String sayHello(String name);

}
