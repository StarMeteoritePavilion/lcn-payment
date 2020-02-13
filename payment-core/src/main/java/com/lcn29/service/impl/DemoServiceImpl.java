package com.lcn29.service.impl;


import com.lcn29.service.DemoService;
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
