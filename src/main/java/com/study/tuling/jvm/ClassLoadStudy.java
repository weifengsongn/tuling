package com.study.tuling.jvm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 类加载，双亲委派
 *
 * @author wfsong
 * @date 2021/9/16 22:51
 */
public class ClassLoadStudy {
	public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
		TestClassLoad testClassLoad = new TestClassLoad("D:/test/");
		Class<?> aClass = testClassLoad.loadClass("Test1");
		Object o = aClass.newInstance();
		Method printf = aClass.getDeclaredMethod("print", null);
		Object invoke = printf.invoke(o, null);
		System.out.println(o.getClass().getClassLoader());

	}

	static class TestClassLoad extends ClassLoader{

		private String path;

		public TestClassLoad(String path) {
			this.path = path;
		}

		@Override
		protected Class<?> findClass(String name){
			// 整整需要重写的饿方法
			// 加载字节码文件到内存
			byte[] aClass = new byte[0];
			try {
				aClass = getClass(name);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 将字节码文件加载为类对象
			return defineClass(name, aClass, 0, aClass.length);
		}

		private byte[] getClass(String name) throws IOException {
			String allPath = path + name + ".class";
			byte[] bytes = null;
			try(FileInputStream fileInputStream = new FileInputStream(allPath);){
				bytes = new byte[fileInputStream.available()];
				fileInputStream.read(bytes);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return bytes;
		}

		/**
		 * 重写，打破双亲委派机制
		 *
		 * @author wfsong
		 * @date 2021/9/16 22:33
		 * @param name
		 * @return java.lang.Class<?>
		 */
		@Override
		public Class<?> loadClass(String name) throws ClassNotFoundException {
			Class<?> c = findLoadedClass(name);
			if (c == null) {
				long t0 = System.nanoTime();

				if (c == null) {
					// If still not found, then invoke findClass in order
					// to find the class.
					long t1 = System.nanoTime();
					if (name.equals( "Test1")) {
						c = findClass(name);
					} else {
						// 获取
						c= this.getParent().loadClass(name);
						System.out.println(this.getParent().toString());
					}

				}
			}

			return c;
		}
	}
}
