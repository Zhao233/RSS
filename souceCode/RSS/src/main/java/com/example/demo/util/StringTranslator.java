package com.example.demo.util;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class StringTranslator<T> {//T:目标数据类型

    public static List<Long> getListFromString(String string, int type){//type:0 转换为long
        List<Long> list = new LinkedList<Long>();

        String[] strings = string.split("_");

        for(String index : strings){
            list.add(Long.parseLong(index));
        }

        return list;
    }
}
