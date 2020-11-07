package com.shareeverything.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationDto {

    String to;
    Notification notification;
    Data data;
    String priority;

}
