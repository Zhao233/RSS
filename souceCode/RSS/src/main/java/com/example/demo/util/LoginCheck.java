package com.example.demo.util;

import com.example.demo.service.user.CookerService;
import com.example.demo.service.user.WaiterService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LoginCheck {
    @Autowired
    private static WaiterService waiterService;

    @Autowired
    private static CookerService cookerService;


    public static String generateRandomLoginID(){
        return "testtesttest";
    }

    public static boolean isLoginIDExist(String role, String loginID){

//        switch (role){
//            case "waiter" :
//                return waiterService.isLoginIDExist(loginID);
//
//            case "cooker" :
//                return cookerService.isLoginIDExist(loginID);
//
//            default:
//                return false;
//        }

        return true;

    }
}
