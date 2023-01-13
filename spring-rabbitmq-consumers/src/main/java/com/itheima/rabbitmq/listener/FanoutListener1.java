package com.itheima.rabbitmq.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

/**
 * @Author Jzj
 * @Date 2023/1/6 16:36
 * @Version 1.0
 * @Message: Consumer Ack机制：
 * 设置手动签收
 */
public class FanoutListener1 implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println(new String(message.getBody()));

            System.out.println("处理业务逻辑");
            channel.basicAck(deliveryTag,true);
        } catch (Exception e) {
            //第三个参数是否重回队列
            channel.basicNack(deliveryTag,true,true);
        }

    }
}
