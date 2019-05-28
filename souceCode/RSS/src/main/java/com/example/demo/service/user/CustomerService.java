package com.example.demo.service.user;

import com.example.demo.domain.user.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    /**================================For Admin====================================*/
    Page<Customer> getAll(String search, Pageable pageable);

    Long getIDByOpenID(String openID);

    boolean isLogin(String openid);

    int checkIsCustomerExistByLoginID(String loginID);

    Customer registerCustomer(String openid);
}
