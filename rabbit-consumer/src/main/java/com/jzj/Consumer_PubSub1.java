package com.jzj;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @Author Jzj
 * @Date 2023/1/5 16:49
 * @Version 1.0
 * @Message:
 */
public class Consumer_PubSub1 {

    public static final String QUEUE_NAME1 = "test_fanout_queue1";

    public static void main(String[] args) throws Exception {
        //创建一个连接工厂
        Channel channel = RabbitMqUtils.getChannel();
        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println(new String(message.getBody()));
        };
        CancelCallback cancelCallback = consumerTag->{
            System.out.println("消息消费被中断");
        };
        System.out.println("test_fanout_queue1");
        channel.basicConsume(QUEUE_NAME1,true,deliverCallback,cancelCallback);
    }
}
