package com.example.demo.repository.info;

import com.example.demo.domain.info.FrequentlyUsedMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrequentlyUsedMenuDao extends JpaRepository<FrequentlyUsedMenu,Long> {
}

