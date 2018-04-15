package com.lf.car.util;

import com.lf.car.config.property.WeChatProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class WeChatSignatureUtil {

    @Autowired
    private WeChatProperty weChatProperty;

    public boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[]{weChatProperty.getToken(), timestamp, nonce};
        // 排序
        Arrays.sort(arr);
        // 生成字符串
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        // sha256加密
        String temp = SHA1Util.getSHA1StrJava(content.toString());

        return temp.equals(signature); // 与微信传递过来的签名进行比较
    }
}
