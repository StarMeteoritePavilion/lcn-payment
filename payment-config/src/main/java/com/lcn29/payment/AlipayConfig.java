package com.lcn29.payment;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

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


    private static final String KEY_CREATE_TYPE = "key";

    private static final String CERT_CREATE_TYPE = "cert";

    private static final String REQUEST_FORMAT = "json";

    private static final String CHARSET = "utf-8";

    private static final String SIGN_TYPE = "RSA2";

    @Autowired
    protected Environment config;

    @Bean
    public AlipayClient createAlipayClient() throws AlipayApiException {

        String createType = config.getProperty("alipay.client.createType", KEY_CREATE_TYPE);

        // alipay client create by certificate
        if (createType.equals(CERT_CREATE_TYPE)) {
            return createAlipayClientByCert();
        }

        // alipay client create by private key and public key
        return creatAlipayClientByKey();
    }


    private AlipayClient creatAlipayClientByKey() throws AlipayApiException {

        String signType = config.getProperty("alipay.client.signType", SIGN_TYPE);

        String appId = config.getProperty("alipay.client.appId");
        if (StringUtils.isEmpty(appId)) {
            throw new AlipayApiException("the config of 'alipay.client.appId' is must be set");
        }

        String publicKey = config.getProperty("alipay.client.publicKey");
        if (StringUtils.isEmpty(publicKey)) {
            throw new AlipayApiException("the config of 'alipay.client.publicKey' is must be set");
        }

        String privateKey = config.getProperty("alipay.client.privateKey");
        if (StringUtils.isEmpty(privateKey)) {
            throw new AlipayApiException("the config of 'alipay.client.privateKey' is must be set");
        }

        String apiGateWayUrl = config.getProperty("alipay.client.openApiDomain");
        if (StringUtils.isEmpty(apiGateWayUrl)) {
            throw new AlipayApiException("the config of 'alipay.client.openApiDomain' is must be set");
        }

        return new DefaultAlipayClient(apiGateWayUrl, appId, privateKey, REQUEST_FORMAT, CHARSET, publicKey, signType);
    }

    private AlipayClient createAlipayClientByCert() throws AlipayApiException {



        CertAlipayRequest certParams = new CertAlipayRequest();

        String apiGateWayUrl = config.getProperty("alipay.client.openApiDomain");
        if (StringUtils.isEmpty(apiGateWayUrl)) {
            throw new AlipayApiException("the config of 'alipay.client.openApiDomain' is must be set");
        }
        certParams.setServerUrl(apiGateWayUrl);

        String appId = config.getProperty("alipay.client.appId");
        if (StringUtils.isEmpty(appId)) {
            throw new AlipayApiException("the config of 'alipay.client.appId' is must be set");
        }
        certParams.setAppId(appId);

        String privateKey = config.getProperty("alipay.client.privateKey");
        if (StringUtils.isEmpty(privateKey)) {
            throw new AlipayApiException("the config of 'alipay.client.privateKey' is must be set");
        }
        certParams.setPrivateKey(privateKey);

        // 支付宝根证书文件路径
        String rootCertPath = config.getProperty("alipay.client.rootCertPath");
        if (StringUtils.isEmpty(rootCertPath)) {
            throw new AlipayApiException("the config of 'alipay.client.rootCertPath' is must be set");
        }
        certParams.setRootCertPath(rootCertPath);

        // 支付宝公钥证书文件路径
        String alipayPublicCertPath = config.getProperty("alipay.client.alipayPublicCertPath");
        if (StringUtils.isEmpty(alipayPublicCertPath)) {
            throw new AlipayApiException("the config of 'alipay.client.alipayPublicCertPath' is must be set");
        }
        certParams.setAlipayPublicCertPath(alipayPublicCertPath);

        // 应用公钥证书文件路径
        String appCertPath = config.getProperty("alipay.client.appCertPath");
        if (StringUtils.isEmpty(appCertPath)) {
            throw new AlipayApiException("the config of 'alipay.client.appCertPath' is must be set");
        }
        certParams.setCertPath(appCertPath);

        certParams.setCharset(CHARSET);
        certParams.setFormat(REQUEST_FORMAT);
        String signType = config.getProperty("alipay.client.signType", SIGN_TYPE);
        certParams.setSignType(signType);
        AlipayClient alipayClient = new DefaultAlipayClient(certParams);
        return alipayClient;
    }
}
