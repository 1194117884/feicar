package com.lf.car.repository;

import com.lf.car.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    List<VerificationCode> findByPhone(String phone);

    BigInteger countByPhoneAndCreateTimeBetween(String phone, Date startTime, Date endTime);

    List<VerificationCode> findByPhoneAndTypeAndStatus(String phone, String type, int status);
}
