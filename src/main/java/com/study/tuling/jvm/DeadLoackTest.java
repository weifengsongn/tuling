package com.study.tuling.jvm;

/**
 * 死锁测试
 *
 * @author wfsong
 * @date 2021/10/15 10:33
 */
public class DeadLoackTest {
	static Object object  = new Object();
	static Object objec2  = new Object();

	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (object) {
					try {
						System.out.println("th1 start to sleep");
						Thread.sleep(5000);

						synchronized (objec2) {
							System.out.println("th1 get obj2 ");
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (objec2) {
					try {
						System.out.println("th2 start to sleep");
						Thread.sleep(5000);

						synchronized (object) {
							System.out.println("th2 get obj1 ");
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

}
