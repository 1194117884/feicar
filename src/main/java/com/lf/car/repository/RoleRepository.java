package com.lf.car.repository;

import com.lf.car.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findByStatusAndIdIn(int status, List<Long> ids);

    Role findOneById(Long id);

}
