package com.lf.car.repository;

import com.lf.car.entity.WeChatAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeChatAccessTokenRepository extends JpaRepository<WeChatAccessToken, Long> {

    WeChatAccessToken findLastByOrderByIdAsc();

}
