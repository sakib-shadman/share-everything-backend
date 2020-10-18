package com.shareeverything.controller;

import com.shareeverything.component.RabbitMqMessageSender;
import com.shareeverything.config.RabbitMqConfig;
import com.shareeverything.dto.request.RabbitMqTestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/")
@Slf4j
public class RabbitMqController {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    RabbitMqConfig rabbitMqConfig;
    @Autowired
    RabbitMqMessageSender rabbitMqMessageSender;

    @RequestMapping(path = "/send-message", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendMessage(@RequestBody RabbitMqTestDto rabbitMqTestDto) {

        String exchange = rabbitMqConfig.getApp1Exchange();
        String routingKey = rabbitMqConfig.getApp1RoutingKey();

        /* Sending to Message Queue */
        try {
            rabbitMqMessageSender.sendMessage(rabbitTemplate, exchange, routingKey, rabbitMqTestDto);
            return new ResponseEntity<String>("In queue", HttpStatus.OK);

        } catch (Exception ex) {
            log.error("Exception occurred while sending message to the queue. Exception= {}", ex);
            return new ResponseEntity("Message queue send error",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
