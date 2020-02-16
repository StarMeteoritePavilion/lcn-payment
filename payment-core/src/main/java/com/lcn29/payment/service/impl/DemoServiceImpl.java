package com.lcn29.payment.service.impl;


import com.lcn29.payment.service.DemoService;
import org.springframework.stereotype.Service;

/**
 * <pre>
 *
 * </pre>
 *
 * @author LCN
 * @date 2020-02-10 15:28
 */

@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello() {
        return "Hello";
    }
}
