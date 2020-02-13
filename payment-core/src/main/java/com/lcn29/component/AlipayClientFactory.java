package com.lcn29.component;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *  alipayclient factory
 * </pre>
 *
 * @author LCN
 * @date 2020-02-13 16:12
 */

@Component
public class AlipayClientFactory implements FactoryBean<AlipayClient> {

    private static final String REQUEST_FORMAT = "json";

    private static final String CHARSET = "utf-8";

    private static final String SIGN_TYPE = "RSA2";

    @Autowired
    protected Environment config;

    @Value("${alipay.application.tenantId}")
    private String privateKey;

    @Override
    public AlipayClient getObject() throws Exception {

        String signType = config.getProperty("alipay.application.signType", SIGN_TYPE);

        return new DefaultAlipayClient("", "", "");
    }

    @Override
    public Class<?> getObjectType() {
        return AlipayClient.class;
    }



}
