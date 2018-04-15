package com.lf.car.service;

import com.alibaba.fastjson.JSONObject;
import com.lf.car.entity.Role;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RoleService {

    private static Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleRepository roleRepository;

    public Role createOneRole(Role role) {
        logger.info("新建一个新角色：{}", JSONObject.toJSONString(role));
        //valid
        validCreateArgs(role);
        //save
        return roleRepository.saveAndFlush(role);
    }

    private void validCreateArgs(Role role) {
        if (role == null || StringUtils.isEmpty(role.getName()))
            throw new CarException(ErrorCode.PARAM_ERROR);
        if (StringUtils.isEmpty(role.getMenuIds()))
            throw new CarException(ErrorCode.PARAM_ERROR);
        if (StringUtils.isEmpty(role.getIntroduction()))
            role.setIntroduction(role.getName());
        if (role.getStatus() == null)
            role.setStatus(0);

    }

    public Role updateOneRole(Role role) {
        logger.info("更新一个角色：{}", JSONObject.toJSONString(role));
        //valid
        validUpdateArgs(role);
        //save
        return roleRepository.saveAndFlush(role);
    }

    private void validUpdateArgs(Role role) {
        if (role == null || role.getId() == null)
            throw new CarException(ErrorCode.PARAM_ERROR);
        if (StringUtils.isEmpty(role.getName()) || StringUtils.isEmpty(role.getMenuIds()))
            throw new CarException(ErrorCode.PARAM_ERROR);
    }

    public void deleteOneRole(Role role) {
        logger.info("删除一个角色：{}", JSONObject.toJSONString(role));
        //valid
        validDeleteArgs(role);
        //save
         roleRepository.delete(role);
    }

    private void validDeleteArgs(Role role) {
        if (role == null || role.getId() == null)
            throw new CarException(ErrorCode.PARAM_ERROR);

    }

    public Role findOneRole(Long id) {
        logger.info("查询角色：{}信息", id);
        if (id == null)
            throw new CarException(ErrorCode.PARAM_ERROR);

        return roleRepository.findOneById(id);
    }
}
