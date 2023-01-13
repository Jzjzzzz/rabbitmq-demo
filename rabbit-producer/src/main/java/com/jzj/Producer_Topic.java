package com.jzj;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

/**
 * @Author Jzj
 * @Date 2023/1/6 9:11
 * @Version 1.0
 * @Message:
 */
public class Producer_Topic {
    public static final String QUEUE_NAME1 = "test_topic_queue1";
    public static final String QUEUE_NAME2 = "test_topic_queue2";

    public static void main(String[] args) throws Exception {
        //创建一个连接工厂
        Channel channel = RabbitMqUtils.getChannel();
        String exchangeName = "test_topic";
        //创建交换机
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC,true,false,false,null);
        //创建队列
        channel.queueDeclare(QUEUE_NAME1,true,false,false,null);
        channel.queueDeclare(QUEUE_NAME2,true,false,false,null);
        //绑定队列和交换机
        //routing Key 系统的名称.日志级别
        //需求:所有error级别的日志存入数据库,所有order系统的日志存入数据库
        channel.queueBind(QUEUE_NAME1,exchangeName,"#.error");
        channel.queueBind(QUEUE_NAME1,exchangeName,"order.*");
        channel.queueBind(QUEUE_NAME2,exchangeName,"*.*");

        String message = "日志信息：张三调用了findAll方法...日志级别:info...";
        //发送消息
        channel.basicPublish(exchangeName,"good.info",null,message.getBytes());
        //释放资源
        channel.close();

    }
}
