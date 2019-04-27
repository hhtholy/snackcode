package com.hhtholy.controller.fore;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.hhtholy.config.aliPayConfig.AlipayConfig;
import com.hhtholy.entity.Order_;
import com.hhtholy.entity.Pay_;
import com.hhtholy.service.OrderService;
import com.hhtholy.service.PayService;
import com.hhtholy.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.PrinterAbortException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author hht
 * @create 2019-04-27 10:48
 */
@Controller
public class CallbackController {
    @Autowired private PayService payService;
    @Autowired private OrderService orderService;

    @RequestMapping("/returnUrl")
    public String synCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException, ParseException {
        response.setContentType("text/html;charset=utf-8");
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用 实际无效
          //  valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
        if(signVerified) {
          /*  //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
            //付款时间
            String datePay = new String(request.getParameter("timestamp").getBytes("ISO-8859-1"),"UTF-8");

            //创建交易记录
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Pay_ pay = new Pay_();
            pay.setOut_trade_no(out_trade_no);
            pay.setTrade_no(trade_no);
            pay.setTotal_fee(Float.parseFloat(total_amount));
            pay.setPay_date(f.parse(datePay));
            payService.addPay(pay); //存入交易记录
            Order_ orderResult = orderService.getOrderByOrderCode(out_trade_no);
            orderResult.setStatus(Constant.ORDER_WAITDELIVERY.getWord());
            orderService.updateOrder(orderResult); //更新订单
          */

        }else {
            System.out.println("验签失败");
        }
        return "redirect:home";
    }

    @RequestMapping("/notifyUrl")
    public void asynCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException, ParseException {
        response.setContentType("text/html;charset=utf-8");
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
           //付款时间
            String datePay = new String(request.getParameter("timestamp").getBytes("ISO-8859-1"),"UTF-8");

            //创建交易记录
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Pay_ pay = new Pay_();
            pay.setOut_trade_no(out_trade_no);
            pay.setTrade_no(trade_no);
            pay.setTotal_fee(Float.parseFloat(total_amount));
            pay.setPay_date(f.parse(datePay));
            payService.addPay(pay); //存入交易记录
            Order_ orderResult = orderService.getOrderByOrderCode(out_trade_no);
            orderResult.setStatus(Constant.ORDER_WAITDELIVERY.getWord());
            orderService.updateOrder(orderResult); //更新订单
        } else {
            System.out.println("验签失败");
        }
    }


}
