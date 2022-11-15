package com.study.tuling.spring.eventLisener;

import org.springframework.context.ApplicationEvent;
import org.w3c.dom.events.Event;

/**
 * @author wfsong
 * @date 2022/11/15 15:46
 */
public class OrderEvent extends ApplicationEvent {
	public OrderEvent(Object source) {
		super(source);
	}

	@Override
	public Object getSource() {
		return super.getSource();
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
