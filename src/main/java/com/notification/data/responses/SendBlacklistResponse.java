package com.notification.data.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SendBlacklistResponse {
    private String data;
    private String error;
    public static SendBlacklistResponse failureResponse(String message){
        SendBlacklistResponse sendBlacklistResponse=new SendBlacklistResponse();
        sendBlacklistResponse.setError(message);
        return sendBlacklistResponse;
    }
}
