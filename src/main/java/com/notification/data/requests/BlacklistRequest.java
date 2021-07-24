package com.notification.data.requests;

import lombok.Data;

import java.util.List;

@Data
public class BlacklistRequest {
    List<String> phoneNumbers;
}
