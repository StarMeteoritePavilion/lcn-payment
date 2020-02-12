package com.lcn29;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <pre>
 *
 * </pre>
 *
 * @author LCN
 * @date 2020-02-10 15:11
 */

@SpringBootApplication
@MapperScan("com.lcn29.mapper")
public class PaymentApplicationLauncher {

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplicationLauncher.class,args);
    }
}
