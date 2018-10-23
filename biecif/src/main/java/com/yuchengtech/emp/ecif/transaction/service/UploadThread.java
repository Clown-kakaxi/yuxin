package com.yuchengtech.emp.ecif.transaction.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ComponentScan(basePackageClasses = {ExcelUploadTask.class, ZipUploadTask.class})
public class UploadThread {
	private static ThreadPoolTaskExecutor pool = null;
	
	@Bean
	public static ThreadPoolTaskExecutor taskExecutor() {
		if (pool == null) {
			pool = new ThreadPoolTaskExecutor();  
            pool.setCorePoolSize(5);  
            pool.setMaxPoolSize(10);  
            pool.setWaitForTasksToCompleteOnShutdown(true);
		}
		return pool;
	}
}
