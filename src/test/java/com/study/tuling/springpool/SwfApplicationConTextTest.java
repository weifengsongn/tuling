package com.study.tuling.springpool;

import com.study.tuling.springpool.config.SpringConfig;
import com.study.tuling.springpool.interfaces.DynamicProxyInterface;
import com.study.tuling.springpool.service.OrderService;
import com.study.tuling.springpool.service.UserService;
import org.junit.Test;

/**
 * @author wfsong
 * @date 2022/11/8 18:39
 */
public class SwfApplicationConTextTest {

	@Test
	public void test1() {
		SwfApplicationConText applicationConText = new SwfApplicationConText(SpringConfig.class);

		UserService userService = (UserService)applicationConText.getBean("userService");
		userService.sout();
		OrderService orderService = (OrderService)applicationConText.getBean("orderService");
		orderService.sout();
		//单例 or 多例

		// 依赖注入
		userService.fieldOut();

		// 动态代理测试
		DynamicProxyInterface studyService = (DynamicProxyInterface)applicationConText.getBean("studyService");
		studyService.learn();
	}


}