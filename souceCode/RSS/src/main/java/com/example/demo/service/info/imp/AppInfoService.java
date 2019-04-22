package com.example.demo.service.info.imp;

import com.example.demo.domain.info.AppInfo;
import com.example.demo.repository.info.AppInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("appInfoService")
public class AppInfoService {
    @Autowired
    private AppInfoDao appInfoDao;

    public AppInfo getAppInfo(){
        return appInfoDao.getOne(Long.valueOf(5));
    }

}
