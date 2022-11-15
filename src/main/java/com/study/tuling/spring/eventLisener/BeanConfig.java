package com.study.tuling.spring.eventLisener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wfsong
 * @date 2022/11/15 15:26
 */

@Configuration
public class BeanConfig {

	@Bean
	public ApplicationListener  applicationListener() {
		return event -> System.out.println("我是事件监听器" + event.getSource());
	}
}
