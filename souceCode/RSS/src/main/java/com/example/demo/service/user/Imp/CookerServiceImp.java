package com.example.demo.service.user.Imp;

import com.example.demo.repository.user.CookerDao;
import com.example.demo.service.user.CookerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cookerSerivice")
public class CookerServiceImp implements CookerService {
    @Autowired
    private CookerDao cookerDao;


//    @Override
//    public boolean isLoginIDExist(String loginID) {
//        List<String> list = cookerDao.getLoginID(loginID);
//
//        return list.size() != 0;
//    }
}
