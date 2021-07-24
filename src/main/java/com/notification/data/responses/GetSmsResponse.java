package com.notification.data.responses;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.notification.data.model.SmsRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSmsResponse {
    private SmsRecord data;
    private Error error;

    public static GetSmsResponse failureResponseWithCode(String code, String message){
        Error error1=new Error();
        error1.setMessage(message);
        error1.setCode(code);
        GetSmsResponse getSmsResponse=new GetSmsResponse();
        getSmsResponse.setError(error1);
        return getSmsResponse;
    }
}
