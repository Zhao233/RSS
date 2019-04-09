package com.example.demo.repository.foodInfo;

import com.example.demo.domain.foodInfo.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuDao extends JpaRepository<Menu,Long> {
    @Query(value = "SELECT menu FROM Menu menu where menu.name like %?1%")
    Page<Menu> getMenuList(String search, Pageable pageable);
}
