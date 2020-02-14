package com.lcn29.payment;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.lcn29.payment.constants.PaymentConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Optional;

/**
 * <pre>
 * alipay client config
 *
 *
 *
 *
 * </pre>
 *
 * @author LCN
 * @date 2020-02-13 21:04
 */

@Configuration
public class AlipayConfig {

    /** the default key to create alipayClient  */
    private static final String KEY_CREATE_TYPE = "key";

    /** create alipayClient by cert */
    private static final String CERT_CREATE_TYPE = "cert";

    /** the default key to encrypt */
    private static final String SIGN_TYPE = "RSA2";

    /** the request format */
    private static final String REQUEST_FORMAT = "json";

    /** character  */
    private static final String CHARSET = "utf-8";


    @Autowired
    protected Environment config;

    @Bean
    public AlipayClient createAlipayClient() throws AlipayApiException {

        String appId = Optional.ofNullable(config.getProperty(PaymentConfig.Alipay.appId))
            .orElseThrow(() -> createAlipayApiException(PaymentConfig.Alipay.appId));

        String apiGateWayUrl = Optional.ofNullable(config.getProperty(PaymentConfig.Alipay.gateWayUrl))
            .orElseThrow(() -> createAlipayApiException(PaymentConfig.Alipay.gateWayUrl));

        String appPrivateKey = Optional.ofNullable(config.getProperty(PaymentConfig.Alipay.appPrivateKey))
            .orElseThrow(() -> createAlipayApiException(PaymentConfig.Alipay.appPrivateKey));

        String signType = config.getProperty(PaymentConfig.Alipay.signType, SIGN_TYPE);
        String createType = config.getProperty(PaymentConfig.Alipay.createType, KEY_CREATE_TYPE);

        // alipay client create by certificate
        if (createType.equals(CERT_CREATE_TYPE)) {
            return createAlipayClientByCert(apiGateWayUrl, appId, appPrivateKey, signType);
        }

        // alipay client create by private key and public key
        return creatAlipayClientByKey(apiGateWayUrl, appId, appPrivateKey, signType);
    }

    /**
     * create alipayClient by private key and public key
     * @param apiGateWayUrl
     * @param appId
     * @param signType
     * @return
     * @throws AlipayApiException
     */
    private AlipayClient creatAlipayClientByKey(String apiGateWayUrl, String appId, String appPrivateKey, String signType) throws AlipayApiException {

        String platformPublicKey = Optional.ofNullable(config.getProperty(PaymentConfig.Alipay.platformPublicKey))
            .orElseThrow(() -> createAlipayApiException(PaymentConfig.Alipay.platformPublicKey));

        return new DefaultAlipayClient(apiGateWayUrl, appId, appPrivateKey, REQUEST_FORMAT, CHARSET, platformPublicKey, signType);
    }

    /**
     * create alipayClient by certificate
     * @param apiGateWayUrl
     * @param appId
     * @param signType
     * @return
     * @throws AlipayApiException
     */
    private AlipayClient createAlipayClientByCert(String apiGateWayUrl, String appId, String appPrivateKey, String signType) throws AlipayApiException {

        // 支付宝根证书文件路径
        String rootCertPath = Optional.ofNullable(config.getProperty(PaymentConfig.Alipay.rootCertPath))
            .orElseThrow(() -> createAlipayApiException(PaymentConfig.Alipay.rootCertPath));

        // 支付宝公钥证书文件路径
        String alipayPublicCertPath = Optional.ofNullable(config.getProperty(PaymentConfig.Alipay.alipayPublicCertPath))
            .orElseThrow(() -> createAlipayApiException(PaymentConfig.Alipay.alipayPublicCertPath));

        // 应用公钥证书文件路径
        String appCertPath = Optional.ofNullable(config.getProperty(PaymentConfig.Alipay.appCertPath))
            .orElseThrow(() -> createAlipayApiException(PaymentConfig.Alipay.appCertPath));

        CertAlipayRequest certParams = new CertAlipayRequest();
        certParams.setRootCertPath(rootCertPath);
        certParams.setAlipayPublicCertPath(alipayPublicCertPath);
        certParams.setCertPath(appCertPath);

        certParams.setPrivateKey(appPrivateKey);
        certParams.setServerUrl(apiGateWayUrl);
        certParams.setAppId(appId);
        certParams.setSignType(signType);
        certParams.setCharset(CHARSET);
        certParams.setFormat(REQUEST_FORMAT);

        return new DefaultAlipayClient(certParams);
    }

    /**
     * create a AlipayApiException
     * @param config
     * @return
     */
    private AlipayApiException createAlipayApiException(String config) {
        String msg = String.format("the config of \" %s \" is must be set", config);
        return new AlipayApiException(msg);
    }

}
