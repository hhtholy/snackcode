package com.hhtholy.utils.aliSendMs;

import com.aliyuncs.exceptions.ClientException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author hht
 * @create 2019-05-09 11:37
 */
public class Dmo {

    public static void main(String[] args) throws ClientException {
        // SendMs.sendMs("18061651105","12333");

      /*  String password = "111";
       // String salt = "M3L1x+20tSQ4Q6BbQxeUzA==";  //需要进行  加盐的操作  使用shiro的方式进行登录
        int times = 2;        //加密次数
        String algorithmName = "md5";        //加密策略
        String encodePassword = new SimpleHash(algorithmName, password, salt, times).toString();        //密码加密

        System.out.println(encodePassword);*/

        String salt = new SecureRandomNumberGenerator().nextBytes().toString();  //需要进行  加盐的操作  使用shiro的方式进行登录
        System.out.println(salt);
    }
}
