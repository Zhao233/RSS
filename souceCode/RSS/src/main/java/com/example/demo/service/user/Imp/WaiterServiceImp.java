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
}
