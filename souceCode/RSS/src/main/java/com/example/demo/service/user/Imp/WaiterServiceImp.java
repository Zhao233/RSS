package com.example.demo.service.user.Imp;

import com.example.demo.domain.user.Waiter;
import com.example.demo.repository.user.WaiterDao;
import com.example.demo.service.user.WaiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("waiterService")
public class WaiterServiceImp implements WaiterService {
    @Autowired
    private WaiterDao waiterDao;

    @Override
    public Page<Waiter> getAll(String search, Pageable pageable) {
        return waiterDao.getAll(search, pageable);
    }

    @Override
    public Waiter getOneByID(long id) {
        return waiterDao.getOne(id);
    }

    @Override
    public void addOne(Waiter waiter) {
        waiterDao.save(waiter);
    }

    @Override
    public void updateOne(Waiter waiter) {
        waiterDao.save(waiter);
    }

    @Override
    public void deleteOne(long id) {
        waiterDao.deleteById(id);
    }


    /**=========================For Waiter==========================*/

    @Override
    public Map<String, Object> waiterLoginCheck(String openid, String secret) {
        Waiter waiter = waiterDao.isWaiterExist(secret);

        if(waiter == null){// 输错secret
            Map<String, Object> map = new HashMap<>();

            map.put("status", "FAILED");
            map.put("message", "用户未注册或登录码输入错误，请联系管理员注册");

            return map;
        }

        if(waiter != null && waiter.getUserID() == null){//secret正确, 但之前未登录
            Map<String, Object> map = new HashMap<>();

            waiter.setUserID(openid);

            waiterDao.save(waiter);

            map.put("status", "SUCCEED");
            map.put("message", "注册成功");
            return map;
        }

        if(waiter != null && waiter.getUserID() != null){//已注册
            Map<String, Object> map = new HashMap<>();

            map.put("status", "SUCCEED");
            map.put("message", "登录成功");
            return map;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("status","ERROR");

        return map;

    }

    @Override
    public boolean isLogin(String openid) {
        Map<String, Object> map = new HashMap<>();

        Waiter waiter = waiterDao.getWaiterByOpenID(openid);

        if(waiter == null || waiter.getLoginID() == null){
            return false;
        }

        return true;
    }
}
