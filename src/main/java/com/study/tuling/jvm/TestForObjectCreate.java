package com.study.tuling.jvm;

/**
 * 测试对象创建时构造器与成员变量赋值执行顺序
 *
 * 结论先执行成员变量赋值与编写顺序无关
 * @author wfsong
 * @date 2021/11/18 8:07
 */
public class TestForObjectCreate {
	public static void main(String[] args) {
		System.out.println(new A());
	}
}

class A{
	public String name = "zs";
	A(){
		name = "ls";
		age = "123";
	}
	public String age = "12";

	@Override
	public String toString() {
		return "A{" +
				"name='" + name + '\'' +
				", age='" + age + '\'' +
				'}';
	}
}
