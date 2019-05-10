package com.hhtholy.controller.fore;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.hhtholy.config.aliPayConfig.AlipayConfig;
import com.hhtholy.entity.OrderItem;
import com.hhtholy.entity.Order_;
import com.hhtholy.entity.Pay_;
import com.hhtholy.entity.Product;
import com.hhtholy.service.OrderItemService;
import com.hhtholy.service.OrderService;
import com.hhtholy.service.PayService;
import com.hhtholy.service.ProductService;
import com.hhtholy.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@Slf4j
public class CallbackController {
    @Autowired private PayService payService;
    @Autowired private OrderService orderService;
    @Autowired private OrderItemService orderItemService;
    @Autowired private ProductService productService;


    /****
     *   支付宝的同步回调  请求到这
     * @param request  请求参数获取对象
     * @param response
     * @return
     * @throws IOException
     * @throws AlipayApiException
     * @throws ParseException
     */
    @GetMapping("/returnUrl")
    public String synCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException, ParseException {
       log.info("同步界面");
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
           //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
            //付款时间
            String datePay = new String(request.getParameter("timestamp").getBytes("ISO-8859-1"),"UTF-8");
            Order_ orderResult = orderService.getOrderByOrderCode(out_trade_no);
            return "redirect:/paySuccess?oid="+orderResult.getId();

        }else {
            System.out.println("验签失败");
        }
         return null;
    }

    /**
     * 支付宝异步回调
     * @param request
     * @param response
     * @throws IOException
     * @throws AlipayApiException
     * @throws ParseException
     */
    @PostMapping("/notifyUrl")
    public void asynCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException, ParseException {
        log.info("进入异步通知~~");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
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
            //String datePay = new String(request.getParameter("timestamp").getBytes("ISO-8859-1"),"UTF-8");

            //创建交易记录
        //    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

             Date time = new Date();
            Pay_ pay = new Pay_();
            pay.setOut_trade_no(out_trade_no);
            pay.setTrade_no(trade_no);
            pay.setTotal_fee(Float.parseFloat(total_amount));
             pay.setPay_date(time);
            payService.addPay(pay); //存入交易记录
            Order_ orderResult = orderService.getOrderByOrderCode(out_trade_no);
            List<OrderItem> orderItems = orderResult.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                orderItem.setIncart(0); //不在购物车中 也代表已经支付过了
                orderItemService.updateOrderItem(orderItem);

                Integer number = orderItem.getNumber();//商品购买数量
                Product product = orderItem.getProduct();
                Integer stock = product.getStock();//库存
                if(stock >= number){
                    stock = stock - number;
                }else {
                    stock = 0;
                }
                product.setStock(stock);
                productService.updateProduct(product);
            }
            orderResult.setStatus(Constant.ORDER_WAITDELIVERY.getWord());
            orderResult.setPayDate(time);
            orderService.updateOrder(orderResult); //更新订单

            writer.write("success");
            log.info("进入异步通知~~");
        } else {
            writer.write("fail");
            System.out.println("验签失败");
        }
    }


}
