package com.example.demo.service.user.Imp;

import com.example.demo.domain.user.Cooker;
import com.example.demo.repository.user.CookerDao;
import com.example.demo.service.user.CookerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
