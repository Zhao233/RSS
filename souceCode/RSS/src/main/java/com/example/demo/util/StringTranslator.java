package com.example.demo.util;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class StringTranslator {//T:目标数据类型

    public static List getListFromString(String string, int type){//type:0 转换为long  1:转换为int
        if(string.length() == 0) {
            switch (type) {
                case 0:

                    return new LinkedList<Integer>();

                case 1:
                    return new LinkedList<Long>();
            }
        }

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

    public static <T> String getStringFromList(List<T> list){
        String listToString = "";

        for(T t : list){
            listToString += ( t + "_" );
        }

        if( listToString.length() == 0 ){
            return listToString;
        }

        String result = (String) listToString.subSequence(0,listToString.length()-1);
        return result;
    }

    public static <T> String getStringFromArray(T[] list){
        String result = "";

        for(T t : list){
            result += t+"_";
        }

        return result;
    }
}
