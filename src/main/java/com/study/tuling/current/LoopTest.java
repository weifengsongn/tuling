package com.study.tuling.current;

/**
 * 循环测试
 *
 * @author wfsong
 * @date 2021/12/14 21:59
 */
public class LoopTest {

	public static void main(String[] args) {
		int i = 0;
		while (true) {
			if (i > 10 ){
				break;
			}
			i++;
		}
		System.out.println("i am out");
	}
}
