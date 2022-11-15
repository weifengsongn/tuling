package com.study.tuling.springpool;

import com.study.tuling.springpool.config.SpringConfig;
import com.study.tuling.springpool.interfaces.DynamicProxyInterface;
import com.study.tuling.springpool.service.OrderService;
import com.study.tuling.springpool.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author wfsong
 * @date 2022/11/8 18:39
 */
public class SwfApplicationConTextTest {

	@Test
	public void test1() {
		SwfApplicationConText applicationConText = new SwfApplicationConText(SpringConfig.class);

		UserService userService = (UserService) applicationConText.getBean("userService");
		userService.sout();
		OrderService orderService = (OrderService) applicationConText.getBean("orderService");
		orderService.sout();
		//单例 or 多例

		// 依赖注入
		userService.fieldOut();

		// 动态代理测试
		DynamicProxyInterface studyService = (DynamicProxyInterface) applicationConText.getBean("studyService");
		studyService.learn();
	}

	@Test
	public void test2() throws IOException {
		AnnotationConfigApplicationContext applicationConText = new AnnotationConfigApplicationContext(SpringConfig.class);

		Resource resource = applicationConText.getResource("classpath:application.properties");
		System.out.println(resource.contentLength());
		System.out.println(resource.getFilename());
		System.out.println(resource.getURI());
		System.out.println(resource.exists());
		System.out.println(resource.getFile());

		Resource resourceByFilePath = applicationConText.getResource("file:/D:/workspace/tuling/target/classes/application.properties");
		System.out.println(resourceByFilePath.exists());

		// classpath*时， 表达式匹配才会生效
		Resource[] resources = applicationConText.getResources("classpath*:**.xml");
		System.out.println(resources.length);

		HashMap<String, ArrayList<Long>> stringArrayListHashMap = new HashMap<>();
		ArrayList<Long> objects = new ArrayList<>();
		objects.add(2L);
		stringArrayListHashMap.put("b", objects);
		stringArrayListHashMap.computeIfAbsent("a", key -> new ArrayList<>(2));
		stringArrayListHashMap.get("a").add(1L);
		stringArrayListHashMap.computeIfAbsent("b", key -> new ArrayList<>(2));
		stringArrayListHashMap.get("b").add(3L);
	}

}