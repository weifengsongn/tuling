package com.study.tuling.jvm;

/**
 * 测试类对父类和静态变量引用的加载顺序
 * 结论：
 *  （1）继承的类型；
 *  （2）new 的类型
 *  （3）成员变量中引用的类型
 *
 * @author wfsong
 * @date 2021/9/26 18:15
 */
public class ClassLoadTest2 extends A1{

	static {
		System.out.println("i am ClassLoadTest2");
	}

	String name = B.name;

	public static void main(String[] args) {
		ClassLoadTest2 classLoadTest2 = new ClassLoadTest2();
//		System.out.println(classLoadTest2.name);
	}

}

class A1 {
	static {
		System.out.println("i am A1");
	}
}

class B {
	static {
		System.out.println("i am B");
	}
	public static String name = "a";
}
