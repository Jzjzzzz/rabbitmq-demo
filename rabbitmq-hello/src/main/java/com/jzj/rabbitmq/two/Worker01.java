package com.jzj.rabbitmq.two;

import com.jzj.rabbitmq.one.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @Author Jzj
 * @Date 2023/1/5 15:35
 * @Version 1.0
 * @Message:
 */
public class Worker01 {

    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println(new String(message.getBody()));
        };
        CancelCallback cancelCallback = consumerTag->{
            System.out.println("消息消费被中断");
        };
        System.out.println("C2等待接收消息");
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);

    }
}
