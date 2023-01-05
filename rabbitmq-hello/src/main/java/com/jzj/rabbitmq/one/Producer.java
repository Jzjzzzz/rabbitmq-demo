package com.jzj.rabbitmq.one;

import com.rabbitmq.client.Channel;

/**
 * @Author Jzj
 * @Date 2023/1/5 11:07
 * @Version 1.0
 * @Message: 生产者：发消息
 */
public class Producer {
    //队列
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        //创建一个连接工厂
        Channel channel = RabbitMqUtils.getChannel();
        /**
         * 生成队列
         * 1.队列名称
         * 2.队列里面的消息是否持久化（磁盘） 默认情况消息存储在内存中
         * 3.该队列是否只供一个消费者进行消费 是否进行消费共享,true可以多个消费者消费,false只能一个消费者消费
         * 4.是否自动删除,最后一个消费者断开连接以后,该队列是否自动删除,true自动删除,false不自动删除
         * 5.其他参数
         */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String message = "Hello World";
        /**
         * 发送消息
         * 1.发送到哪个交换机
         * 2.路由的Key值是哪一个 本次是队列名称
         * 3.其他参数信息
         * 4.发送消息的消息体
         */
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("消息发送完毕");
    }
}
