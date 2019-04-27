package com.example.demo;

import com.example.demo.util.StringTranslator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.LinkedList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RssApplicationTests {

    @Test
    public void contextLoads() {
        LinkedList<Integer> list = new LinkedList<>();

        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        String a = StringTranslator.getStringFromList(list);

        System.out.println(a);

    }



}
