package com.example.demo.repository.user;

import com.example.demo.domain.user.Waiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaiterDao extends JpaRepository<Waiter,Long> {
}

