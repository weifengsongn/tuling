package com.study.tuling.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程池测试
 *
 * @author wfsong
 * @date 2021/12/30 10:08
 */
@Slf4j
public class ExecutePoolTest {
	 static volatile Integer count = 1;
	 static volatile boolean mainBlackFlag = false;
	 public static  Object object = new Object();
	 public static  Object object1 = new Object();
	/**
	 * 线程池创建测试
	 *
	 * @author wfsong
	 * @date 2021/12/30 10:09
	 */
	@Test
	public void createTest() throws InterruptedException {

		// 创建最基本线程池吗，查看线程名称
		ThreadPoolExecutor pool1 = new ThreadPoolExecutor(2, 3, 5000
				, TimeUnit.NANOSECONDS, new ArrayBlockingQueue<>(3));
		// 测试阻塞队列 优先级
		for (int i = 0; i < 6; i++) {
			pool1.execute(() -> {
				try {
					log.info("{}开始执行,时间：{}！", Thread.currentThread().getName(), getConcurrentIme());
					Thread.sleep(3000);
					log.info("{}执行结束,,时间：{}", Thread.currentThread().getName(), getConcurrentIme());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
		Thread main = Thread.currentThread();

		// 如果子线程未执行结束，阻塞主线程
		if(pool1.getActiveCount() > 0) {
			// 创建子线程，检测线程池。如果所有任务都执行完毕，则唤醒主线程
			new Thread(() -> {
				// 线程执行完成，且main阻塞成功
				while (pool1.getActiveCount() > 0 || mainBlackFlag == false) {
					Thread.yield();
				}
				synchronized (object1) {
					mainBlackFlag = false;
					LockSupport.unpark(main);
				}
			}).start();
			// 阻塞不会释放锁？
			synchronized (object) {
				mainBlackFlag = true;
				LockSupport.park();
			}
		}
	}

	/**
	 * 优先级队列测试：
	 * 结论： 需要使用实现Compara接口的任务子类。并且自定义优先级的比较规则。
	 * 注意点：可能会发生任务还没执行完，获取当前活动线程数为0的状态
	 * @author wfsong
	 * @date 2021/12/30 14:57
	 */
	@Test
	public void createPriorityTest() throws InterruptedException {
		ThreadPoolExecutor pool2 = new ThreadPoolExecutor(2, 2, 5000
				, TimeUnit.NANOSECONDS, new PriorityBlockingQueue<>());
		// 测试阻塞队列 优先级
		for (int i = 0; i < 6; i++) {
			pool2.execute(new PriorityRunnable(i) {
				@Override
				public void run() {
					try {
						log.info("{}开始执行,时间：{},优先级为：{}！", Thread.currentThread().getName(), getConcurrentIme(), this.priority);
						Thread.sleep(1000);
						System.out.printf("总任务数为： %s,活动线程数为： %s,完成任务数为：%s %n", pool2.getTaskCount(), pool2.getActiveCount(), pool2.getCompletedTaskCount());
						log.info("{}执行结束,,时间：{}", Thread.currentThread().getName(), getConcurrentIme());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		Thread main = Thread.currentThread();
		// 如果子线程未执行结束，阻塞主线程
		if(pool2.getActiveCount() > 0) {
			// 创建子线程，检测线程池。如果所有任务都执行完毕，则唤醒主线程
			new Thread(() -> {
				// 线程执行完成，且main阻塞成功
				// 存在队列中，任务未执行完。getActiveCount == 0;
				while (pool2.getCompletedTaskCount() == 0 || pool2.getTaskCount() > pool2.getCompletedTaskCount() || mainBlackFlag == false) {
					Thread.yield();
				}
				synchronized (object1) {
					mainBlackFlag = false;
					LockSupport.unpark(main);
				}
			}).start();
			// 阻塞不会释放锁？
			synchronized (object) {
				mainBlackFlag = true;
				LockSupport.park();
			}
		}
	}
	/**
	 * 测试线程池参数使用
	 * 1 public   long   getTaskCount()   //线程池已执行与未执行的任务总数
	 * 2 public   long   getCompletedTaskCount()   //已完成的任务数
	 * 3 public   int   getPoolSize()   //线程池当前的线程数
	 * 4 public   int   getActiveCount()   //线程池中正在执行任务的线程数量
	 * shutdown（）：在完成已提交的任务后封闭办事，不再接管新任务,
	 * 4，shutdownNow（）：停止所有正在履行的任务并封闭办事。
	 * 5，isTerminated（）：测试是否所有任务都履行完毕了。
	 * 6，isShutdown（）：测试是否该ExecutorService已被关闭。
	 * @author wfsong
	 * @date 2021/12/30 10:22
	 */
	public void poolMethodTest() {
		
	}

	/**
	 * 获取当前时间
	 *
	 * @author wfsong
	 * @date 2021/12/30 10:45
	 */
	public static String getConcurrentIme() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return now.format(dateTimeFormatter);
	}

	/**
	 * 可以设置线程名称及优先级、是否守护线程
	 *
	 * @author wfsong
	 * @date 2021/12/30 10:34
	 */
	public static ThreadFactory getThreadFactory() {
		return new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				thread.setName(String.format("swf-%s", count));
				// 对任务设置优先级，设定比较规则。与线程优先级无关
//				thread.setPriority(count);
				// 优先级小于二设置为守护线程
//				if (count < 2) {
//					thread.setDaemon(true);
//				}
				count++;
				return thread;
			}
		};

	}

	/**
	 * 创建任务
	 *
	 * 优先级越大，越先执行
	 * @author wfsong
	 * @date 2021/12/30 14:10
	 */
	abstract class  PriorityRunnable implements Runnable, Comparable<PriorityRunnable> {

		public Integer priority;

		public PriorityRunnable(Integer priority) {
			this.priority = priority;
		}

		@Override
		public int compareTo(PriorityRunnable o) {
			return o.priority - this.priority;
		}
	}
}
