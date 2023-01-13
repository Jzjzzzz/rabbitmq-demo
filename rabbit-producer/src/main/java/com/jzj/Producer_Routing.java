package com.jzj;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

/**
 * @Author Jzj
 * @Date 2023/1/6 9:11
 * @Version 1.0
 * @Message:
 */
public class Producer_Routing {
    public static final String QUEUE_NAME1 = "test_direct_queue1";
    public static final String QUEUE_NAME2 = "test_direct_queue2";

    public static void main(String[] args) throws Exception {
        //创建一个连接工厂
        Channel channel = RabbitMqUtils.getChannel();
        String exchangeName = "test_direct";
        //创建交换机
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT,true,false,false,null);
        //创建队列
        channel.queueDeclare(QUEUE_NAME1,true,false,false,null);
        channel.queueDeclare(QUEUE_NAME2,true,false,false,null);
        //绑定队列和交换机
        //队列1的绑定
        channel.queueBind(QUEUE_NAME1,exchangeName,"error");
        //队列2的绑定
        channel.queueBind(QUEUE_NAME2,exchangeName,"info");
        channel.queueBind(QUEUE_NAME2,exchangeName,"error");
        channel.queueBind(QUEUE_NAME2,exchangeName,"warning");

        String message = "日志信息：张三调用了findAll方法...日志级别:info...";
        //发送消息
        channel.basicPublish(exchangeName,"error",null,message.getBytes());
        //释放资源
        channel.close();

    }
}
