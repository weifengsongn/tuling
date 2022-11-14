package com.study.tuling.springpool.entry;

import lombok.Data;

/**
 * 定义类
 *
 * @author wfsong
 * @date 2022/11/8 18:59
 */
@Data
public class BeanDefinition {

	private String scope = "sing";
	private Class beanClass;

}
