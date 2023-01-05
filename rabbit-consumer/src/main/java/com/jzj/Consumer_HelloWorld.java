package com.jzj;

import com.rabbitmq.client.*;

/**
 * @Author Jzj
 * @Date ${DATE} ${TIME}
 * @Version 1.0
 * @Message:
 */
public class Consumer_HelloWorld {
    public static final String QUEUE_NAME = "hello_world";

    public static void main(String[] args) throws Exception {
        //创建一个连接工厂
        Channel channel = RabbitMqUtils.getChannel();
        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println(new String(message.getBody()));
        };
        CancelCallback cancelCallback = consumerTag->{
            System.out.println("消息消费被中断");
        };

        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);

    }
}
