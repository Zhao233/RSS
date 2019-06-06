package com.example.demo.service.user.Imp;

import com.example.demo.domain.user.Cooker;
import com.example.demo.domain.user.Waiter;
import com.example.demo.repository.user.CookerDao;
import com.example.demo.service.user.CookerService;
import com.example.demo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("cookerService")
public class CookerServiceImp implements CookerService {
    @Autowired
    private CookerDao cookerDao;

    @Override
    public Page<Cooker> getAll(String search, Pageable pageable) {
        return cookerDao.getAll(search, pageable);
    }

    @Override
    public Cooker getOne(long id) {
        return cookerDao.getOne(id);
    }

    @Override
    public void addOne(Cooker cooker) {
        cookerDao.save(cooker);

    }

    @Override
    public void updateOne(Cooker cooker) {
        cookerDao.save(cooker);
    }

    @Override
    public void deleteOne(long id) {
        cookerDao.deleteById(id);
    }

    @Override
    public Cooker registerCooker(String loginID, String openid, String name) {
        Cooker cooker = cookerDao.getCookerByLoginID(loginID);

        cooker.setOpenID(openid);
        cooker.setName("h");
        cooker.setUpdateTime(TimeUtil.getTimeNow());
        cooker = cookerDao.save(cooker);

        return cooker;
    }

    /**=========================For Cooker==========================*/

    @Override
    public boolean isLogin(String openid) {
        Cooker cooker = cookerDao.getCookerByOpenID(openid);

        if(cooker == null || cooker.getLoginID() == null){
            return false;
        }

        return true;
    }

    @Override
    public int checkIsCookerExistByLoginID(String loginID) {
        Cooker cooker = cookerDao.getCookerByLoginID(loginID);

        if(cooker == null){//后台未录入
            return 1;
        }

        if(cooker != null && cooker.getOpenID() != null){//后台已存在

            return 2;
        }

        return 3;
    }



    @Override
    public Cooker getCookerByOpenID(String openID) {
        return cookerDao.getCookerByOpenID(openID);
    }
}
