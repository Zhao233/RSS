package com.example.demo.service.user;

import com.example.demo.domain.user.Customer;

public interface CustomerService {
    Long getIDByOpenID(String openID);

    boolean isLogin(String openid);

    int checkIsCustomerExistByLoginID(String loginID);

    Customer registerCustomer(String openid);
}
