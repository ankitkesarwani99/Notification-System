package com.notification.service;

import com.notification.data.externalRequests.RootSmsRequest;
import com.notification.data.externalResponse.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class SendSmsService {
    @Autowired
    private RestTemplate restTemplate;

    private static final String SEND_SMS_URL = "https://api.imiconnect.in/resources/v1/messaging";

    public ApiResponse sendSMS(RootSmsRequest rootSmsRequest) throws Exception {

        log.info("RootSmsRequest : {}", rootSmsRequest);

        HttpHeaders httpHeaders = createHttpHeaders();

        HttpEntity<Object> request = new HttpEntity<>(rootSmsRequest, httpHeaders);

        ResponseEntity<ApiResponse> genericResponse = restTemplate.exchange(SEND_SMS_URL, HttpMethod.POST, request, ApiResponse.class);

        if (ObjectUtils.isEmpty(genericResponse)) {
            throw new Exception("Error in sending Sms");
        }
        return genericResponse.getBody();
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("key", "93ceffda-5941-11ea-9da9-025282c394f2");
        return httpHeaders;
    }
}
