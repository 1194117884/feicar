package com.lf.car.service;

import com.lf.car.entity.VerificationCode;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.repository.VerificationCodeRepository;
import com.lf.car.util.DateUtil;
import com.lf.car.util.JuheSmsUtil;
import com.lf.car.util.VerificationCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Service
public class VerificationCodeService {

    private static final int SMS_CODE_MAX_ONE_DAY = 10;
    private static final int SMS_CODE_TIMEOUT = 6;
    private static Logger logger = LoggerFactory.getLogger(VerificationCodeService.class);

    @Autowired
    private JuheSmsUtil juheSmsUtil;
    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    public void sendCode(String phone, String type) {
        logger.info("向手机：{}发送类型：{}验证码", phone, type);
        //valid
        validSendCodeArgs(phone, type);
        //get
        String code = getOneCode(4);
        //send
//        sendSmsCode(phone, code);//TODO:
        //record
        saveOneCode(phone, code, type);
    }

    private void sendSmsCode(String phone, String code) {
        juheSmsUtil.sendCodeSms(phone, code);
    }


    public String getOneCode(double weishu) {
        int random = (int) (Math.random() * Math.pow(10.0d, weishu));
        return String.valueOf(random);
    }

    private void validSendCodeArgs(String phone, String type) {
        //参数
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(type))
            throw new CarException(ErrorCode.PARAM_ERROR);
        //手机号
        if (!phone.startsWith("1") || phone.length() != 11)
            throw new CarException(ErrorCode.SMS_PHONE_ERROR);
        //类型
        boolean rightType = false;
        for (VerificationCodeEnum e : VerificationCodeEnum.values()) {
            if (e.getValue().equals(type)) rightType = true;
        }
        if (!rightType)
            throw new CarException(ErrorCode.PARAM_ERROR);
        //发送频率
        Date endTime = DateUtil.getDayStart(new Date());
        Date startTime = DateUtil.addDay(endTime, 1);
        BigInteger count = verificationCodeRepository.countByPhoneAndCreateTimeBetween(phone, startTime, endTime);
        if (count != null && count.intValue() >= SMS_CODE_MAX_ONE_DAY)
            throw new CarException(ErrorCode.SMS_MAX_SEND_ERROR);

    }

    private void saveOneCode(String phone, String code, String type) {
        VerificationCode one = new VerificationCode();
        one.setCode(code);
        one.setCreateTime(new Date());
        one.setPhone(phone);
        one.setStatus(0);
        one.setType(type);
        verificationCodeRepository.saveAndFlush(one);
    }

    public boolean verifyCode(String phone, VerificationCodeEnum vcEnum, String code) {
        logger.info("验证手机：{}类型为：{}验证码", phone, vcEnum);
        //valid
        validVerificationCodeArgs(phone, vcEnum, code);
        //check
        boolean check = checkCode(phone, vcEnum, code);
        //update record
        if (check)
            updateOneCode(phone, vcEnum);
        return check;
    }

    private boolean checkCode(String phone, VerificationCodeEnum vcEnum, String code) {
        List<VerificationCode> verificationCodes = verificationCodeRepository.findByPhoneAndTypeAndStatus(phone, vcEnum.getValue(), 0);
        if (verificationCodes == null || verificationCodes.size() < 1)
            throw new CarException(ErrorCode.SMS_CODE_TIMEOUT_ERROR);

        boolean valid = false;
        boolean check = false;
        for (VerificationCode verificationCode : verificationCodes){
            if (code.equals(verificationCode.getCode())){
                check = true;
                if(new Date().before(DateUtil.addMinute(verificationCode.getCreateTime(), SMS_CODE_TIMEOUT))){
                    return true;
                } else {
                    valid = false;
                    continue;
                }
            }
        }
        if (!check)
            throw new CarException(ErrorCode.SMS_CODE_ERROR);
        if (!valid)
            throw new CarException(ErrorCode.SMS_CODE_TIMEOUT_ERROR);
        return true;
    }

    private void updateOneCode(String phone, VerificationCodeEnum vcEnum) {
        List<VerificationCode> verificationCodes = verificationCodeRepository.findByPhoneAndTypeAndStatus(phone, vcEnum.getValue(), 0);
        if (verificationCodes == null) return;

        for (VerificationCode verificationCode : verificationCodes){
            verificationCode.setStatus(1);
        }
        verificationCodeRepository.saveAll(verificationCodes);
    }

    private void validVerificationCodeArgs(String phone, VerificationCodeEnum vcEnum, String code) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(vcEnum) || StringUtils.isEmpty(code))
            throw new CarException(ErrorCode.PARAM_ERROR);
        //手机号
        if (!phone.startsWith("1") || phone.length() != 11)
            throw new CarException(ErrorCode.SMS_PHONE_ERROR);
        //类型
        boolean rightType = false;
        for (VerificationCodeEnum e : VerificationCodeEnum.values()) {
            if (e.equals(vcEnum)) rightType = true;
        }
        if (!rightType)
            throw new CarException(ErrorCode.PARAM_ERROR);
    }


}
