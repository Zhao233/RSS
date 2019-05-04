package com.example.demo.service.user;

import com.example.demo.domain.user.Waiter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface WaiterService {
    Page<Waiter> getAll(String search, Pageable pageable);

    Waiter getOneByID(long id);

    void addOne(Waiter waiter);

    void updateOne(Waiter waiter);

    void deleteOne(long id);

    /**=========================For Waiter==========================*/
    Map<String, Object> waiterLoginCheck(String openid, String secret);

    boolean isLogin(String openid);
}
