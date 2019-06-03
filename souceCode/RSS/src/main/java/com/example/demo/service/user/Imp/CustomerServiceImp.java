package com.example.demo.service.user.Imp;

import com.example.demo.domain.info.CookerDeliveryRecord;
import com.example.demo.domain.user.Customer;
import com.example.demo.repository.user.CustomerDao;
import com.example.demo.service.user.CustomerService;
import com.example.demo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

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

    @Override
    public Customer registerCustomer(String openID, String name) {
        if( !isLogin(openID) ){

            Customer customer = new Customer();
            customer.setOpenID( openID );
            customer.setUserName(name);
            customer.setCreateTime( TimeUtil.getTimeNow() );
            customer.setLogInTimes(0);

            customerDao.save(customer);

            return customer;
        }

        return null;
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


    public Timestamp getTimeWithMonth(){
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        return new Timestamp(calendar.getTime().getTime());
    }
    public Timestamp getTimeWithWeek(){
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_WEEK, 2);
        calendar.set(Calendar.MINUTE, 0);

        return new Timestamp(calendar.getTime().getTime());
    }
    public Timestamp getTimeWithDay(){
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        return new Timestamp(calendar.getTime().getTime());
    }

}
