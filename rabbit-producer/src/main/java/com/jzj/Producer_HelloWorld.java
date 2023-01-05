package com.jzj;

import com.rabbitmq.client.Channel;

/**
 * @Author Jzj
 * @Date ${DATE} ${TIME}
 * @Version 1.0
 * @Message:
 */
public class Producer_HelloWorld {
    public static final String QUEUE_NAME = "hello_world";

    public static void main(String[] args) throws Exception {
        //创建一个连接工厂
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        String message = "Hello World";
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        channel.close();
    }
}
