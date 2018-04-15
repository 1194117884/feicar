package com.lf.car.repository;

import com.lf.car.entity.Method;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MethodRepository extends JpaRepository<Method, Long> {

    List<Method> findByStatusAndIdIn(int status, List<Long> ids);

    Method findOneById(Long id);

    List<Method> findByStatusAndMenuIdIn(int status, List<Long> menuIds);
}
