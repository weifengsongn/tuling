package com.study.tuling.springpool;

import com.study.tuling.springpool.annotations.SwfAutowired;
import com.study.tuling.springpool.annotations.SwfComponent;
import com.study.tuling.springpool.annotations.SwfComponentSacn;
import com.study.tuling.springpool.annotations.SwfScope;
import com.study.tuling.springpool.entry.BeanDefinition;
import com.study.tuling.springpool.interfaces.SwfBeanPostProcessor;
import com.study.tuling.springpool.service.SwfInitializingBean;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 主类
 *
 * @author wfsong
 * @date 2022/11/8 18:19
 */
public class SwfApplicationConText {
	private Map<String, BeanDefinition> definitionMap = new ConcurrentHashMap<>();
	private Map<String, Object> beanMap = new ConcurrentHashMap<>();
	private Set<SwfBeanPostProcessor> postProcessorSet = new HashSet<>();


	/**
	 * 解析配置，生成bean容器
	 *
	 * <p>
	 *     1.获取配置类的@ComponentScan,读取扫描的包路径;
	 *     2.读取相对路径，然后获取路径下所有class字节码文件；
	 *     3、扫描字节码文件是否有{@link SwfComponent @SwfComponent}注解;
	 *     4. 在扫描{@link SwfScope @SwfScope}注解;
	 *     5. 为了避免后续重复扫面，创建BeanDefination,记录这些配置信息
	 * </p>
	 * @param configClass
	 */
	public SwfApplicationConText(Class configClass) {
		initializeDefinition(configClass);
		initializeBeans();
	}

	/**
	 * 扫描要与创建的逻辑剥离，避免在创建时遇到还未加载的beanDefinition
	 *
	 * @param configClass
	 */
	private void initializeDefinition(Class configClass) {
		if (configClass.isAnnotationPresent(SwfComponentSacn.class)) {
			SwfComponentSacn swfComponentSacn = (SwfComponentSacn)configClass.getAnnotation(SwfComponentSacn.class);
			String path = swfComponentSacn.value();
			String javaPath = path;
			path = path.replace(".", "/");

			ClassLoader classLoader = this.getClass().getClassLoader();
			URL systemResource = classLoader.getSystemResource(path);
			File file = new File(systemResource.getFile());

			if (file.isDirectory()) {
				for (File listFile : file.listFiles()) {
					try {
						Class<?> aClass = classLoader.loadClass(javaPath + "." + listFile.getName().substring(0, listFile.getName().indexOf(".class")));
						// 检查是否使用了component;
						if (aClass.isAnnotationPresent(SwfComponent.class)) {
							// 判断是否当前接口类型
							if (SwfBeanPostProcessor.class.isAssignableFrom(aClass)) {
								postProcessorSet.add((SwfBeanPostProcessor) aClass.getConstructor().newInstance());
							}

							SwfComponent swfComponent = aClass.getAnnotation(SwfComponent.class);
							String beanName = swfComponent.value();
							if ("".equals(beanName)) {
								beanName = Introspector.decapitalize(aClass.getSimpleName());
							}
							BeanDefinition beanDefinition = new BeanDefinition();
							definitionMap.put(beanName, beanDefinition);
							beanDefinition.setBeanClass(aClass);
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
			}

		}
	}


	private void initializeBeans() {
		definitionMap.forEach((k,v) -> {
			createBean(v, k);
		});
	}


	/**
	 *
	 * 创建bean
	 *
	 * 实现功能列表
	 * <P>
	 *     1. 依赖注入;
	 *       (1)先看map；
	 *      （2） 没有则创建，循环此方法；
	 *           a.根据类型找到beanDefinition;
	 *      （3）暂不出来循环依赖场景
	 * </P>
	 *
	 * <p>
	 *     2. 创建后的回调
	 *       （1）检查是否实现接口；
	 *       （2）如果实现，直接调用父类方法，在创建后
	 * </p>
	 * <p>
	 *     3. AOP
	 * </p>
	 *
	 * @param definition
	 * @param beanName
	 * @return
	 */
	private Object createBean(BeanDefinition definition, String beanName) {
		Object beanObj = null;
		try {
			Class beanClass = definition.getBeanClass();
			beanObj = beanClass.newInstance();
			// 依赖注入
			// getDeclaredFields() 可获取包括私有的所有本类的一定义对象
			for (Field field : beanClass.getDeclaredFields()) {
				if (field.isAnnotationPresent(SwfAutowired.class)) {
					field.setAccessible(true);
					String fieldName = field.getName();
					if (!beanMap.containsKey(fieldName)) {
						BeanDefinition beanDefinition = definitionMap.get(fieldName);
						if (beanDefinition == null) {
							throw new NullPointerException("么有这个类型的字段bean!");
						}
						createBean(beanDefinition, fieldName);
					}
					Object val = beanMap.get(fieldName);
					field.set(beanObj, val);
				}
			}


			for (SwfBeanPostProcessor processor : postProcessorSet) {
				beanObj = processor.beforeProcessor(beanObj, beanName);
			}

			//回调
			if (beanObj instanceof SwfInitializingBean) {
				((SwfInitializingBean)beanObj).afterBreak();
			}

			for (SwfBeanPostProcessor processor : postProcessorSet) {
				beanObj = processor.afterProcessor(beanObj, beanName);
			}

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		beanMap.put(beanName, beanObj);
		return  beanObj;
	}


	public Object getBean(String beanName) {
		if (!beanMap.containsKey(beanName)) {
			throw  new ArrayIndexOutOfBoundsException("这个么么有");
		}

		BeanDefinition beanDefinition = definitionMap.get(beanName);
		if (beanDefinition.getScope().equals("sing")) {
			return beanMap.get(beanName);
		} else {
			return createBean(beanDefinition, beanName);
		}
	}


}
