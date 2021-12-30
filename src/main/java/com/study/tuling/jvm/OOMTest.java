package com.study.tuling.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * OOM自动打印堆栈信息测试
 *
 * @author wfsong
 * @date 2021/10/15 9:27
 */
public class OOMTest {
	static  List list = new ArrayList();
	public static void main(String[] args) {
		while (true) {
			list.add(new Test());
		}
	}
}

class Test{
	String name;
	String age;
	byte[] bytes = new byte[1024];
}
