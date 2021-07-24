package com.notification.data.externalRequests;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Destination {
    private List<String> msisdn;
    private String correlationid;
}
