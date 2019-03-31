package com.example.demo.repository.foodInfo;

import com.example.demo.domain.foodInfo.Style;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StyleDao extends JpaRepository<Style,Long> {
}
