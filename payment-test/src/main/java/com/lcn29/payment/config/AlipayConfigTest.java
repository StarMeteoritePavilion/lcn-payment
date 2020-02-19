package com.lcn29.payment.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.lcn29.payment.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;

/**
 * <pre>
 *
 * </pre>
 *
 * @author LCN
 * @date 2020-02-19 18:06
 */
@Slf4j
public class AlipayConfigTest extends BaseTest {

    @Resource
    private AlipayClient alipayClient;

    @Value("${alipay.tenantId}")
    private String tenantId;

    @Test
    public void testConfig() throws AlipayApiException {

        // 支付宝当面付，已下单，通过这个接口，可以获取到支付的二维码，
        // 用户可以通过这个扫码支付
        AlipayTradePrecreateRequest request = createRequest();
        // 调用接口
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        System.out.println(JSONObject.toJSONString(response));
        if (response.isSuccess()) {
            log.info("获取支付二维码成功");
            System.out.println("获取支付二维码成功");
        } else {
            log.error("获取支付二维码失败");
        }
    }

    /**
     * 封装请求参数
     * @return
     */
    private AlipayTradePrecreateRequest createRequest() {

        JSONObject json = new JSONObject();

        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = "tradeprecreate" + System.currentTimeMillis() + (long) (Math.random() * 10000000L);
        json.put("out_trade_no", outTradeNo);

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        json.put("seller_id", tenantId);

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        json.put("total_amount", 88.88);

        // 打折金额
        json.put("discountable_amount", 8.88);

        // 不可打折金额
        json.put("undiscountable_amount", 80);

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        json.put("subject", "xxx品牌xxx门店当面付扫码消费");

        JSONObject goods1 = new JSONObject();

        goods1.put("goods_id","goods_id001");
        goods1.put("goods_name", "xxx小面包");
        goods1.put("price", "1000");
        goods1.put("quantity", 1);

        JSONObject goods2 = new JSONObject();

        goods2.put("goods_id","goods_id002");
        goods2.put("goods_name", "xxx牙刷");
        goods2.put("price", "500");
        goods2.put("quantity", 2);

        JSONArray goodArr = new JSONArray();
        goodArr.add(goods1);
        goodArr.add(goods2);
        // 物品列表
        json.put("goods_detail", goodArr);

        // 对交易或商品的描述
        json.put("body", "购买商品3件共80.00元");
        // 产品 code, 默认为 FACE_TO_FACE_PAYMENT
        json.put("product_code", "FACE_TO_FACE_PAYMENT");
        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        json.put("operator_id", "test_operator_id");
        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        json.put("store_id", "test_store_id");
        // 禁用渠道，用户不可用指定渠道支付, 多个通过逗号隔开, 余额等，具体查看 https://docs.open.alipay.com/common/wifww7
        // json.put("disable_pay_channels", "debitCardExpress");
        // 支持的渠道， 和禁用渠道互斥, 2个不能同时存在
        json.put("enable_pay_channels","moneyFund,balance,bankPay");
        // 商户机具终端编号
        json.put("terminal_id", "NJ_T_001");

        // 业务扩展参数，
        JSONObject extendParamJson = new JSONObject();
        extendParamJson.put("sys_service_provider_id", "2088100200300400500");
        json.put("extend_params", extendParamJson);

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d, 不支持小数点
        json.put("timeout_express", "120m");

        System.out.println("请求参数--->" + JSONObject.toJSONString(json));
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizContent(JSONObject.toJSONString(json));
        return request;
    }
}
