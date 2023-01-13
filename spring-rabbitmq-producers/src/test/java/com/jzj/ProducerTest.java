package com.jzj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author Jzj
 * @Date 2023/1/6 10:02
 * @Version 1.0
 * @Message:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-producer.xml")
public class ProducerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testHelloWorld(){
        rabbitTemplate.convertAndSend("spring_queue","hello world spring1.....");
    }

    @Test
    public void testFanout(){
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("spring_fanout_exchange","","spring fanout");
        }
    }


    /**
     * 确认模式下的消息发送 :不管exchange是否接收到消息都会执行回调
     */
    @Test
    public void testConfirm(){
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             *
             * @param correlationData 相关配置信息
             * @param ack exchange交换机 是否成功收到消息,true成功,false失败
             * @param cause 失败原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("Confirm被执行了。。。");
                if(ack){
                    //接收成功
                    System.out.println("消息接收成功");
                }else {
                    //接收失败
                    System.out.println("接收失败:"+cause);
                }
            }
        });

        rabbitTemplate.convertAndSend("spring_fanout_exchange","","fanout fanout");
    }


    /**
     * 返回模式下的消息发送:当消息发送exchange后，exchange路由到queue失败时才会执行ReturnCallBack
     */
    @Test
    public void testReturn(){
        //设置交换机处理失败消息的模式
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             *
             * @param message 消息对象
             * @param replyCode 错误码
             * @param replyText 错误信息
             * @param exchange 交换机
             * @param routingKey 路由键
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("return 执行了");
            }
        });

        rabbitTemplate.convertAndSend("spring_fanout_exchange","","fanout fanout");
    }

    /**
     * TTL过期时间
     * 1.队列的统一过期
     * 2.消息单独过期
     * 如果设置了消息的过期时间,也设置了队列的过期时间,他以时间短的为准
     * 队列过期后,会将队列所有消息全部移除。
     */
    @Test
    public void testTTL(){
        // for (int i = 0; i < 10; i++) {
        //     rabbitTemplate.convertAndSend("test_exchange_ttl","ttl.hello","message ttl");
        // }
        //消息的后处理对象,设置一些消息的参数信息
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration("5000"); //消息的过期时间
                return message;
            }
        };

        //消息单独过期
        rabbitTemplate.convertAndSend("test_exchange_ttl","ttl.hello","message ttl",messagePostProcessor);
    }

    /**
     * 发送测试死信队列
     * 1.过期时间
     * 2.长度限制
     * 3.消息拒收
     */
    @Test
    public void testDLX(){
        //过期时间
        rabbitTemplate.convertAndSend("test_exchange_dlx","test.dlx.hello","我是一条消息");
    }


    /**
     * RabbitMQ实现延迟队列的方式:TTL+死信队列
     */

    @Test
    public void testDelay() throws InterruptedException {
        rabbitTemplate.convertAndSend("order_exchange","order.msg","订单信息:id=1,time=2023年1月13日16:32:11");

    }
}
