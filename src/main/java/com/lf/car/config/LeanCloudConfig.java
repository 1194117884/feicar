package com.lf.car.config;

import com.avos.avoscloud.AVOSCloud;
import com.lf.car.config.property.LeanCloudProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeanCloudConfig {

    private static Logger logger = LoggerFactory.getLogger(LeanCloudConfig.class);

    @Autowired
    private LeanCloudProperty leanCloudProperty;

    public void init() {
        logger.info("初始化leancloud信息");
        AVOSCloud.initialize(
                leanCloudProperty.getAppId(),
                leanCloudProperty.getAppKey(),
                leanCloudProperty.getMasterKey()
        );
        logger.info("leanCloud debug info :[{}]", leanCloudProperty.getDebug());
        //开启DBUG模式
        AVOSCloud.setDebugLogEnabled(leanCloudProperty.getDebug());
    }

}
