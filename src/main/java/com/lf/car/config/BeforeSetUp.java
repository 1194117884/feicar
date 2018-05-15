package com.lf.car.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@Configuration
public class BeforeSetUp implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger logger = LoggerFactory.getLogger(BeforeSetUp.class);

    @Autowired
    private LeanCloudConfig leanCloudConfig;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("spring加载完毕，开始初始化......");
        leanCloudConfig.init();
    }

}