package com.example.demo;

import com.example.demo.service.info.OrderRecordService;
import com.example.demo.util.PasswordEncodeAssistant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RssApplicationTests {
    @Test
    public void run() throws IOException {
        String pass = "root";

        String encode = PasswordEncodeAssistant.encode(pass.toCharArray());

        System.out.println(encode);
    }
}