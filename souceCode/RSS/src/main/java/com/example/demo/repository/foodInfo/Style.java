package com.example.demo.repository.foodInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Style extends JpaRepository<Style,Long> {
}
