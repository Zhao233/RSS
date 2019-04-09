package com.example.demo.service.user.Imp;

import com.example.demo.domain.foodInfo.Menu;
import com.example.demo.domain.user.Waiter;
import com.example.demo.repository.user.WaiterDao;
import com.example.demo.service.user.WaiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("waiterService")
public class WaiterServiceImp implements WaiterService {
    @Autowired
    private WaiterDao waiterDao;

    @Override
    public Page<Waiter> getWaiterList(String search, Pageable pageable) {
        return waiterDao.getMenuList(search, pageable);
    }

    @Override
    public void addWaiter(Waiter waiter) {
        waiterDao.save(waiter);
    }

    @Override
    public void updateWaiter(Waiter waiter) {
        waiterDao.save(waiter);
    }

    @Override
    public void deleteWaiter(long id) {
        waiterDao.deleteWaiter(id);
    }

    @Override
    public boolean isLoginIDExist(String loginID) {
        List<Long> list = waiterDao.getLoginID(loginID);

        return list.size() != 0;
    }
}
