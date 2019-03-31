package com.example.demo.repository.user;

import com.example.demo.domain.user.Cooker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CookerDao extends JpaRepository<Cooker,Long> {
}

