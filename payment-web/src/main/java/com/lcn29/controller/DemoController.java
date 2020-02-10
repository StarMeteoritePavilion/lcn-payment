package com.lcn29.controller;

import com.lcn29.DemoService;
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

    @GetMapping("/hello")
    public String sayhello() {
        return demoService.sayHello();
    }

}
