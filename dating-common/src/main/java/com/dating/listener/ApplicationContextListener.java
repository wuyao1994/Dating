package com.dating.listener;

import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.dating.annotation.BaseService;

/**
 * spring容器初始化完成事件
 */
@Component
public class ApplicationContextListener
		implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ApplicationContextListener.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		// root application context
		if (null == contextRefreshedEvent.getApplicationContext().getParent()) {
			LOGGER.debug(">>>>> spring初始化完毕 <<<<<");
			// spring初始化完毕后，通过反射调用所有使用BaseService注解的initMapper方法
			Map<String, Object> baseServices = contextRefreshedEvent
					.getApplicationContext().getBeansWithAnnotation(BaseService.class);
			for (Object service : baseServices.values()) {
				LOGGER.debug(">>>>> {}.initMapper()", service.getClass().getName());
				try {
					Method initMapper = service.getClass().getMethod("initMapper");
					initMapper.invoke(service);
				}
				catch (Exception e) {
					LOGGER.error("初始化BaseService的initMapper方法异常", e);
					e.printStackTrace();
				}
			}

		}
	}

}
