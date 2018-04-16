package com.lf.car.repository;

import com.lf.car.entity.ReserveDriveRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReserveDriveRecordRepository extends JpaRepository<ReserveDriveRecord, Long> {

    Page<ReserveDriveRecord> findAll(Specification<ReserveDriveRecord> specification, Pageable pageable);

}
