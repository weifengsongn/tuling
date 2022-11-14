package com.study.tuling.springpool.service;

import com.study.tuling.springpool.annotations.SwfAutowired;
import com.study.tuling.springpool.annotations.SwfComponent;
import com.study.tuling.springpool.interfaces.DynamicProxyInterface;

/**
 * a
 *
 * @author wfsong
 * @date 2022/11/8 18:36
 */
@SwfComponent
public class OrderService {

	@SwfAutowired
	private DynamicProxyInterface studyService;

	public void sout() {
		System.out.println("OrderService");
	}

	public void studySout() {
		studyService.learn();
	}
}
