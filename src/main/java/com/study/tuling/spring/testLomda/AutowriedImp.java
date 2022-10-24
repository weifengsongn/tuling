package com.study.tuling.spring.testLomda;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现自动注入测试
 *
 * @author wfsong
 * @date 2022/10/21 17:59
 */
public class AutowriedImp {

	@Autowired
	public OrderService orderService;

	public PayService payService;
}

class OrderService{

}

class PayService{

}