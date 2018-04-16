package com.lf.car.service;

import com.lf.car.controller.requs.FindInquiriesArgs;
import com.lf.car.entity.CarModel;
import com.lf.car.entity.CarSeries;
import com.lf.car.entity.InquiryPriceRecord;
import com.lf.car.exception.CarException;
import com.lf.car.exception.ErrorCode;
import com.lf.car.repository.CarModelRepository;
import com.lf.car.repository.CarSeriesRepository;
import com.lf.car.repository.InquiryPriceRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class InquiryPriceRecordService {

    private static Logger logger = LoggerFactory.getLogger(InquiryPriceRecordService.class);

    @Autowired
    private CarSeriesRepository carSeriesRepository;
    @Autowired
    private CarModelRepository carModelRepository;
    @Autowired
    private InquiryPriceRecordRepository inquiryPriceRecordRepository;


    public void inquiryOnePrice(InquiryPriceRecord record) {
        logger.info("发起一次询价");

        validInquiryPrice(record);

        inquiryPriceRecordRepository.saveAndFlush(record);
    }

    private void validInquiryPrice(InquiryPriceRecord record) {
        if (record == null || StringUtils.isEmpty(record.getUserPhone()))
            throw new CarException(ErrorCode.PARAM_ERROR);
        if (record.getSeriesId() == null || record.getModelId() == null)
            throw new CarException(ErrorCode.PARAM_ERROR);
        String phone = record.getUserPhone();
        if (phone.length() != 11 || !phone.startsWith("1"))
            throw new CarException(ErrorCode.SMS_PHONE_ERROR);
        record.setStatus(0);

    }


    public List<InquiryPriceRecord> findUserInquiryRecords(long userId, FindInquiriesArgs args) {
        logger.info("查询用户：{}的询问记录", userId);
        if (userId <= 0 )
            throw new CarException(ErrorCode.PARAM_ERROR);

        int pageSize = args.getPageSize() <= 0 ? 20 : args.getPageSize();
        int pageNum = args.getPageNum() <= 0 ? 1 : args.getPageNum();

        Page<InquiryPriceRecord> page = inquiryPriceRecordRepository.findAll(new Specification<InquiryPriceRecord>() {
            @Override
            public Predicate toPredicate(Root<InquiryPriceRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> listPredicates = new ArrayList<>();

                listPredicates.add(cb.equal(root.get("userId"), userId));

                Predicate[] arrayPredicates = new Predicate[listPredicates.size()];
                return cb.and(listPredicates.toArray(arrayPredicates));
            }
        }, new PageRequest(pageNum - 1, pageSize, new Sort(Sort.Direction.DESC, "createTime")));

        List<InquiryPriceRecord> content = page.getContent();
        //添加车型车系
        appendModelAndSeries(content);
        return  content;
    }

    private void appendModelAndSeries(List<InquiryPriceRecord> list) {
        if(list != null && list.size() < 1) return;

        for (InquiryPriceRecord record : list){
            CarModel carModel = carModelRepository.findOneById(record.getModelId());
            if (carModel == null) continue;
            CarSeries carSeries = carSeriesRepository.findOneById(record.getSeriesId());
            if (carSeries == null) continue;

            carModel.setSeriesInfo(carSeries);
            record.setCarModel(carModel);
        }
    }
}
