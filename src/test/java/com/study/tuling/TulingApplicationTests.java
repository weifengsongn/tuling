package com.study.tuling;

import com.study.tuling.spring.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class TulingApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() throws Exception {
		// 可以获取到 getObject（） 内部的返回对象
		Object factoryBeanImpl = applicationContext.getBean("factoryBeanImpl");
		// 可以获取到真正的bean
		FactoryBean bean = applicationContext.getBean("&factoryBeanImpl", FactoryBean.class);
		Object object = bean.getObject();
		System.out.println("123");

	}


	/**
	 * 1.spring内部推断构造方法的实验；
	 *
	 * 如果非懒加载则会在spring初始化阶段，使用无参构造
	 */
	@Test
	void testForConstruct() {
		Object userService = applicationContext.getBean("userService", new OrderService());

	}

}
