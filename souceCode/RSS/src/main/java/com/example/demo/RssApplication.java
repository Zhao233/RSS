package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RssApplication {
    public static void main(String[] args) {
        Integer[] values = {1,5,3,7,3,8,4,6,4,3,7};


        for(int i = 0; i < 10;i ++){
            int index_max = 0;
            int max_value = values[i];

            for(int j = i; j < values.length; j++) {
                if (values[j] > max_value) {
                    max_value = values[j];
                    index_max = j;
                }
            }

            moveItemToPos(values, index_max, i);
        }

        SpringApplication.run(RssApplication.class, args);
    }

    public static void moveItemToPos(Integer[] values, int pos_1, int endPos){
        for(int i = pos_1; i > endPos; i--){// 1 5 2 5 3
            Integer temp = values[i];

            values[i] = values[i-1];
            values[i-1] = temp;
        }
    }
}
