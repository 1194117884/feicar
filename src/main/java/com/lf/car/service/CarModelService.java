package com.lf.car.service;

import com.alibaba.fastjson.JSONObject;
import com.lf.car.controller.requs.FindCarModelArgs;
import com.lf.car.entity.CarModel;
import com.lf.car.entity.CarModelFeature;
import com.lf.car.entity.CarSeries;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.repository.CarModelFeatureRepository;
import com.lf.car.repository.CarModelRepository;
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
import java.util.*;

@Service
public class CarModelService {

    private static Logger logger = LoggerFactory.getLogger(CarModelService.class);

    @Autowired
    private CarSeriesRepository carSeriesRepository;
    @Autowired
    private CarModelRepository carModelRepository;
    @Autowired
    private CarModelFeatureRepository carModelFeatureRepository;

    public CarModel createOneCarModel(CarModel carModel) {
        logger.info("保存一个车型:{}", JSONObject.toJSONString(carModel));
        //valid
        validCreateArgs(carModel);
        //save
        return carModelRepository.saveAndFlush(carModel);
    }

    private void validCreateArgs(CarModel carModel) {
        if (carModel == null || StringUtils.isEmpty(carModel.getName()))
            throw new CarException(ErrorCode.PARAM_ERROR);
        if (StringUtils.isEmpty(carModel.getSeriesId()))
            throw new CarException(ErrorCode.PARAM_ERROR);
        if (carModel.getStatus() == null) carModel.setStatus(0);

        carModel.setCreateTime(new Date());
    }

    public CarModel updateOneCarModel(CarModel carModel) {
        logger.info("更新一个车型:{}", JSONObject.toJSONString(carModel));
        //valid
        validUpdateArgs(carModel);
        //update
        return carModelRepository.saveAndFlush(carModel);
    }

    private void validUpdateArgs(CarModel carModel) {
        if (carModel == null || carModel.getId() == null)
            throw new CarException(ErrorCode.PARAM_ERROR);
        if (carModel == null || StringUtils.isEmpty(carModel.getName()))
            throw new CarException(ErrorCode.PARAM_ERROR);
        if (StringUtils.isEmpty(carModel.getSeriesId()))
            throw new CarException(ErrorCode.PARAM_ERROR);

        if (carModel.getStatus() == null) carModel.setStatus(0);
    }

    public void deleteOneCarModel(CarModel carModel) {
        logger.info("删除一个车型:{}", JSONObject.toJSONString(carModel));
        //valid
        validDeleteArgs(carModel);
        //delete
        carModelRepository.delete(carModel);
    }

    private void validDeleteArgs(CarModel carModel) {
        if (carModel == null || carModel.getId() == null)
            throw new CarException(ErrorCode.PARAM_ERROR);

    }

    public CarModel findOneCarModel(Long id) {
        logger.info("查询一个车型:{}", id);
        if (id == null)
            throw new CarException(ErrorCode.PARAM_ERROR);

        return carModelRepository.findOneById(id);
    }


    public List<CarModel> findCarModels(FindCarModelArgs args) {
        logger.info("查询车型");
        if (args == null)
            throw new CarException(ErrorCode.PARAM_ERROR);

        int pageSize = args.getPageSize() <= 0 ? 20 : args.getPageSize();
        int pageNum = args.getPageNum() <= 0 ? 1 : args.getPageNum();

        Page<CarModel> page = carModelRepository.findAll(new Specification<CarModel>() {
            @Override
            public Predicate toPredicate(Root<CarModel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> listPredicates = new ArrayList<>();
                if (args.getStatus() != null) {
                    listPredicates.add(cb.equal(root.get("status"), args.getStatus()));
                }
                if (args.getSeriesId() != null) {
                    listPredicates.add(cb.equal(root.get("seriesId"), args.getSeriesId()));
                }

                Predicate[] arrayPredicates = new Predicate[listPredicates.size()];
                return cb.and(listPredicates.toArray(arrayPredicates));
            }
        }, new PageRequest(pageNum - 1, pageSize, new Sort(Sort.Direction.DESC, "createTime")));

        return page.getContent();
    }

    private CarModel appendCarFeatures(CarModel carModel) {
        if (carModel == null) return null;

        CarSeries carSeries = carSeriesRepository.findOneById(carModel.getSeriesId());
        carModel.setSeriesInfo(carSeries);
        //car features
        carModel.setFeatures(buildFeatureMaps(carModelFeatureRepository.findByModelId(carModel.getId())));

        return carModel;
    }

    private Map<String, List<CarModelFeature>> buildFeatureMaps(List<CarModelFeature> features) {
        if (features == null || features.size() < 1) return null;
        Map<String, List<CarModelFeature>> maps = new HashMap<>();
        for (CarModelFeature feature : features) {
            String featureType = feature.getFeatureType();
            List<CarModelFeature> list = maps.get(featureType);
            if (list == null) {
                list = new ArrayList<>();
                maps.put(featureType, list);
            } else {
                if (!list.contains(feature))
                    list.add(feature);
            }
        }
        return maps;
    }

    public CarModel findCarModelInfoWithFeature(Long id) {
        logger.info("查询车型详细信息：{}", id);
        if (id == null)
            throw new CarException(ErrorCode.PARAM_ERROR);

        CarModel model = carModelRepository.findOneById(id);
        if (model == null) return null;
        //拼接特性信息
        return appendCarFeatures(model);
    }
}
