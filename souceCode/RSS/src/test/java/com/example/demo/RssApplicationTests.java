package com.example.demo;

import com.example.demo.service.info.OrderRecordService;
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
        String path = "C:\\Users\\zx\\Desktop\\test.txt";
        String path1 = "C:\\Users\\zx\\Desktop\\test1.txt";

        File file = new File(path);
        File file1 = new File(path1);

        FileReader reader_ = new FileReader(file);
        FileWriter writer = new FileWriter(file1);

        BufferedReader reader = new BufferedReader(reader_);
        BufferedWriter writer1 = new BufferedWriter(writer);

        String line = reader.readLine();

        Random random = new Random();

        int num_ = 0;

        while(line != null){
            String[] strings = line.split(" ");

            int num = random.nextInt(11);
            while(num <= 1){
                num = random.nextInt(11);
            }

            String newString = strings[3] + "\t"+strings[1]+"\t"+strings[2]+":"+"\t"+num+"\n";

            writer1.write(newString);
            writer1.newLine();
            

            line = reader.readLine();

            System.out.println(num_);
            num_++;
        }

        writer1.flush();

        System.out.println("dadad");
    }
}