package com.notification.data.externalResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private String code;
    private String transid;
    private String description;
    private String correlationid;
}
