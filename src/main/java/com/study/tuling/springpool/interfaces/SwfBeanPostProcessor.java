package com.study.tuling.springpool.interfaces;

/**
 * bean一切增强方法的具体实现
 *
 * aop
 * beanAware...
 *
 * @author wfsong
 * @date 2022/11/9 19:00
 */
public interface SwfBeanPostProcessor {

	default Object beforeProcessor(Object beanObj, String beanName) {
		return beanObj;
	};

	default Object afterProcessor(Object beanObj, String beanName) {
		return beanObj;
	};
}
