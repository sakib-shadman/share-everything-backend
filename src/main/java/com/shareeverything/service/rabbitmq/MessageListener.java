package com.shareeverything.service.rabbitmq;

import com.shareeverything.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@Slf4j
public class MessageListener {

    @Autowired
    RabbitMqConfig rabbitMqConfig;


    @RabbitListener(queues = "${app2.queue.name}")
    public void receiveMessageForApp2(String reqObj) {
        log.info("Received message: {} from app2 queue.", reqObj);

        try {
            log.info("Making REST call to the API");
            //TODO: Code to make REST call
            log.info("<< Exiting receiveMessageCrawlCI() after API call.");
        } catch(HttpClientErrorException ex) {

            if(ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.info("Delay...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) { }

                log.info("Throwing exception so that message will be requed in the queue.");
                // Note: Typically Application specific exception can be thrown below
                throw new RuntimeException();
            } else {
                throw new AmqpRejectAndDontRequeueException(ex);
            }

        } catch(Exception e) {
            log.error("Internal server error occurred in python server. Bypassing message requeue {}", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }

    }



}
