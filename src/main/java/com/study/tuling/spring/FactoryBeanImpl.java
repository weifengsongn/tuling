package com.study.tuling.spring;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author wfsong
 * @date 2022/11/21 16:50
 */
@Component
public class FactoryBeanImpl  implements FactoryBean {
	@Override
	public Object getObject() throws Exception {
		String s = new String("12");
		return s;
	}

	@Override
	public Class<?> getObjectType() {
		return String.class;
	}
}
