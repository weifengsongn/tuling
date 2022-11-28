package com.study.tuling.spring.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * a
 *
 * @author wfsong
 * @date 2022/11/8 18:36
 */
@Component
//@Lazy
public class UserService {

	private OrderService orderService;

	public UserService(OrderService orderService) {
		System.out.println(1);
		this.orderService = orderService;
	}

	public UserService() {
		System.out.println(0);
	}

	public void sout() {
		System.out.println("UserService");
	}
}