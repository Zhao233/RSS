package com.example.demo.repository.foodInfo;

import com.example.demo.domain.foodInfo.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuDao extends JpaRepository<Menu,Long> {
    @Query(value = "SELECT menu FROM Menu menu where menu.name like %?1%")
    Page<Menu> getMenuList(String search, Pageable pageable);


    /**
     * 调用:customer
     * 查找所有启用的Menu
     * */
    @Query(value = "SELECT menu FROM Menu menu where menu.enable = 1")
    List<Menu> getMenuList();

    @Query(value = "SELECT menu.name FROM Menu menu where menu.id = ?1")
    String getMenuNameById(long id);
}
