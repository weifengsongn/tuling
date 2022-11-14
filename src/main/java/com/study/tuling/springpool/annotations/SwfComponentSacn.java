package com.study.tuling.springpool.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 扫描配置
 *
 * @author wfsong
 * @date 2022/11/8 19:03
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SwfComponentSacn {

	String value() default "";
}
