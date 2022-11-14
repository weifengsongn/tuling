package com.study.tuling.springpool.service;

import com.study.tuling.springpool.annotations.SwfAutowired;
import com.study.tuling.springpool.annotations.SwfComponent;
import com.study.tuling.springpool.annotations.SwfScope;
import com.study.tuling.springpool.interfaces.SwfInitializingBean;

/**
 * a
 *
 * @author wfsong
 * @date 2022/11/8 18:36
 */
@SwfComponent
@SwfScope("pro")
public class UserService implements SwfInitializingBean {

	@SwfAutowired
	private OrderService orderService;

	public void sout() {
		System.out.println("UserService");
	}

	public void fieldOut() {
		orderService.sout();
		orderService.studySout();
	}


	@Override
	public void afterBreak() {
		System.out.println("afterBreak");
	}
}
