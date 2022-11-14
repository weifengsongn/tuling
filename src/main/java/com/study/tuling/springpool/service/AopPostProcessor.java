package com.study.tuling.springpool.service;

import com.study.tuling.springpool.annotations.SwfComponent;
import com.study.tuling.springpool.interfaces.DynamicProxyInterface;
import com.study.tuling.springpool.interfaces.SwfBeanPostProcessor;

/**
 * 实现aop动态代理逻辑
 *
 * @author wfsong
 * @date 2022/11/14 19:09
 */

@SwfComponent
public class AopPostProcessor implements SwfBeanPostProcessor {
	@Override
	public Object beforeProcessor(Object beanObj, String beanName) {
		return SwfBeanPostProcessor.super.beforeProcessor(beanObj, beanName);
	}

	@Override
	public Object afterProcessor(Object beanObj, String beanName) {
		System.out.println(beanName);
		if (beanObj instanceof DynamicProxyInterface) {
			DynamicProxyInterface dynamicProxyInterface = () -> {
				System.out.println("执行切面前逻辑");
				((DynamicProxyInterface) beanObj).learn();
				System.out.println("执行切面后逻辑");
			};
			return dynamicProxyInterface;
		}

		return beanObj;
	}
}
