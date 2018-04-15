package com.lf.car.service;

import com.lf.car.controller.resps.HotForSlider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotSliderService {

    private static Logger logger = LoggerFactory.getLogger(HotSliderService.class);

    public List<HotForSlider> getHotSliderList() {
        logger.info("获取首页热门滚动条");
        List<HotForSlider> list = new ArrayList<>();

        HotForSlider slider1 = new HotForSlider();
        slider1.setImg("http://p6wghqgva.bkt.clouddn.com/FitA9NTg05M3HtCO7TjTtv_t_DQp.jpg");
        slider1.setHref("https://baidu.com");
        slider1.setIntroduction("这个是简介");
        slider1.setTitle("这个是标题");
        list.add(slider1);

        HotForSlider slider2 = new HotForSlider();
        slider2.setImg("http://p6wghqgva.bkt.clouddn.com/FitA9NTg05M3HtCO7TjTtv_t_DQp.jpg");
        slider2.setHref("https://sohu.com");
        slider2.setIntroduction("这个是简介这个是简介这个是简介这个是简介这个是简介这个是简介这个是简介这个是简介这个是简介这个是简介这个是简介");
        slider2.setTitle("这个是标题");
        list.add(slider2);

        HotForSlider slider3 = new HotForSlider();
        slider3.setImg("http://p6wghqgva.bkt.clouddn.com/FitA9NTg05M3HtCO7TjTtv_t_DQp.jpg");
        slider3.setHref("https://163.com");
        slider3.setIntroduction("这个是简介");
        slider3.setTitle("这个是标题这个是标题这个是标题这个是标题这个是标题这个是标题这个是标题这个是标题这个是标题这个是标题这个是标题这个是标题这个是标题");
        list.add(slider3);

        HotForSlider slider4 = new HotForSlider();
        slider4.setImg("http://p6wghqgva.bkt.clouddn.com/FitA9NTg05M3HtCO7TjTtv_t_DQp.jpg");
        slider4.setHref("https://qq.com");
        slider4.setIntroduction("这个是简介这个是简介这个是简介这个是简介这个是简介这个是简介这个是简介这个是简介这个是简介这个是简介这个是简介这个是简介这个是简介这个是简介这个是简介这个是简介这个是简介");
        slider4.setTitle("这个是标题这个是标题这个是标题这个是标题这个是标题这个是标题这个是标题这个是标题这个是标题这个是标题这个是标题这个是标题这个是标题");
        list.add(slider4);

        return list;
    }

}
