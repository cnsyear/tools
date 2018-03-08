package com.cnsyear.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cnsyear.client.Order;
import com.cnsyear.client.OrderWs;

public class ClientTest {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]  {"beans.xml"});
		OrderWs orderWS = (OrderWs) context.getBean("orderClient");
		Order order = orderWS.getOrderById(24);
		System.out.println(order);
	}
}
