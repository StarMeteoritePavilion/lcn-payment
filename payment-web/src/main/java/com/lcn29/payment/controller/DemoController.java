package com.lcn29.payment.controller;

import com.alipay.api.AlipayClient;
import com.lcn29.payment.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <pre>
 *
 * </pre>
 *
 * @author LCN
 * @date 2020-02-10 15:29
 */

@RestController
public class DemoController {

    @Resource
    private DemoService demoService;

    @Resource
    private AlipayClient alipayClient;

    @GetMapping("/hello")
    public String sayhello() {

        System.out.println(demoService.hashCode() + "///" + demoService);

        System.out.println(alipayClient.hashCode() + "///" + alipayClient);

        return demoService.sayHello();
    }

}
