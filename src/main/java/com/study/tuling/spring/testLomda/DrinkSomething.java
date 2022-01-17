package com.study.tuling.spring.testLomda;

/**
 * 喝点什么把
 *
 * @author wfsong
 * @date 2022/1/17 10:23
 */
@FunctionalInterface
public interface DrinkSomething {

	/**
	 * 喝点东西，具体喝什么，依赖子类实现
	 *
	 * @author wfsong
	 * @date 2022/1/17 10:26
	 * @return T
	 */
	String drink() ;
}
