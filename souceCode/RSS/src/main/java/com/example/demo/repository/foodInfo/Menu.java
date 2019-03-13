package com.example.demo.repository.foodInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Menu extends JpaRepository<Menu,Long> {
}
