package com.notification.service;

import com.notification.data.externalRequests.Channels;
import com.notification.data.externalRequests.Destination;
import com.notification.data.externalRequests.RootSmsRequest;
import com.notification.data.externalRequests.Sms;
import com.notification.data.externalResponse.ApiResponse;
import com.notification.data.model.SmsRecord;
import com.notification.data.model.Status;
import com.notification.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class ConsumerService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private SendSmsService sendSmsService;

    @KafkaListener(topics = "notification.send_sms", groupId = "sms_group")
    public void  consumeMessage(String message)  {
        if(ObjectUtils.isEmpty(message)){
            log.info("No requestId data consumed");
            return;
        }
        Integer requestId=Integer.parseInt(message);
        log.info("Received requestId is : {}",requestId);

        Optional<SmsRecord> smsRequest=notificationRepository.findById(requestId);

        if(smsRequest.isPresent()) {
            RootSmsRequest rootSmsRequest=createRootSmsRequest(smsRequest.get());
            ApiResponse apiResponse;
            try {
                apiResponse=sendSmsService.sendSMS(rootSmsRequest);

                log.info("3rd party Api Response is : {}",apiResponse);

            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
            updateSmsRequestInDB(smsRequest.get(),apiResponse);
        } else {
            log.error("Some error occurred while fetching data from DB");
        }
    }

    private void updateSmsRequestInDB(SmsRecord smsRecord, ApiResponse apiResponse) {
        if (apiResponse.getResponse().get(0).getCode().equals("1001")) {
            smsRecord.setStatus(Status.SUCCESS);

        } else {
            smsRecord.setStatus(Status.FAILED);
            smsRecord.setFailureCode(apiResponse.getResponse().get(0).getCode());
            smsRecord.setFailureComments(apiResponse.getResponse().get(0).getDescription());
        }
        smsRecord.setUpdatedAt(LocalDateTime.now());
        notificationRepository.save(smsRecord);
    }

    private RootSmsRequest createRootSmsRequest(SmsRecord smsRecord) {
        Sms sms=new Sms();
        sms.setText(smsRecord.getMessage());

        Channels channels=new Channels();
        channels.setSms(sms);

        List<String> phoneNumbers=new ArrayList<>();
        phoneNumbers.add(smsRecord.getPhoneNumber());

        Destination destination=new Destination();
        destination.setMsisdn(phoneNumbers);

        destination.setCorrelationid(UUID.randomUUID().toString());

        List<Destination> destinations=new ArrayList<>();
        destinations.add(destination);

        RootSmsRequest rootSmsRequest=new RootSmsRequest();
        rootSmsRequest.setChannels(channels);
        rootSmsRequest.setDestination(destinations);
        rootSmsRequest.setDeliverychannel("sms");

        return rootSmsRequest;
    }


}
