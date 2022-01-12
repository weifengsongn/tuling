package com.study.tuling.current;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * 测试对象头中锁标识
 *    未启动偏向锁： 00000001 00000000 00000000 00000000
 *    启动匿名偏向锁： 00000101 00000000 00000000 00000000 （还没有线程持有锁）
 *    偏向锁：  00000101 00000010 00000010 00000000  状态不变，但是 已存储了线程的ID
 *    偏向锁：
 *    轻量级锁：00
 *   重量级锁： 10
 *      0     4        (object header)                           05 28 fe 02 (00000101 00101000 11111110 00000010) (50210821)   第一行为锁状态；00000101   倒数第三个1为偏向锁标识
 *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
 *       8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
 *      12     4        (loss due to the next object alignment)
 * 查看锁升级过程
 *
 * @author wfsong
 * @date 2021/12/13 23:39
 */
public class TestForObjHead {


	public static void main(String[] args) throws InterruptedException {
		// 延迟对象的创建时间： 偏向锁机制未启动前，创建的对象加锁时，直接为轻量级锁
		TimeUnit.SECONDS.sleep(6);
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
