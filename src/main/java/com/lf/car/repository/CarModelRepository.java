package com.lf.car.repository;

import com.lf.car.entity.CarModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarModelRepository extends JpaRepository<CarModel, Long> {

    CarModel findOneById(Long id);

    Page<CarModel> findAll(Specification<CarModel> specification, Pageable pageable);

}