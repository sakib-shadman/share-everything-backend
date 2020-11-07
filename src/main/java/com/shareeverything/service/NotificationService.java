package com.shareeverything.service;

import com.shareeverything.dto.Data;
import com.shareeverything.dto.Notification;
import com.shareeverything.dto.NotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.dnd.DropTarget;

@Slf4j
@Service
public class NotificationService {

    private void publishNotification(String token, String title, String message, String action) {
        publish(createNotification(token,title,message,action));
    }

    private void publish(NotificationDto notification) {

        String url = "https://fcm.googleapis.com/fcm/send";
        RestTemplate restTemplate = new RestTemplate();
    }

    private NotificationDto createNotification(String token, String title, String message, String action) {
        NotificationDto notificationDto = NotificationDto.builder()
                .to(token)
                .notification(Notification.builder().title(title).message(message).build())
                .data(Data.builder().action(action).title(title).message(message).build())
                .priority("high")
                .build();
        return notificationDto;
    }

}
