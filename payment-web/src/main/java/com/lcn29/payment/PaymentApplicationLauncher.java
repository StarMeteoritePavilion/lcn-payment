package com.lcn29.payment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <pre>
 * Application Launcher
 * </pre>
 *
 * @author LCN
 * @date 2020-02-16 09:47
 */
@SpringBootApplication
@MapperScan("com.lcn29.mapper")
public class PaymentApplicationLauncher {

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplicationLauncher.class, args);
    }
}
