package com.example.demo;

import com.example.demo.service.info.OrderRecordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RssApplicationTests {
    @Autowired
    private OrderRecordService orderRecordService;

    @Test
    public void contextLoads(){
        double a = orderRecordService.getAccount(1);

        System.out.println(a);
    }



}
