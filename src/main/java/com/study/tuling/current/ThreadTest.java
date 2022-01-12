package com.study.tuling.current;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 测试线程yield、sleeo]、wait
 *
 * @author wfsong
 * @date 2021/12/15 5:57
 */
public class ThreadTest {
	public static final Object obj = new Object();

	private volatile static Integer flag = 0;
	@Test
	public void runTest() {
	    getThreads().forEach(thread -> {
		   thread.start();
	    });
		while (flag <9) {

		}
	}

	/**
	 * 1.测试让出Cpu优先级
	 * 2.测试是否有锁的释放
	 *
	 * 验证结论：
	 *   1.sleep 不释放锁
	 *   2.任何优先级都可获取cpu
	 *   3.会阻塞指定时间
	 *
	 * @author wfsong
	 * @date 2021/12/15 6:14
	 */
	public void testSleep() throws InterruptedException {
		System.out.println(Thread.currentThread().getName() +":进入testSleep");
		synchronized (obj) {
			flag++;
			System.out.println(Thread.currentThread().getName() +":进入synchronized");
			Thread.sleep(1000);
		}
		System.out.println(Thread.currentThread().getName() +":is over ");
	}

	/**
	 * 会让出cpu但是不会阻塞
	 *
	 * @author wfsong
	 * @date 2021/12/15 7:10
	 */
	public void testYield() throws InterruptedException {
		System.out.println(Thread.currentThread().getName() +":进入testYield,放弃时间片");
		Thread.yield();
		System.out.println(Thread.currentThread().getName() +":testYield");
		synchronized (obj) {
			flag++;
			System.out.println(Thread.currentThread().getName() +":进入synchronized");
			Thread.yield();
		}
		System.out.println(Thread.currentThread().getName() +":is over ");
	}

	/**
	 * 1到10的范围指定。10表示最高优先级,1表示最低优先级,5是普通优先级
	 *
	 * @author wfsong
	 * @date 2021/12/15 6:24 
	 * @return java.util.List<java.lang.Thread>
	 */
	public List<Thread> getThreads(){
		ArrayList<Thread> threads = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(() -> {
				try {
//					this.testSleep();
					this.testYield();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
			thread.setPriority(i+1);
			threads.add(thread);
		}
		return  threads;
	}


}
