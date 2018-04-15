package com.lf.car.service;

import com.alibaba.fastjson.JSONObject;
import com.lf.car.controller.requs.FindCarSeriesArgs;
import com.lf.car.entity.CarSeries;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.repository.CarSeriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarSeriesService {

    private static Logger logger = LoggerFactory.getLogger(CarSeriesService.class);

    @Autowired
    private CarSeriesRepository carSeriesRepository;

    public CarSeries createOneCarSeries(CarSeries carSeries) {
        logger.info("保存一个车系:{}", JSONObject.toJSONString(carSeries));
        //valid
        validCreateArgs(carSeries);
        //save
        return carSeriesRepository.saveAndFlush(carSeries);
    }

    private void validCreateArgs(CarSeries carSeries) {
        if (carSeries == null || StringUtils.isEmpty(carSeries.getName()))
            throw new CarException(ErrorCode.PARAM_ERROR);
        if (StringUtils.isEmpty(carSeries.getMainPic()) ||
                StringUtils.isEmpty(carSeries.getIntroduction()) ||
                StringUtils.isEmpty(carSeries.getType()))
            throw new CarException(ErrorCode.PARAM_ERROR);
        if (carSeries.getStatus() == null) carSeries.setStatus(0);
    }

    public CarSeries updateOneCarSeries(CarSeries carSeries) {
        logger.info("更新一个车系:{}", JSONObject.toJSONString(carSeries));
        //valid
        validUpdateArgs(carSeries);
        //update
        return carSeriesRepository.saveAndFlush(carSeries);
    }

    private void validUpdateArgs(CarSeries carSeries) {
        if (carSeries == null || carSeries.getId() == null)
            throw new CarException(ErrorCode.PARAM_ERROR);
        if (carSeries.getIntroduction() == null || carSeries.getStatus() == null)
            throw new CarException(ErrorCode.PARAM_ERROR);
    }

    public void deleteOneCarSeries(CarSeries carSeries) {
        logger.info("删除一个车系:{}", JSONObject.toJSONString(carSeries));
        //valid
        validDeleteArgs(carSeries);
        //delete
        carSeriesRepository.delete(carSeries);
    }

    private void validDeleteArgs(CarSeries carSeries) {
        if (carSeries == null || carSeries.getId() == null)
            throw new CarException(ErrorCode.PARAM_ERROR);

    }

    public CarSeries findOneCarSeries(Long id) {
        logger.info("查询一个车系:{}", id);
        if (id == null)
            throw new CarException(ErrorCode.PARAM_ERROR);

        return carSeriesRepository.findOneById(id);
    }


    public List<CarSeries> findCarsSeries(FindCarSeriesArgs args) {
        logger.info("查询车系");
        if (args == null)
            throw new CarException(ErrorCode.PARAM_ERROR);

        int pageSize = args.getPageSize() <= 0 ? 20 : args.getPageSize();
        int pageNum = args.getPageNum() <= 0 ? 1 : args.getPageNum();

        Page<CarSeries> page = carSeriesRepository.findAll(new Specification<CarSeries>() {
            @Override
            public Predicate toPredicate(Root<CarSeries> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> listPredicates = new ArrayList<>();
                if (args.getStatus() != null) {
                    listPredicates.add(cb.equal(root.get("status"), args.getStatus()));
                }

                Predicate[] arrayPredicates = new Predicate[listPredicates.size()];
                return cb.and(listPredicates.toArray(arrayPredicates));
            }
        }, new PageRequest(pageNum - 1, pageSize, new Sort(Sort.Direction.DESC, "createTime")));
        return page.getContent();
    }
}
