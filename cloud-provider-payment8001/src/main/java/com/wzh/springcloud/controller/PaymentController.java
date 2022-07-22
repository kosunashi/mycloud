package com.wzh.springcloud.controller;

import com.wzh.springcloud.entities.CommonResult;
import com.wzh.springcloud.entities.Payment;
import com.wzh.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private DiscoveryClient discoveryClient;

    @PostMapping("/payment/create")
    public CommonResult create(Payment payment){
        int result = paymentService.create(payment);
        log.info("*****插入结果：" + result);
        if (result > 0){
            return new CommonResult(200, "插入数据库成功, serverPort: " + serverPort, result);
        } else {
            return new CommonResult(444, "插入数据库失败", null);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("*****插入结果：" + payment);
        if (payment != null){
            return new CommonResult(200, "查询成功, serverPort: " + serverPort, payment);
        }else {
            return new CommonResult(444, "没有对应记录, 查询ID：" + id, null);
        }
    }

    @GetMapping("/payment/discovery")
    public Object discovery(){
        //获得服务清单列表
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("*******server: " + service);
        }

        //根据微服务的具体服务名称 进一步获得与这个微服务相关的信息 举例: 获取CLOUD-PAYMENT-SERVICE	的相关信息
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }
        return this.discoveryClient;
    }

}
