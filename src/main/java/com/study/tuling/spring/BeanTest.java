package com.study.tuling.spring;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * IOC加载过程
 * 1.扫描需要加载bean；
 * 2.BeanDefinition 读取bean属性
 * 3.交由BeanFactory去创建bean
 * 4.通过注入属性进行bean的注入
 *
 * @author wfsong
 * @date 2022/1/12 10:10
 */
public class BeanTest {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(Configure.class);
		// 通过对正在创建的bean标记。然后检测创建标记，打破循环依赖
		Assert.assertTrue(appContext.containsBean("getUserA"));
	}
}
/**
 * 循环依赖测试
 *
 * @author wfsong
 * @date 2022/1/17 10:05
 */
@Configuration
class Configure{
	@Bean
	public userA getUserA(@Autowired UserB userB) {
		return new userA();
	}

	@Bean
	public UserB getUserB(@Autowired UserC userc) {
		return new UserB();
	}

	@Bean
	public UserC getUserC(@Autowired userA userA) {
		return new UserC();
	}
}


class userA{
	String name;
	@Autowired
	UserB userB;
}

class UserB{
	String name;
	@Autowired
	UserB userC;
}

class UserC{
	String name;
	@Autowired
	UserB userA;
}
