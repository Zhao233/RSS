package com.example.demo.service.user.Imp;

import com.example.demo.domain.user.Customer;
import com.example.demo.repository.user.CustomerDao;
import com.example.demo.service.user.CustomerService;
import com.example.demo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("customerService")
public class CustomerServiceImp implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    /**=====================================For Admin=====================================*/

    @Override
    public Page<Customer> getAll(String search, Pageable pageable) {
        return customerDao.getAll(search, pageable);
    }

    @Override
    public Long getIDByOpenID(String openID) {
        return customerDao.getIdByOpenID(openID);
    }


    /**=====================================For Customer=================================*/
    @Override
    public boolean isLogin(String openID) {
        Customer customer = customerDao.getCustomerByOpenID(openID);

        return customer != null;
    }


    @Override
    public int checkIsCustomerExistByLoginID(String openID) {
        Customer customer = customerDao.getCustomerByOpenID(openID);

        if(customer == null){//后台未录入
            return 1;
        }

        return 3;
    }

    @Override
    public Customer registerCustomer(String openID) {
        if( !isLogin(openID) ){

            Customer customer = new Customer();
            customer.setOpenID( openID );
            customer.setCreateTime( TimeUtil.getTimeNow() );
            customer.setLogInTimes(0);

            customerDao.save(customer);

            return customer;
        }

        return null;
    }


}
