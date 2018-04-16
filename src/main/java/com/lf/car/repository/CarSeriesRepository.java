package com.lf.car.repository;

import com.lf.car.entity.CarSeries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarSeriesRepository extends JpaRepository<CarSeries, Long> {

    CarSeries findOneById(Long id);

    Page<CarSeries> findAll(Specification<CarSeries> specification, Pageable pageable);

    List<CarSeries> findByStatus(int status);
}
