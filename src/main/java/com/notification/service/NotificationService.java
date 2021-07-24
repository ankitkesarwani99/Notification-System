package com.notification.service;

import com.notification.data.model.BlackListNumbers;
import com.notification.data.model.SmsRecord;
import com.notification.data.model.Status;
import com.notification.data.requests.BlacklistRequest;
import com.notification.data.requests.SmsSendRequest;
import com.notification.data.responses.Data;
import com.notification.data.responses.GetSmsResponse;
import com.notification.data.responses.SendBlacklistResponse;
import com.notification.data.responses.SmsSendResponse;
import com.notification.repository.BlackListNumbersRepository;
import com.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private BlackListNumbersRepository blackListNumbersRepository;

    public  GetSmsResponse getSms(Integer requestId) throws Exception {

        Optional<SmsRecord> smsRecord=notificationRepository.findById(requestId);
        if(!smsRecord.isPresent()){
            throw new Exception("request_id not found");
        }
        GetSmsResponse getSmsResponse=new GetSmsResponse();
        getSmsResponse.setData(smsRecord.get());
        return getSmsResponse;
    }

    public SmsSendResponse sendSms(SmsSendRequest smsSendRequest) {
        SmsRecord smsRecord =createSmsRequest(smsSendRequest);
        SmsRecord smsRequestSuccessful=notificationRepository.save(smsRecord);
        producerService.sendMessage(""+ smsRequestSuccessful.getId());
        return createSmsSendSuccessfulResponse(smsRequestSuccessful.getId());
    }

    public SendBlacklistResponse blacklistPhoneNumbers(BlacklistRequest blacklistRequest) {
        List<String> blacklistPhoneNumber=blacklistRequest.getPhoneNumbers();

        for(String phoneNumber : blacklistPhoneNumber){
            blackListNumbersRepository.save(new BlackListNumbers(phoneNumber));
        }
        SendBlacklistResponse sendBlacklistResponse=new SendBlacklistResponse();
        sendBlacklistResponse.setData("Successfully blacklisted");
        return sendBlacklistResponse;
    }

    private SmsRecord createSmsRequest(SmsSendRequest smsSendRequest) {
        SmsRecord smsRecord =new SmsRecord();
        smsRecord.setStatus(Status.PROCESSING);
        smsRecord.setCreatedAt(LocalDateTime.now());
        smsRecord.setMessage(smsSendRequest.getMessage());
        smsRecord.setPhoneNumber(smsSendRequest.getPhoneNumber());
        return smsRecord;

    }

    private SmsSendResponse createSmsSendSuccessfulResponse(Integer id) {
        SmsSendResponse smsSendresponse=new SmsSendResponse();
        Data data=new Data();
        data.setComments("Successfully Sent");
        data.setRequestId(id);
        smsSendresponse.setData(data);
        return smsSendresponse;
    }


}
