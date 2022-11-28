package com.study.tuling.spring.testLomda;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author wfsong
 * @date 2022/10/21 18:02
 */
public class AutowriedImpTest {

	@Test
	public void test1() throws IllegalAccessException {
		AutowriedImp autowriedImp = new AutowriedImp();
		for (Field field : autowriedImp.getClass().getFields()) {
			// 查看是否存在注解
			if (field.isAnnotationPresent(Autowired.class)) {
				// 属性赋值
				field.set(autowriedImp, new OrderService());
			}
		}
		System.out.println(Objects.isNull(autowriedImp.orderService));
		System.out.println(Objects.isNull(autowriedImp.payService));

	}

}