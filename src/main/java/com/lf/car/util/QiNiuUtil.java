package com.lf.car.util;

import com.lf.car.config.property.QiNiuProperty;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QiNiuUtil {
    private Logger logger = LoggerFactory.getLogger(QiNiuUtil.class);

    @Autowired
    private QiNiuProperty qiNiuProperty;

    public String getUploadToken() {
        Auth auth = Auth.create(qiNiuProperty.getAccessKey(), qiNiuProperty.getSecretKey());
        String uploadToken = auth.uploadToken(qiNiuProperty.getBucket());
        return uploadToken;
    }

    public String getPhotoUrl(String photoUrl) {
        String uploadPhotoUrl = qiNiuProperty.getDomain() + photoUrl;
        return uploadPhotoUrl;
    }


}