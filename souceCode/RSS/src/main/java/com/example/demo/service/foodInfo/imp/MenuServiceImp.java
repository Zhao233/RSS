package com.example.demo.service.foodInfo.imp;

import com.example.demo.domain.foodInfo.Menu;
import com.example.demo.repository.foodInfo.MenuDao;
import com.example.demo.service.foodInfo.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("menuService")
public class MenuServiceImp implements MenuService {
    @Autowired
    private MenuDao menuDao;


    @Override
    public Page<Menu> getMenuList(String search, Pageable pageable) {
        return  menuDao.getMenuList(search, pageable);
    }

    @Override
    public Menu getOne(long id) {
        return menuDao.getOne(id);
    }

    @Override
    public void addMenu(Menu menu) {
        menuDao.save(menu);
    }

    @Override
    public void updateMenu(Menu menu) {
        menuDao.save(menu);
    }

    @Override
    public void deleteMenu(long id) {
        menuDao.deleteById(id);
    }
}
