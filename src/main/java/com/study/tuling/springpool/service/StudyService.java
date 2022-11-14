package com.study.tuling.springpool.service;

import com.study.tuling.springpool.annotations.SwfComponent;
import com.study.tuling.springpool.interfaces.DynamicProxyInterface;

/**
 * q
 *
 * @author wfsong
 * @date 2022/11/9 19:05
 */
@SwfComponent
public class StudyService implements DynamicProxyInterface {

	public void sout(){
		System.out.println("StudyService");
	}

	@Override
	public void learn() {
		System.out.println("StudyService, learn()");
	}
}
