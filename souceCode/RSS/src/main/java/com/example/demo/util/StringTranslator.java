package com.example.demo.util;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class StringTranslator<T> {//T:目标数据类型

    public static List getListFromString(String string, int type){//type:0 转换为long  1:转换为int
        List list = null;
        String[] strings = string.split("_");

        switch ( type ){
            case 0 :
                list = new LinkedList<Long>();

                for(String index : strings){
                    list.add(Long.parseLong(index));
                }

                break;
            case 1 :
                list = new LinkedList<Integer>();

                for(String index : strings){
                    list.add(Integer.parseInt(index));
                }

                break;
        }

        return list;
    }
}
