package com.study.tuling.springpool.metadata;

import com.study.tuling.springpool.annotations.SwfComponent;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import java.io.IOException;
import java.util.Arrays;

/**
 * asm方式加载， 区别于直接用反射的是，不需要将字节码文件加载到中JVM中。直接用文件流的方式去读取
 *
 * 高效
 *
 * @author wfsong
 * @date 2022/11/17 19:34
 */
public class Test1 {

	public static void main(String[] args) throws IOException {
		SimpleMetadataReaderFactory factory = new SimpleMetadataReaderFactory();
		MetadataReader metadataReader = factory.getMetadataReader("com.study.tuling.springpool.service.UserService");
		ClassMetadata classMetadata = metadataReader.getClassMetadata();
		AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();

		System.out.println(classMetadata.getClassName());
		System.out.println(Arrays.toString(classMetadata.getInterfaceNames()));
		System.out.println(classMetadata.isAbstract());
		System.out.println(annotationMetadata.getAnnotations());
		System.out.println(annotationMetadata.hasAnnotation("com.study.tuling.springpool.annotations.SwfComponent"));
		// 查询@Target失败。、。
		System.out.println(annotationMetadata.hasMetaAnnotation("org.springframework.stereotype.Component"));




	}
}
