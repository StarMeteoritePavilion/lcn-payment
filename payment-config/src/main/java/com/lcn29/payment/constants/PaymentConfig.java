package com.lcn29.payment.constants;

/**
 * <pre>
 *
 * </pre>
 *
 * @author LCN
 * @date 2020-02-14 11:13
 */
public class PaymentConfig {

    public interface Alipay {

        /** 创建方式  值为 key(公私钥创建) 或者为 cert(证书创建) 默认为 key */
        String createType = "alipay.createType";

        /** 加密方式 */
        String signType = "alipay.signType";

        /** 应用Id */
        String appId = "alipay.appId";

        /** 应用私钥 */
        String appPrivateKey = "alipay.appPrivateKey";

        /** 支付宝平台的公钥 */
        String platformPublicKey = "alipay.platformPublicKey";

        /** api 网关 url */
        String gateWayUrl = "alipay.apiGateWayUrl";

        /** 支付宝根证书文件路径 */
        String rootCertPath = "alipay.rootCertPath";

        /** 支付宝公钥证书文件路径 */
        String alipayPublicCertPath = "alipay.alipayPublicCertPath";

        /** 应用公钥证书文件路径 */
        String appCertPath = "alipay.appCertPath";
    }
}
