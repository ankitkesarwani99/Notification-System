package com.notification.controller;

import com.notification.data.requests.BlacklistRequest;
import com.notification.data.requests.SmsSendRequest;
import com.notification.data.responses.GetSmsResponse;
import com.notification.data.responses.SendBlacklistResponse;
import com.notification.data.responses.SmsSendResponse;
import com.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/v1")
@Slf4j
@Validated
public class NotificationServiceController {

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "/sms/send", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<SmsSendResponse> sndSms(@RequestBody @Valid SmsSendRequest smsSendRequest) {

        log.info("Received SMS send request:{}", smsSendRequest);
        SmsSendResponse smsSendResponse;
        HttpStatus httpStatus;
        try {
            smsSendResponse = notificationService.sendSms(smsSendRequest);
            httpStatus = HttpStatus.OK;
        }catch (Exception e) {
            log.error("some error occurred during sms send request, error:{}", e);
            smsSendResponse = SmsSendResponse.failureResponse("some error occurred during sms send request");
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(smsSendResponse, httpStatus);
    }

    @RequestMapping(value = "/sms/{request_id}", method = RequestMethod.GET)
    public ResponseEntity<GetSmsResponse> getSms(@PathVariable("request_id") Integer requestId) {

        GetSmsResponse getSmsResponse;
        HttpStatus httpStatus;
        try {
            getSmsResponse = notificationService.getSms(requestId);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            log.info(e.getMessage());
            getSmsResponse = GetSmsResponse.failureResponseWithCode("Invalid Request", e.getMessage());
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(getSmsResponse, httpStatus);
    }

    @RequestMapping(value = "/blacklist", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<SendBlacklistResponse> blacklistPhoneNumbers(@RequestBody BlacklistRequest blacklistRequest) {

        log.info("Received blacklist request:{}", blacklistRequest);
        SendBlacklistResponse sendBlacklistResponse;
        HttpStatus httpStatus;
        try {
            sendBlacklistResponse = notificationService.blacklistPhoneNumbers(blacklistRequest);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            log.error("some error occurred during saving blacklisted phone numbers , error:{}", e);
            sendBlacklistResponse = SendBlacklistResponse.failureResponse("some error occurred during saving blacklisted phone numbers");
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(sendBlacklistResponse, httpStatus);
    }


}
