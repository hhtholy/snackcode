package com.hhtholy.controller.fore;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.hhtholy.config.aliPayConfig.AlipayConfig;
import com.hhtholy.utils.Result;
import com.hhtholy.utils.aliPay.Pay;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author hht
 * @create 2019-04-26 20:02
 */
@RestController
public class PayController {

    @PostMapping("/pay")
    public Object aliPay(@RequestBody Pay pay, HttpServletResponse response) throws AlipayApiException, IOException {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        pay.setTotal_fee("1");
        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = pay.getOut_trade_no();
        // 付款金额，必填 企业金额
        String total_amount = pay.getTotal_fee();
        // 订单名称，必填
        String subject = pay.getSubject();
        String body = pay.getBody();

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        response.setContentType("text/html;charset=utf-8");
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        System.out.println(result);

        return Result.success(result);

    }
}
