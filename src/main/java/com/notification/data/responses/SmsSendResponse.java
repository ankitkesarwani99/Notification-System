package com.notification.data.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SmsSendResponse {

    private Data data;
    private Error error;

    public static SmsSendResponse failureResponseWithCode(String code, String message){
        Error error1=new Error();
        error1.setMessage(message);
        error1.setCode(code);
        SmsSendResponse smsSendresponse=new SmsSendResponse();
        smsSendresponse.setError(error1);
        return smsSendresponse;
    }
    public static SmsSendResponse failureResponse(String message){
        Error error1=new Error();
        error1.setMessage(message);
        SmsSendResponse smsSendresponse=new SmsSendResponse();
        smsSendresponse.setError(error1);
        return smsSendresponse;
    }

}
