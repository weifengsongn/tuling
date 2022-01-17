package com.study.tuling.spring.testLomda;

/**
 * 喝水
 *
 * @author wfsong
 * @date 2022/1/17 10:27
 */
public class DrinkWater {

	public static void main(String[] args) {
		String s = drinkWater(() -> {
			System.out.println("i am drink water!");
			return "water";
		});
		System.out.println(s);
	}

	public static String drinkWater(DrinkSomething drinkSomething) {
		return drinkSomething.drink();
	}
}
