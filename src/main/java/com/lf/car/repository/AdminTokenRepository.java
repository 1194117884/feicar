package com.lf.car.repository;

import com.lf.car.entity.AdminToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminTokenRepository extends JpaRepository<AdminToken, Long> {

    AdminToken findByTokenAndType(String token, String type);

    AdminToken findByAdminIdAndType(Long adminId, String type);
}
