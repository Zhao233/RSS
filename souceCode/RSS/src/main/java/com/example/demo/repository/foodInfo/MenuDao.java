package com.example.demo.repository.foodInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuDao extends JpaRepository<MenuDao,Long> {
}
