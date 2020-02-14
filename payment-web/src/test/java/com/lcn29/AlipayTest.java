package com.lcn29;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.ExtendParams;
import com.alipay.api.domain.GoodsDetail;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author LCN
 * @date 2020-02-14 15:13
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class AlipayTest {

    @Resource
    private AlipayClient alipayClient;

    @Value("${alipay.tenantId}")
    private String tenantId;

    @Test
    public void testConfig() throws AlipayApiException {

        AlipayTradePrecreateRequest request = createRequest();

        AlipayTradePrecreateResponse response = alipayClient.execute(request);

        System.out.println(JSONObject.toJSONString(response));

        if (response.isSuccess()) {
            System.out.println("获取支付二维码成功");
        } else {
            System.out.println("获取支付二维码失败");
        }

    }

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

        List<GoodsDetail> goodsDetailList = new ArrayList<>();
        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
        GoodsDetail goods1 =  new GoodsDetail();
        goods1.setGoodsId("goods_id001");
        goods1.setGoodsName("xxx小面包");
        goods1.setPrice("1000");
        goods1.setQuantity(1L);
        goodsDetailList.add(goods1);

        // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
        GoodsDetail goods2 = new GoodsDetail();
        goods2.setGoodsId("goods_id002");
        goods2.setGoodsName("xxx牙刷");
        goods2.setPrice("500");
        goods2.setQuantity(2L);
        goodsDetailList.add(goods2);

        json.put("goods_detail", goodsDetailList);









        String subject = "xxx品牌xxx门店当面付扫码消费";

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = "0.01";

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";


        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = "购买商品3件共20.00元";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";


        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();

        request.setBizContent("{" +
            "\"out_trade_no\":\"20150320010101001\"," +
            "\"seller_id\":\"2088102146225135\"," +
            "\"total_amount\":88.88," +
            "\"discountable_amount\":8.88," +
            "\"undiscountable_amount\":80," +
            "\"buyer_logon_id\":\"15901825620\"," +
            "\"subject\":\"Iphone6 16G\"," +
            "      \"goods_detail\":[{" +
            "        \"goods_id\":\"apple-01\"," +
            "\"alipay_goods_id\":\"20010001\"," +
            "\"goods_name\":\"ipad\"," +
            "\"quantity\":1," +
            "\"price\":2000," +
            "\"goods_category\":\"34543238\"," +
            "\"categories_tree\":\"124868003|126232002|126252004\"," +
            "\"body\":\"特价手机\"," +
            "\"show_url\":\"http://www.alipay.com/xxx.jpg\"" +
            "        }]," +
            "\"body\":\"Iphone6 16G\"," +
            "\"product_code\":\"FACE_TO_FACE_PAYMENT\"," +
            "\"operator_id\":\"yx_001\"," +
            "\"store_id\":\"NJ_001\"," +
            "\"disable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
            "\"enable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
            "\"terminal_id\":\"NJ_T_001\"," +
            "\"extend_params\":{" +
            "\"sys_service_provider_id\":\"2088511833207846\"," +
            "\"hb_fq_num\":\"3\"," +
            "\"hb_fq_seller_percent\":\"100\"," +
            "\"industry_reflux_info\":\"{\\\\\\\"scene_code\\\\\\\":\\\\\\\"metro_tradeorder\\\\\\\",\\\\\\\"channel\\\\\\\":\\\\\\\"xxxx\\\\\\\",\\\\\\\"scene_data\\\\\\\":{\\\\\\\"asset_name\\\\\\\":\\\\\\\"ALIPAY\\\\\\\"}}\"," +
            "\"card_type\":\"S0JP0000\"" +
            "    }," +
            "\"timeout_express\":\"90m\"," +
            "\"royalty_info\":{" +
            "\"royalty_type\":\"ROYALTY\"," +
            "        \"royalty_detail_infos\":[{" +
            "          \"serial_no\":1," +
            "\"trans_in_type\":\"userId\"," +
            "\"batch_no\":\"123\"," +
            "\"out_relation_id\":\"20131124001\"," +
            "\"trans_out_type\":\"userId\"," +
            "\"trans_out\":\"2088101126765726\"," +
            "\"trans_in\":\"2088101126708402\"," +
            "\"amount\":0.1," +
            "\"desc\":\"分账测试1\"," +
            "\"amount_percentage\":\"100\"" +
            "          }]" +
            "    }," +
            "\"settle_info\":{" +
            "        \"settle_detail_infos\":[{" +
            "          \"trans_in_type\":\"cardAliasNo\"," +
            "\"trans_in\":\"A0001\"," +
            "\"summary_dimension\":\"A0001\"," +
            "\"settle_entity_id\":\"2088xxxxx;ST_0001\"," +
            "\"settle_entity_type\":\"SecondMerchant、Store\"," +
            "\"amount\":0.1" +
            "          }]" +
            "    }," +
            "\"sub_merchant\":{" +
            "\"merchant_id\":\"19023454\"," +
            "\"merchant_type\":\"alipay: 支付宝分配的间连商户编号, merchant: 商户端的间连商户编号\"" +
            "    }," +
            "\"alipay_store_id\":\"2016052600077000000015640104\"," +
            "\"merchant_order_no\":\"20161008001\"," +
            "\"ext_user_info\":{" +
            "\"name\":\"李明\"," +
            "\"mobile\":\"16587658765\"," +
            "\"cert_type\":\"IDENTITY_CARD\"," +
            "\"cert_no\":\"362334768769238881\"," +
            "\"min_age\":\"18\"," +
            "\"fix_buyer\":\"F\"," +
            "\"need_check_info\":\"F\"" +
            "    }," +
            "\"business_params\":{" +
            "\"campus_card\":\"0000306634\"," +
            "\"card_type\":\"T0HK0000\"," +
            "\"actual_order_time\":\"2019-05-14 09:18:55\"" +
            "    }," +
            "\"qr_code_timeout_express\":\"90m\"" +
            "  }");
        return request;
    }

    @Test
    public void test() {
        JSONObject json = new JSONObject();

        List<GoodsDetail> goodsDetailList = new ArrayList<>();
        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
        GoodsDetail goods1 =  new GoodsDetail();
        goods1.setGoodsId("goods_id001");
        goods1.setGoodsName("xxx小面包");
        goods1.setPrice("1000");
        goods1.setQuantity(1L);
        goodsDetailList.add(goods1);

        // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
        GoodsDetail goods2 = new GoodsDetail();
        goods2.setGoodsId("goods_id002");
        goods2.setGoodsName("xxx牙刷");
        goods2.setPrice("500");
        goods2.setQuantity(2L);
        goodsDetailList.add(goods2);

        json.put("goods_detail", goodsDetailList);

        System.out.println(JSONObject.toJSONString(json));
    }
}
