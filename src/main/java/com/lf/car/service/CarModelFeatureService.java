package com.lf.car.service;

import com.lf.car.entity.CarModelFeature;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.model.CarModelFeatureMapping;
import com.lf.car.repository.CarModelFeatureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarModelFeatureService {

    private static Logger logger = LoggerFactory.getLogger(CarModelFeatureService.class);

    @Autowired
    private CarModelFeatureRepository carModelFeatureRepository;

    public void addFeatureToModels(String feature, String featureType, Long seriesId, Map<Long, String> modelsInfo) {
        logger.info("添加车型的特性");
        if (StringUtils.isEmpty(feature) || StringUtils.isEmpty(featureType)
                || seriesId == null || modelsInfo == null)
            throw new CarException(ErrorCode.PARAM_ERROR);
        List<CarModelFeature> list = new ArrayList<>();
        for (Long modelId : modelsInfo.keySet()) {
            CarModelFeature modelFeature = new CarModelFeature();
            modelFeature.setFeature(feature);
            modelFeature.setFeatureType(featureType);
            modelFeature.setModelId(modelId);
            modelFeature.setSeriesId(seriesId);
            modelFeature.setModelInfo(modelsInfo.get(modelId));

            list.add(modelFeature);
        }
        carModelFeatureRepository.saveAll(list);
    }

    @Transactional
    public void deleteFeatureBySeriesId(Long seriesId) {
        logger.info("删除车系的特性");
        if (StringUtils.isEmpty(seriesId))
            throw new CarException(ErrorCode.PARAM_ERROR);

        carModelFeatureRepository.deleteBySeriesId(seriesId);
    }

    public CarModelFeatureMapping findMappingBySeriesId(long seriesId) {
        logger.info("获取同一车系的所有车型开通状态");
        if (seriesId <= 0)
            throw new CarException(ErrorCode.PARAM_ERROR);
        List<CarModelFeature> features = carModelFeatureRepository.findBySeriesId(seriesId);
        CarModelFeatureMapping mapping = new CarModelFeatureMapping();
        if (features == null || features.size() < 1) {
            CarModelFeature simple = null;
            Map<Long, String> modelInfos = new HashMap<>();

            for (CarModelFeature feature : features) {
                if (simple == null) simple = feature;
                modelInfos.put(feature.getModelId(), feature.getModelInfo());
            }
            mapping.setModelInfos(modelInfos);
            mapping.setFeature(simple.getFeature());
            mapping.setFeatureType(simple.getFeatureType());
            mapping.setSeriesId(simple.getSeriesId());
        }
        return mapping;
    }

}
