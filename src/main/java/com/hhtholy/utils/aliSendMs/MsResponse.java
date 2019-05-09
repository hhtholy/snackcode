package com.hhtholy.utils.aliSendMs;

import lombok.Data;

/**
 * @author hht
 * @create 2019-05-09 14:22
 */
@Data
public class MsResponse {
    /**
     * {"Message":"OK",
     * "RequestId":"E371CF31-59FB-4E36-9B71-EB10FDAB1158",
     * "BizId":"265011057383046231^0",
     * "Code":"OK"}
     */
    private  String message;
    private  String requestId;
    private  String bizId;
    private  String code;

    @Override
    public String toString() {
        return "MsResponse{" +
                "message='" + message + '\'' +
                ", requestId='" + requestId + '\'' +
                ", bizId='" + bizId + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
