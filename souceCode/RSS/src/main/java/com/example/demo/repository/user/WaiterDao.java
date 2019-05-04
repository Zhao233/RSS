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

    /**==============================For Waiter====================================*/
    @Query(value = "SELECT waiter FROM Waiter waiter WHERE waiter.userID = ?1 AND waiter.enable = 1")
    Waiter getWaiterByOpenID(String openid);

    @Query(value = "SELECT waiter FROM Waiter waiter WHERE waiter.loginID = ?1 AND waiter.enable = 1")
    Waiter getWaiterByLoginID(String secret);
}

