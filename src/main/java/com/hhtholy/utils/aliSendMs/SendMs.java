package com.hhtholy.utils.aliSendMs;

import com.aliyun.oss.ClientException;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.rmi.ServerException;

/**
 * @author hht
 * @create 2019-05-09 9:58
 */
public class SendMs {

         public static String  sendMs(String phone,String code) throws com.aliyuncs.exceptions.ClientException {
           ///
             DefaultProfile profile = DefaultProfile.getProfile("default", "LTAIM8cPA9D5dI2o", "OhvJKYZaylOtgARfhwewcs3NuRY31z");
             IAcsClient client = new DefaultAcsClient(profile);

             CommonRequest request = new CommonRequest();
             //request.setProtocol(ProtocolType.HTTPS);
             request.setMethod(MethodType.POST);
             request.setDomain("dysmsapi.aliyuncs.com");
             request.setVersion("2017-05-25");
             request.setAction("SendSms");
             request.putQueryParameter("PhoneNumbers", phone);
             request.putQueryParameter("TemplateParam", "{\"code\":"+code+"}");
             request.putQueryParameter("SignName", "mini零食小站");
             request.putQueryParameter("TemplateCode", "SMS_165040116");
             try {
                 CommonResponse response = client.getCommonResponse(request);
                 String data = response.getData();
                 System.out.println(data);
               /*  System.out.println(data);
                 ObjectMapper mapper = new ObjectMapper();

                 MsResponse msResponse = mapper.readValue(data, MsResponse.class);
                 System.out.println(msResponse.getCode());*/

             } catch (ClientException e) {
                 e.printStackTrace();
             }

             return code;
         }
}
