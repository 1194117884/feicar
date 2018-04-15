package com.lf.car.repository;

import com.lf.car.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    Menu findOneById(long id);

    List<Menu> findByStatusAndTypeAndIdIn(int status, int type, List<Long> ids);

    List<Menu> findByStatusAndTypeAndPmIdIn(int status, int type, List<Long> pmIds);

    List<Menu> findByStatusAndType(int status, int type);

    List<Menu> findByStatusAndTypeAndPmId(int status, int type, Long pmId);
}
