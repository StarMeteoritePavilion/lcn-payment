package com.lcn29.controller;

import com.alipay.api.AlipayClient;
import com.lcn29.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <pre>
 *
 * </pre>
 *
 * @author LCN
 * @date 2020-02-13 16:44
 */
@RestController
public class Demo2Controller {

    @Resource
    private DemoService demoService;

    @Resource
    private AlipayClient alipayClient;

    @GetMapping("/hello2")
    public String sayhello() {

        System.out.println(demoService.hashCode() + "///" + demoService);

        System.out.println(alipayClient.hashCode() + "///" + alipayClient);

        return demoService.sayHello();
    }
}
