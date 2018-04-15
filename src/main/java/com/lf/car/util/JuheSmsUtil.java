package com.lf.car.util;

import com.lf.car.config.property.JuheSmsProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JuheSmsUtil {

    private static Logger logger = LoggerFactory.getLogger(JuheSmsUtil.class);
    @Autowired
    private HttpUtil httpUtil;
    @Autowired
    private JuheSmsProperty juheSmsProperty;

    public boolean sendCodeSms(String phone, String code) {
        logger.info("给手机：{}发送验证码：{}", phone, code);
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) return false;

        try {
            String url = buildCodeMsg(phone, code);
            String result = httpUtil.getResponse(url, "", 3000);
            logger.info("返回结果：{}", result);
            return result.contains("error_code\":0") && result.contains("操作成功");
        } catch (Exception e) {
            logger.error("发送验证码出错", e);
        }
        return false;
    }

    private String buildCodeMsg(String phone, String code) {
        return juheSmsProperty.getUrl()
                + "?mobile=" + phone
                + "&tpl_id=" + juheSmsProperty.getTpl_id()
                + "&tpl_value=%23code%23%3d" + code
                + "&dtype=" + juheSmsProperty.getDtype()
                + "&key=" + juheSmsProperty.getKey();
    }

}
