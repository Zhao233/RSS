package com.example.demo.util;

import com.example.demo.asynchronousHandler.Cooker.CookerJobHandler;
import com.example.demo.domain.user.Cooker;
import com.example.demo.domain.user.Waiter;
import com.example.demo.repository.user.CookerDao;
import com.example.demo.repository.user.WaiterDao;
import com.example.demo.service.user.CookerService;
import com.example.demo.service.user.Imp.CookerServiceImp;
import com.example.demo.service.user.Imp.WaiterServiceImp;
import com.example.demo.service.user.WaiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomUtil {
    @Autowired
    private WaiterService waiterService;
    @Autowired
    private CookerService cookerService;

    private Random random = new Random();


    public String createRandomLoginCode(){
        boolean isExist = true;

        String loginCode = null;

        while(isExist) {

            String randomLoginCode = "";

            randomLoginCode += random.nextInt(9);
            randomLoginCode += random.nextInt(9);
            randomLoginCode += random.nextInt(9);
            randomLoginCode += random.nextInt(9);
            randomLoginCode += random.nextInt(9);
            randomLoginCode += random.nextInt(9);

            Waiter waiter =  waiterService.getWaiterByLoginID(randomLoginCode);
            Cooker cooker = cookerService.getCookerByLoginID(randomLoginCode);

            if(waiter == null && cooker == null){
                isExist = false;

                loginCode = randomLoginCode;

                break;
            }
        }

        return loginCode;
    }
}
