package com.hhtholy.utils;

/**
 * @author hht
 * @create 2019-04-17 10:58
 *
 * 一些错误数据 返回给前端的提示
 */
public class Result {

       public static int SUCCESS_CODE = 1;//定义成功的返回状态码
       public static int FAIL_CODE = 2;  //定义失败的返回状态码
       private int code;  //状态码
       private String message; //提示信息
       private Object data;  //数据

        public static Result success(){//无参数
            return new Result(SUCCESS_CODE,null,null);
        }

        public static Result success(Object data){
            return new Result(SUCCESS_CODE,null,data);
        }    //有参数

    //错误的情况
    public static Result fail(String message){
        return new Result(FAIL_CODE,message,null);
    } //返回错误

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
