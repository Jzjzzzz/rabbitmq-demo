package com.itheima;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author Jzj
 * @Date 2023/1/6 10:15
 * @Version 1.0
 * @Message:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-producer.xml")
public class ConsumerTest {

    @Test
    public void test1(){
        while (true){

        }
    }
}
