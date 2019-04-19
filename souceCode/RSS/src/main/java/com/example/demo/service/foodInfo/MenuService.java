package com.example.demo.service.foodInfo;

import com.example.demo.domain.foodInfo.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MenuService {
    Page<Menu> getMenuList(String search, Pageable pageable);

    List<Menu> getAll();

    Menu getOne(long id);

    void addMenu(Menu menu);

    void updateMenu(Menu menu);

    void deleteMenu(long id);
}
