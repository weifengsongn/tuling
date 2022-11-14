package com.study.tuling.springpool.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 单例或fuli
 * @author wfsong
 * @date 2022/11/8 18:34
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SwfScope {

	String value() default "sing";
}
