package com.cnsyear.server;

import javax.jws.WebService;

import com.cnsyear.bean.Order;
/**
 * 订单服务 SEI的实现
 * @author jiebaobao
 *
 */
@WebService
public class OrderWsImpl implements OrderWs {
	
	public OrderWsImpl() {
		System.out.println("init......");
	}

	@Override
	public Order getOrderById(int id) {
		 System.out.println("服务端获取订单；；；"+id);
		return new Order(1,"暖暖杯55度","$300");
	}

}
