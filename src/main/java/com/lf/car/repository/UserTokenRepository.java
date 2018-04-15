package com.lf.car.repository;

import com.lf.car.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    UserToken findByTokenAndType(String token, String type);

    UserToken findByUserIdAndType(Long userId, String type);
}
