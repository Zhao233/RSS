package com.example.demo.service.user;

import com.example.demo.domain.user.Waiter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WaiterService {
    Page<Waiter> getAll(String search, Pageable pageable);

    Waiter getOneByID(long id);

    void addOne(Waiter waiter);

    void updateOne(Waiter waiter);

    void deleteOne(long id);

    Waiter registerWaiter(String loginID, String openid, String name);

    Waiter getWaiterByLoginID(String secret);

    /**=========================For Waiter==========================*/
    boolean isLogin(String openid);

    int checkIsWaiterExistByLoginID(String loginID);


    Waiter getWaiterByOpenID(String openID);
}
