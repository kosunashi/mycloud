package com.wzh.springcloud.service;

import com.wzh.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {

    /**写操作*/
    public int create(Payment payment);

    /**根据id获取payment*/
    public Payment getPaymentById(@Param("id") Long id);

}
