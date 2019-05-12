package com.example.demo.service.user;

import com.example.demo.domain.foodInfo.Menu;
import com.example.demo.domain.user.Cooker;
import com.example.demo.domain.user.Waiter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CookerService {
    Page<Cooker> getAll(String search, Pageable pageable);

    Cooker getOne(long id);

    void addOne(Cooker cooker);

    void updateOne(Cooker cooker);

    void deleteOne(long id);

    /**=========================For Waiter==========================*/
    boolean isLogin(String openid);

    int checkIsCookerExistByLoginID(String loginID);

    Cooker registerCooker(String loginID, String openid);

    Cooker getCookerByOpenID(String openID);
}
