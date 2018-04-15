package com.lf.car.service;

import com.alibaba.fastjson.JSONObject;
import com.lf.car.entity.Method;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.repository.MethodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MethodService {

    private static Logger logger = LoggerFactory.getLogger(MenuService.class);

    @Autowired
    private MethodRepository methodRepository;


    public Method createOneMethod(Method method) {
        logger.info("新建一个新方法：{}", JSONObject.toJSONString(method));
        //valid
        validCrateArgs(method);
        //save
        return methodRepository.saveAndFlush(method);
    }

    private void validCrateArgs(Method method) {
        if (method == null || method.getMenuId() == null)
            throw new CarException(ErrorCode.PARAM_ERROR);
        if (StringUtils.isEmpty(method.getPath()) || StringUtils.isEmpty(method.getName()))
            throw new CarException(ErrorCode.PARAM_ERROR);
        if (method.getStatus() == null)
            method.setStatus(0);
    }

    public Method updateOneMethod(Method method) {
        logger.info("修改一个方法：{}", JSONObject.toJSONString(method));
        //valid
        validUpdateArgs(method);
        //save
        return methodRepository.saveAndFlush(method);
    }

    private void validUpdateArgs(Method method) {
        if (method == null || method.getId() == null || method.getMenuId() == null)
            throw new CarException(ErrorCode.PARAM_ERROR);
        if (StringUtils.isEmpty(method.getPath()) || StringUtils.isEmpty(method.getName()))
            throw new CarException(ErrorCode.PARAM_ERROR);
    }

    public void deleteOneMethod(Method method) {
        logger.info("删除一个方法：{}", JSONObject.toJSONString(method));
        //valid
        validDeleteArgs(method);
        //delete
        methodRepository.delete(method);
    }

    private void validDeleteArgs(Method method) {
        if (method == null || method.getId() == null)
            throw new CarException(ErrorCode.PARAM_ERROR);
    }

    public Method findOneMethod(Long id) {
        logger.info("查找方法：{}信息", id);
        if (id == null || id <= 0)
            throw new CarException(ErrorCode.PARAM_ERROR);

        return methodRepository.findOneById(id);
    }
}
