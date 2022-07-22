package com.wzh.springcloud.dao;

import com.wzh.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentDao {

    /**写操作*/
    public int create(Payment payment);

    /**根据id获取payment*/
    public Payment getPaymentById(@Param("id") Long id);

}
