package com.example.demo.repository.foodInfo;

import com.example.demo.domain.foodInfo.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuDao extends JpaRepository<Menu,Long> {
}
