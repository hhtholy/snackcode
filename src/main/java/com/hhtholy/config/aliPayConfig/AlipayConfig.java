package com.hhtholy.config.aliPayConfig;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author hht
 * @create 2019-04-26 15:45
 */
public class AlipayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016092400581846";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCYhwSYJX2JCEQzL/BUGU3T2Fo5MKVHr0pJghRMnli6rg8y1xmP15KquiX1NKonWWbezdq28j7Fsvoh9PXksaTaaw5lpolgbo3VyzQuke4PATrpmvoOcHvpMjy+MksyncaGbRQwF7I3G7kDtUWud6402QlWz9qRTAnXA9oMz0oV4izqIDbbkrTcZzLtGLEZyB/0CxbJRzbKdbYpilgw9M2mYm4xaI2wAb5C5Sr17tbgmF/BBZ3frzssTb3+UBy8QepFExQ5tsbAK3mqXoPijtjGhfnF85KULF5xqR+6DwUSpF5DpPTki/Gvx239XKHH5UmtBD9yIpcpm6FHzuRNWj4lAgMBAAECggEADJKEhG4CrPrrdDStYKE9UwrwVOp3lTMTsn9wYOhkc+7I/e3RA5RHWJ9DZbTTma661f6t2oqZNf6Ms6PXaORjrEVKyIIzrKkwM8EuKfCJaN83Fe5aUjpfXFdxvlOy8xciOA5hrZ0maXh1haK5faoLx/tj064KXBOidACzRn8HokoDbPn6WuAXh5gqC+NAGnOELxYq17u5/1Mj++QfK2s9XfMf6FswwnmG1OlO4/0W5OHhOeQWn6pz17OW4/jsm2GW+GmrwmTHmUwmZ2iSQh2+H3yGNiMJYnUqPtQrgXLpwuaSneMyubu6VMKYks6/xyltqZhpVTp2oC0rL/xsHsnxAQKBgQDldLPvq0dA1x2ZP/06gOm7/QLA7ca9dqLLVFRKV3CKmkqjkgWQFoDyu4iwR+w4uatfMzd6tJaOevl2v2t3/vcRue6B3eeojoQdoJgujXvm86v/22oiRRYs5/aur96N23zLm1hzY6EiV1/IuTOl3l0Qc4UO3KmyEV4JYTROkMrWoQKBgQCqLBd7XzCEPit6NPW9ynTe3Z1tXhTRI26sUvqTs00QGBtOG053H4IPXsn1jCzAv3wFBPnOcmodGBjrC7iLImm6NsIupbYw9OlvZOgXOBJFE6ZuLfX78YNYrqQ1cQFlyOjyRLGzmRCHeqvS4jCiKPo0V6Iel8GxUIyrdylRcBHtBQKBgQDL/7fCC0ssEJSWHVfr3ZTwt37tO/kHlSQjFfRiq964u3hEeJxNgFRgee6Km0cfH6OG3igjBc8dGaQUuRa49ot+zQmJmFpPd3VqSc9DBDL2IAn5TX/MvBN9FTYyFNYO1Obctl4rKJany3qzIEUU4N9osuvHTNPqJpquSCeS+hP8QQKBgQCGJ7BzcH29I43/D9VTxFISiL/dQrhCkB5l+BzXaBkuwPJ1xw062Doeh+nLjLCTW+IXyu5tFMIXIbIzStA3z+9uAFFhmP5i5hFJ4lEeTPQKRQ2F+vHbp3CW70dBYk9pwNP0erdlERx40fq3i94vWQK3VQbX+2eVtBzR6GVIcIORSQKBgHGHQF1qVqyzJrH0MQHHpY9mD+QRDd7O/HafnGwrU6fGc1fS/6i9FV0zUnabRqMhOzTWI0It3JhtLP2F9cXo41j9lWfhwJh7QvP6Ulhy8vZBqQHjvZ1YoImqqvZ/CYYaCIErQvvmj/y65AjxWoPEq+q+E8pUSckLfcCCYpdhcPuQ";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmyr+Dxts8CkVmoSi3Htt/BN4yFZ7+UT3X9CoStrZxqHvU9suJoB8hscLT+nGp0ydXuR3FtodWAHtUnDip8QdaVYjdDcxMlxt/6DwyGzqx0Rufecn2gj8I85XvaxQpRVhVAdzU1wDaJzHOdjYMv716wzBtCH2pHNey19WT5AQXqfUmI5tS3DiSNAEquh+nT558F8hWr3TmkZ/zAz3/+EVpRIJtCRl5MKE5uRc5vdjzLw9QiPLRFw1fr5foe6yWMuv5XJnQqQMq94MIz554efeH8hdeUw/oe40P1iBXSSkuPfliPz5P5DvM5fcogreeHmUnv9IIU5lZtie1IXHzgu+NQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://62.234.17.235:8080/snacktrade/notifyUrl";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
      public static String return_url = "http://localhost:8080/miniTrade/returnUrl";

   //public static String return_url = "http://62.234.17.235:8080/snacktrade/returnUrl";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
