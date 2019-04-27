package com.example.demo.service.user.Imp;

import com.example.demo.repository.user.CustomerDao;
import com.example.demo.service.user.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("customerService")
public class CustomerServiceImp implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Override
    public Long getIDByOpenID(String openID) {

        return customerDao.getIdByOpenID(openID);
    }
}
