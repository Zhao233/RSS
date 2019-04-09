package com.example.demo.service.user;

import com.example.demo.domain.foodInfo.Menu;
import com.example.demo.domain.user.Waiter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WaiterService {
    Page<Waiter> getWaiterList(String search, Pageable pageable);

    void addWaiter(Waiter waiter);

    void updateWaiter(Waiter waiter);

    void deleteWaiter(long id);

    boolean isLoginIDExist(String loginID);

}
