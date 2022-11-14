package com.study.tuling.springpool.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示为组件的注解
 *
 * @author wfsong
 * @date 2022/11/8 18:22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SwfComponent {

	String value() default "";

}
