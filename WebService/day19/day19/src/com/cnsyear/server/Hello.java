package com.cnsyear.server;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * SEI
 * @author jiebaobao
 *
 */
@WebService
public interface Hello {
	@WebMethod
	String say(String name);
}
