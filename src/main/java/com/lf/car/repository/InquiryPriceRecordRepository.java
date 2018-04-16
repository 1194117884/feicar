package com.lf.car.repository;

import com.lf.car.entity.InquiryPriceRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryPriceRecordRepository extends JpaRepository<InquiryPriceRecord, Long> {

    Page<InquiryPriceRecord> findAll(Specification<InquiryPriceRecord> specification, Pageable pageable);

}
