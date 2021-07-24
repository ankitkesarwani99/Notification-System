package com.notification.data.externalRequests;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RootSmsRequest {

    private String deliverychannel;
    private Channels channels;
    private List<Destination> destination;
}
