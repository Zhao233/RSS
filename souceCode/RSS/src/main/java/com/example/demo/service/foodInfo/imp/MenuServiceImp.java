package com.example.demo.service.foodInfo.imp;

import com.example.demo.domain.foodInfo.Menu;
import com.example.demo.repository.foodInfo.MenuDao;
import com.example.demo.service.foodInfo.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service("menuService")
public class MenuServiceImp implements MenuService {
    @Autowired
    private MenuDao menuDao;


    @Override
    public List<Menu> getMenuList() {
        try{
            return menuDao.findAll();
        } catch (Exception e){
            return new LinkedList<>();
        }
    }
}
