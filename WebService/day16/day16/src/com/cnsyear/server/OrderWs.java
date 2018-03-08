package com.cnsyear.server;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.cnsyear.bean.Order;

/**
 * 订单服务 SEI
 * 
 * @author jiebaobao
 *
 */
@WebService
public interface OrderWs {

	@WebMethod
	Order getOrderById(int id);

}
