package com.study.tuling.spring.eventLisener;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wfsong
 * @date 2022/11/15 15:29
 */
 public class BeanConfigTest {

	 @Test
	 public void test1() {
		 AnnotationConfigApplicationContext  context = new AnnotationConfigApplicationContext(BeanConfig.class);
		 context.publishEvent(new OrderEvent("12312"));
	 }

}