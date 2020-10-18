package com.shareeverything.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMqMessageSender {
    public void sendMessage(RabbitTemplate rabbitTemplate, String exchange, String routingKey, Object data) {
        log.info("Sending message to the queue using routingKey {}. Message= {}", routingKey, data);
        rabbitTemplate.convertAndSend(exchange, routingKey, data);
        log.info("The message has been sent to the queue.");
    }

    public void sendMessagePublicCorpus(RabbitTemplate rabbitTemplate, String exchange, String routingKey, String url, Object data) {
        log.info("Sending message to the queue using routingKey {}.Url={}. Message= {}", routingKey,url, data);
        rabbitTemplate.convertAndSend(exchange, routingKey, data);
        log.info("The message has been sent to the queue.");
    }
}
