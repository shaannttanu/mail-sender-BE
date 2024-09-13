package com.BulkMailSend.bulkMailSend.util;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestResponse <T>{
    T data;
    Boolean success;
    String errorMessage;
    Integer errorCode;
    String meta;
}
