package com.study.tuling.current;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * 线程池，多线程。返回值阻塞
 *
 * @author wfsong
 * @date 2021/12/29 22:40
 */
public class ForkAndJoinTest {

	/**
	 * 异步线程返回值测试
	 *
	 * 结论：
	 *      如不获取子线程执行结果，不会因子线程执行进度阻塞主线程；
	 *      如获取子线程结果，则会在获取结果出阻塞等待子线程执行结束
	 * @author wfsong
	 * @date 2021/12/29 22:40
	 */
	@Test
	public void test1() throws ExecutionException, InterruptedException, TimeoutException {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		Future<String> future = executorService.submit(() -> {
			System.out.println(Thread.currentThread().getName() +"开始执行子线程！");
			Thread.sleep(2000);
			return "a";
		});
		Future future1 = executorService.submit(() -> {
			System.out.println(Thread.currentThread().getName() +"开始执行子线程！");
			Thread.sleep(2000);
			return "b";
		});
		System.out.println("i am 主线程");
		System.out.printf("子线程1的值为：%s%n", future.get(1000, TimeUnit.MILLISECONDS));
		System.out.println("子线程2的值为：" + future1.get());
		System.out.println("主线程 结束");
	}
}
