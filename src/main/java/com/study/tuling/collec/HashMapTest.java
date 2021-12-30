package com.study.tuling.collec;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;

/**
 * hashMap测试
 *
 * @author wfsong
 * @date 2021/12/24 16:37
 */
public class HashMapTest {

	@Test
	public void test1() {
		HashMap<String, Object> stringObjectHashMap = new HashMap<>(12);
	}

	/**
	 * 测试容积与hashTable的关系
	 *
	 * 测试步骤：
	 *    设置容积为4，然后在put方法打断点
	 *    查看当存放元素 == 3时，进行的扩容操作。
	 *    扩容会增加hash表的数组长度。  通过建立一个新数组将原来数据迁移过去
	 *    猜想：hashTable 的数数组长度与设置饿容积大小一致  是的，容积就是hashTable的长度
	 *      设计之初，可能是按照最理想情况设计的。后来因为hash碰撞，设计出链表。最后增加了红黑树
	 *
	 * @author wfsong
	 * @date 2021/12/27 10:23
	 */
	@Test
	public void testCaptain() {
		HashMap<String, Object> map = new HashMap<>(12);

		for (int i = 0; i < 3; i++) {
			map.put(i+"", i);
		}
	}

	@Test
	public void test2() {
//		A a = new A();
//		a.name = "zs";
//		A a1 = new A();
//		a1.name = "ls";
//		Assert.isTrue(Objects.equals(a.getName(), a1.getName()), "方案编号不允许修改！");

		int a = 4;
		int b = 4;
		System.out.println(a > b-1);
		ArrayList<String> strings = new ArrayList<>();
		List<String> strings1 = new CopyOnWriteArrayList<>();

		Vector<String> strings2 = new Vector<>();
		strings2.add("12");
		strings2.remove("1212");
		strings2.get(1);

	}

	/**
	 * 跳表测试
	 *
	 * @author wfsong
	 * @date 2021/12/29 14:46
	 */
	@Test
	public void skipMapTest() {
		ConcurrentSkipListMap<String, String> skipMap = new ConcurrentSkipListMap<>();
		skipMap.put("2","b");
		skipMap.put("1","a");
		skipMap.put("3","c");
		skipMap.put("19","j");
		skipMap.forEach((k,v) -> System.out.println(String.format("k is %s, v is %s", k,v)));

		ExecutorService executorService = Executors.newFixedThreadPool(5);
		Callable<String> callable = new Callable<String>() {
			@Override
			public String call() throws Exception {
				return "null";
			}
		};
		executorService.submit(callable);

	}

	/**
	 * 并发map测试
	 *
	 * @author wfsong
	 * @date 2021/12/28 15:59
	 */
	@Test
	public void concurrentMap() {
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
		CopyOnWriteArrayList<String> strings = new CopyOnWriteArrayList<>();
		strings.add("1");
		strings.add("2");
		strings.add("3");
		strings.forEach(s -> {
			if ("2".equals(s)) {
				strings.remove(s);
			}
		});
		System.out.println(Arrays.toString(strings.toArray()));

		ConcurrentSkipListSet<String> strings1 = new ConcurrentSkipListSet<>();
	}

	class A{
		String name;

		public String getName() {
			return name;
		}
	}
}
