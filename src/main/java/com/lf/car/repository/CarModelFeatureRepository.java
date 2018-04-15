package com.lf.car.repository;

import com.lf.car.entity.CarModelFeature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarModelFeatureRepository extends JpaRepository<CarModelFeature, Long> {


    CarModelFeature findOneById(Long id);

    List<CarModelFeature> findByModelId(Long modeld);

    Page<CarModelFeature> findAll(Specification<CarModelFeature> specification, Pageable pageable);

    void deleteByFeatureAndFeatureType(String feature, String featureType);

    void deleteBySeriesId(Long seriesId);

    List<CarModelFeature> findBySeriesId(long seriesId);
}
