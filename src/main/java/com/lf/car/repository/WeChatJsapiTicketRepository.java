package com.lf.car.repository;

import com.lf.car.entity.WeChatJsapiTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeChatJsapiTicketRepository extends JpaRepository<WeChatJsapiTicket, Long> {

    WeChatJsapiTicket findFirstByOrderByIdDesc();

}
