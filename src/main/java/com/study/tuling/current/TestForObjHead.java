package com.study.tuling.current;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * 测试对象头中锁标识
 *
 * 查看锁升级过程
 *
 * @author wfsong
 * @date 2021/12/13 23:39
 */
public class TestForObjHead {


	public static void main(String[] args) throws InterruptedException {
		// 延迟对象的创建时间： 偏向锁机制未启动前，创建的对象加锁时，直接为轻量级锁
		TimeUnit.SECONDS.sleep(5);
		Object object = new Object();
		// 无锁状态 （匿名意向锁） 01
		System.out.println(ClassLayout.parseInstance(object).toPrintable());
		// 被一个线程加锁调用，升级为意向锁 01
		synchronized (object) {
			System.out.println(ClassLayout.parseInstance(object).toPrintable());
		}
		Thread.sleep(1);
		// 被多个线程调用，升级为 轻量级锁 00
		new Thread(() -> {
			synchronized (object) {
				System.out.println(ClassLayout.parseInstance(object).toPrintable());
			}
		}).start();
		Thread.sleep(1);
		// 并发时，线程等待时间长，升级未重量级锁 10
		for (int i = 0; i < 4; i++) {

			new Thread(() -> {
				synchronized (object) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		System.out.println(ClassLayout.parseInstance(object).toPrintable());
	}


	@Test
	public void test1() throws InterruptedException {
		TimeUnit.SECONDS.sleep(5);
		Object object = new Object();
		// 无锁状态 （匿名意向锁） 01
		System.out.println(ClassLayout.parseInstance(object).toPrintable());
//		object.hashCode();
		// 被一个线程加锁调用，升级为意向锁 01
		synchronized (object) {
			System.out.println(ClassLayout.parseInstance(object).toPrintable());
		}
		System.out.println(ClassLayout.parseInstance(object).toPrintable());
	}
}
