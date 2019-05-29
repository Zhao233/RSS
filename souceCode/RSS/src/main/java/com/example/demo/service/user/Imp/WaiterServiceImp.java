package com.example.demo.service.user.Imp;

import com.example.demo.domain.user.Waiter;
import com.example.demo.repository.user.WaiterDao;
import com.example.demo.service.user.WaiterService;
import com.example.demo.util.TimeUtil;
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

    @Override
    public Waiter registerWaiter(String loginID, String openid, String name) {
        Waiter waiter = waiterDao.getWaiterByLoginID(loginID);

//        if(waiter == null){//后台未录入
//            map.put("status", "FAILED");
//            map.put("message", "登录码错误或管理员未录入信息，请重新输入，或联系管理员");
//
//            return map;
//        }
//
//        if(waiter != null && waiter.getUserID() != null){//后台已存在
//            map.put("status", "FAILED");
//            map.put("message", "登录码错误或管理员未录入信息，请重新输入，或联系管理员");
//
//            return map;
//        }

        waiter.setUserID(openid);
        waiter.setName(name);
        waiter.setUpdateTime(TimeUtil.getTimeNow());
        waiterDao.save(waiter);

        return waiter;

    }


    /**=========================For Waiter==========================*/

    @Override
    public boolean isLogin(String openid) {
        Map<String, Object> map = new HashMap<>();

        Waiter waiter = waiterDao.getWaiterByOpenID(openid);

        if(waiter == null || waiter.getLoginID() == null){
            return false;
        }

        return true;
    }

    @Override
    public int checkIsWaiterExistByLoginID(String loginID){
        Waiter waiter = waiterDao.getWaiterByLoginID(loginID);

        if(waiter == null){//后台未录入
            return 1;
        }

        if(waiter != null && waiter.getUserID() != null){//后台已存在

            return 2;
        }

        return 3;
    }



    @Override
    public Waiter getWaiterByOpenID(String openID) {
        return waiterDao.getWaiterByOpenID(openID);
    }
}
