package com.study.tuling.jvm;

/**
 * 类加载测试
 *
 * 测试结论：加载静态常量也会加载类，符合猜想
 *
 * @author wfsong
 * @date 2021/11/16 22:03
 */
public class LoadClassTest {
	static {
		System.out.println("LoadClassTest is load!");
	}

	public static void main(String[] args) {
		System.out.println(" my name is " + StaticClass.Name);
	}
}
class StaticClass{
	public static String Name = "sonwge";

	static {
		System.out.println("StaticClass is load!");
	}
}
