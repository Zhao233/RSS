package com.example.demo.repository.user;

import com.example.demo.domain.user.Waiter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaiterDao extends JpaRepository<Waiter,Long> {
    @Query(value = "SELECT waiter FROM Waiter waiter where waiter.name like %?1%")
    Page<Waiter> getAll(String search, Pageable pageable);
}

