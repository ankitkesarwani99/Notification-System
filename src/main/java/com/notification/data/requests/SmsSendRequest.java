package com.notification.data.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Slf4j

public class SmsSendRequest {


    @JsonProperty("phone_number")
    @NotBlank(message = "phone number is mandatory")
    private String phoneNumber;

    @JsonProperty("message")
    private String message;

}
