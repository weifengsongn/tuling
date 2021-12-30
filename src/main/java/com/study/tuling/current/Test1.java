package com.study.tuling.current;

import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 并发测试1 sycnized
 *
 * @author wfsong
 * @date 2021/12/13 21:51
 */
public class Test1 {
	private static Object obj =  new Object();
	private static int total  =  0;
	private static ReentrantLock lock = new ReentrantLock();

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Thread(() ->{
				for (int j = 0; j < 10000; j++) {
					lock.lock();
//					synchronized (obj) {
					total++;
					lock.unlock();
//					}
				}
			}).start();
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("total is :" + total);
	}

	@Test
	public void test1() {
		HashMap<String, Object> stringObjectHashMap = new HashMap<>(11);

		new Thread(() -> {
			System.out.println("i am thread");
		});

		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 10, 5,
				TimeUnit.SECONDS, new ArrayBlockingQueue<>(5));
		threadPool.execute(() -> System.out.println("我是任务1"));
		Thread thread = new Thread(() -> System.out.println("i am thread2"));
		Thread1 thread1 = new Thread1();
		thread.start();
		thread1.start();

	}
	@Test
	public void countCPU() {
		System.out.println(Runtime.getRuntime().availableProcessors());;
	}

	class Thread1 extends Thread{
		@Override
		public void run() {
			System.out.println("i am thread1");
		}

	}
}
