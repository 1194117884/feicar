package com.lf.car.service;

import com.alibaba.fastjson.JSONObject;
import com.lf.car.entity.Menu;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.repository.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class MenuService {

    private static Logger logger = LoggerFactory.getLogger(MenuService.class);

    @Autowired
    private MenuRepository menuRepository;

    public Menu createOneMenu(Menu menu) {
        logger.info("保存一个菜单:{}", JSONObject.toJSONString(menu));
        //valid
        validCreateArgs(menu);
        //save
        return menuRepository.saveAndFlush(menu);
    }

    private void validCreateArgs(Menu menu) {
        if (menu == null || StringUtils.isEmpty(menu.getName()))
            throw new CarException(ErrorCode.PARAM_ERROR);

        if (menu.getType() == null) menu.setType(0);
        if (menu.getStatus() == null) menu.setStatus(0);

        if (menu.getType() == 0) {//目录
            menu.setPmId(null);
        } else {//菜单
            if (menu.getPmId() == null)
                throw new CarException(ErrorCode.PARAM_ERROR);
        }
    }

    public Menu updateOneMenu(Menu menu) {
        logger.info("更新一个菜单:{}", JSONObject.toJSONString(menu));
        //valid
        validUpdateArgs(menu);
        //update
        return menuRepository.saveAndFlush(menu);
    }

    private void validUpdateArgs(Menu menu) {
        if (menu == null || menu.getId() == null)
            throw new CarException(ErrorCode.PARAM_ERROR);
        if (menu.getType() == null || menu.getStatus() == null)
            throw new CarException(ErrorCode.PARAM_ERROR);
        if (menu.getType() == 0) {//目录
            menu.setPmId(null);
        } else {//菜单
            if (menu.getPmId() == null)
                throw new CarException(ErrorCode.PARAM_ERROR);
        }

    }

    public void deleteOneMenu(Menu menu) {
        logger.info("删除一个菜单:{}", JSONObject.toJSONString(menu));
        //valid
        validDeleteArgs(menu);
        //delete
        menuRepository.delete(menu);
    }

    private void validDeleteArgs(Menu menu) {
        if (menu == null || menu.getId() == null)
            throw new CarException(ErrorCode.PARAM_ERROR);

    }

    public Menu findOneMenu(Long id) {
        logger.info("查询一个菜单:{}", id);
        if (id == null)
            throw new CarException(ErrorCode.PARAM_ERROR);

        return menuRepository.findOneById(id);
    }

    public List<Menu> findMenuLink() {
        logger.info("查看当前的菜单链");
        //目录
        List<Menu> directoryList = menuRepository.findByStatusAndType(0, 0);
        if (directoryList == null || directoryList.size() < 1) return null;
        //拼接菜单
        for (Menu menu : directoryList) {
            List<Menu> menuList = menuRepository.findByStatusAndTypeAndPmId(0, 1, menu.getId());
            if (menuList == null || menuList.size() < 1) continue;

            menu.setSubMenuList(menuList);
        }

        return directoryList;
    }
}
