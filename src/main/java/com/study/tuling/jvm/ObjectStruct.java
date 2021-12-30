package com.study.tuling.jvm;

import org.openjdk.jol.info.ClassLayout;

/**
 * 测试对象结构
 *
 * @author wfsong
 * @date 2021/9/26 15:15
 */
public class ObjectStruct {
	private  byte[] bytes = {1,2,3,4,5,6,7,78,8,8,8,8,8,8,8,8,8,8,8,8};

	private  String str = "123";


	public static void main(String[] args) {
		ClassLayout classLayout = ClassLayout.parseInstance(new ObjectStruct());
		System.out.println(classLayout.toPrintable());

		ClassLayout classLayout1 = ClassLayout.parseInstance(new ObjectStruct[18]);
		System.out.println(classLayout1.toPrintable());
	}
}

class A {
	private String name;
}
